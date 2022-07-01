package com.epam.estore.action;

import com.epam.estore.database.dao.impl.OrderDAOImpl;
import com.epam.estore.database.dao.interfaces.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.estore.util.constants.PageNameConstants.ADMIN_PANEL_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class ChangeOrderStatusAction implements Action {

    private OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer statusId = Integer.parseInt(request.getParameter(STATUS_ID));
        Long orderId = Long.parseLong(request.getParameter(ORDER_ID));

        orderDAO.updateOrderStatusByOrderId(statusId, orderId);
        request.getRequestDispatcher(ADMIN_PANEL_JSP).forward(request, response);
    }
}
