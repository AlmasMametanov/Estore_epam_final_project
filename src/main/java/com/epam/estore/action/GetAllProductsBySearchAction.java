package com.epam.estore.action;

import com.epam.estore.database.dao.impl.ProductDAOImpl;
import com.epam.estore.database.dao.interfaces.ProductDAO;
import com.epam.estore.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.PRODUCT_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class GetAllProductsBySearchAction implements Action {
    private ProductDAO productDAO = new ProductDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        String productName = request.getParameter(PRODUCT_NAME);
        Integer localeId = (Integer) httpSession.getAttribute(LOCALE_ID);
        List<Product> products = productDAO.getAllProductsByName(productName, localeId);
        if (products != null) {
            request.setAttribute(PRODUCTS, products);
            request.getRequestDispatcher(PRODUCT_JSP).forward(request, response);
        }
    }
}
