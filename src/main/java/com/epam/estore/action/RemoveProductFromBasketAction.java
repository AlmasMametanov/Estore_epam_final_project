package com.epam.estore.action;

import com.epam.estore.database.dao.impl.BasketDAOImpl;
import com.epam.estore.database.dao.interfaces.BasketDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.estore.util.constants.PageNameConstants.INDEX_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class RemoveProductFromBasketAction implements Action {
    private BasketDAO basketDAO = new BasketDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);

        Long productId = Long.parseLong(request.getParameter(PRODUCT_ID));
        Long userId = (Long) httpSession.getAttribute(USER_ID);
        basketDAO.removeBasketByUserId(productId, userId);
        request.getRequestDispatcher(INDEX_JSP).forward(request, response);
    }
}
