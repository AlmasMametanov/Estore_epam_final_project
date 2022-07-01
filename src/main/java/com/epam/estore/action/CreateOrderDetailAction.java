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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.INDEX_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class CreateOrderDetailAction implements Action {
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
        Integer localeId = (Integer) httpSession.getAttribute(LOCALE_ID);
        Integer totalCost = Integer.parseInt(request.getParameter(TOTAL_COST));
        Integer statusId = null;
        if (localeId == 1) {
            statusId = statusDAO.getStatusIdByNameAndLocaleId("В обработке", localeId);
        }
        if (localeId == 2) {
            statusId = statusDAO.getStatusIdByNameAndLocaleId("In processing", localeId);
        }

        Date dateStart = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.DATE, 7);
        Date dateFinish = calendar.getTime();

        order.setUserId(userId);
        order.setStatusId(statusId);
        order.setTotalCost(totalCost);
        order.setDateStart(dateStart);
        order.setDateFinish(dateFinish);
        orderDAO.insertOrder(order);
        Long orderId = orderDAO.getLastOrderIdByUserId(userId);
        List<Basket> baskets = basketDAO.getAllBasketsByUserId(userId);
        List<Product> productListToOrder = new ArrayList<>();
        Product productToOrder = null;
        Integer newCount;
        for (Basket basket : baskets) {
            productToOrder = productDAO.getProductById(basket.getProductId());
            if (productToOrder.getCount() - basket.getCount() >= 0) {
                newCount = productToOrder.getCount() - basket.getCount();
                productDAO.updateCountById(newCount, productToOrder.getId());
            } else {
                // productDAO.removeProduct(product.getId());
            }
            productToOrder.setCount(basket.getCount());
            productListToOrder.add(productToOrder);
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(productToOrder.getId());
            orderDetail.setCount(productToOrder.getCount());
            orderDetail.setCost(productToOrder.getCost());
            orderDetail.setProduct(productToOrder);
            orderDetailDAO.insertOrderDetail(orderDetail);
        }

        basketDAO.removeBasket(userId);
        request.getRequestDispatcher(INDEX_JSP).forward(request, response);
    }
}
