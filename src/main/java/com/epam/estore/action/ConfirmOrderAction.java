package com.epam.estore.action;

import com.epam.estore.database.dao.impl.*;
import com.epam.estore.database.dao.interfaces.*;
import com.epam.estore.entity.Basket;
import com.epam.estore.entity.Order;
import com.epam.estore.entity.OrderDetail;
import com.epam.estore.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.epam.estore.util.constants.PageNameConstants.INDEX_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class ConfirmOrderAction implements Action {
    private ProductDAO productDAO = new ProductDAOImpl();
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderDetail orderDetail = new OrderDetail();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    private BasketDAO basketDAO = new BasketDAOImpl();
    private StatusDAO statusDAO = new StatusDAOImpl();
    private Order order = new Order();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);

        Long userId = (Long) httpSession.getAttribute(USER_ID);
        Long localeId = (Long) httpSession.getAttribute(LOCALE_ID);
        setValuesIntoOrderAndInsertIntoDatabase(userId, localeId, request);
        List<Basket> baskets = basketDAO.getAllBasketsByUserId(userId);
        viewAllBasketsToConfirmOrder(baskets, userId);

        basketDAO.removeAllBasketsByUserId(userId);
        request.getRequestDispatcher(INDEX_JSP).forward(request, response);
    }

    private void viewAllBasketsToConfirmOrder(List<Basket> baskets, Long userId) throws IOException {
        List<Product> productListToOrder = new ArrayList<>();
        Product productToOrder = null;
        for (Basket basket : baskets) {
            productToOrder = productDAO.getProductById(basket.getProductId());
            Integer newCount = productToOrder.getCount() - basket.getCount();
            productDAO.updateCountById(newCount, productToOrder.getId());
            productToOrder.setCount(basket.getCount());
            productListToOrder.add(productToOrder);
            setValuesIntoOrderDetailAndInsertIntoDatabase(userId, productToOrder);
        }
    }

    private void setValuesIntoOrderDetailAndInsertIntoDatabase(Long userId, Product productToOrder) {
        Long orderId = orderDAO.getLastOrderIdByUserId(userId);

        orderDetail.setOrderId(orderId);
        orderDetail.setProductId(productToOrder.getId());
        orderDetail.setCount(productToOrder.getCount());
        orderDetail.setCost(productToOrder.getCost());
        orderDetail.setProduct(productToOrder);
        orderDetailDAO.insertOrderDetail(orderDetail);
    }

    private void setValuesIntoOrderAndInsertIntoDatabase(Long userId, Long localeId, HttpServletRequest request) {
        Integer totalCost = Integer.parseInt(request.getParameter(TOTAL_COST));
        Long statusId = chooseStatusId(localeId);
        Date dateStart = new Date();
        Date dateFinish = setDateFinish(dateStart);

        order.setUserId(userId);
        order.setStatusId(statusId);
        order.setTotalCost(totalCost);
        order.setDateStart(dateStart);
        order.setDateFinish(dateFinish);
        orderDAO.insertOrder(order);
    }

    private Date setDateFinish(Date dateStart) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }

    private Long chooseStatusId(Long localeId) {
        Long statusId = null;
        if (localeId == 1) {
            statusId = statusDAO.getStatusIdByNameAndLocaleId("В обработке", localeId);
        }
        if (localeId == 2) {
            statusId = statusDAO.getStatusIdByNameAndLocaleId("In processing", localeId);
        }
        return statusId;
    }
}
