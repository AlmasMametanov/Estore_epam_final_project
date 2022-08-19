package com.epam.estore.action;

import com.epam.estore.database.dao.impl.ProductDAOImpl;
import com.epam.estore.database.dao.interfaces.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.estore.util.constants.PageNameConstants.ADMIN_PANEL_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class DeleteProductAction implements Action {
    private ProductDAO productDAO = new ProductDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.valueOf(request.getParameter(PRODUCT_ID));
        productDAO.removeProduct(productId);
        response.sendRedirect(ADMIN_PANEL_JSP);
    }
}
