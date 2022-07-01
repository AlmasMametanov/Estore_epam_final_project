package com.epam.estore.action;

import com.epam.estore.database.dao.impl.OrderDAOImpl;
import com.epam.estore.database.dao.impl.OrderDetailDAOImpl;
import com.epam.estore.database.dao.impl.ProductDAOImpl;
import com.epam.estore.database.dao.impl.StatusDAOImpl;
import com.epam.estore.database.dao.interfaces.OrderDAO;
import com.epam.estore.database.dao.interfaces.OrderDetailDAO;
import com.epam.estore.database.dao.interfaces.ProductDAO;
import com.epam.estore.database.dao.interfaces.StatusDAO;
import com.epam.estore.entity.Order;
import com.epam.estore.entity.OrderDetail;
import com.epam.estore.entity.Status;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.GET_ORDERS_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class GetOrdersAction implements Action {
    private OrderDAO orderDAO = new OrderDAOImpl();
    private StatusDAO statusDAO = new StatusDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);

        Boolean isAdmin = (Boolean) httpSession.getAttribute(IS_ADMIN);
        Integer localeId = (Integer) httpSession.getAttribute(LOCALE_ID);
        Long userId;
        if (isAdmin == true) {
            userId = Long.parseLong(request.getParameter(USER_ID));
            List<Status> statusList = statusDAO.getAllStatusByLocaleId(localeId);
            request.setAttribute(STATUS_LIST, statusList);
        } else {
            userId = (Long) httpSession.getAttribute(USER_ID);
        }
        List<Order> orders = orderDAO.getAllOrdersByUserId(userId);
        List<OrderDetail> orderDetails = null;
        for (Order order : orders) {
            orderDetails = orderDetailDAO.getAllOrderDetailByOrderId(order.getId());
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setProduct(productDAO.getProductById(orderDetail.getProductId()));
            }
            order.setOrderDetails(orderDetails);
            order.setStatus(statusDAO.getStatusById(order.getStatusId()));
        }
        request.setAttribute(ORDERS, orders);
        request.getRequestDispatcher(GET_ORDERS_JSP).forward(request, response);
    }
}
