package com.epam.estore.action;

import com.epam.estore.database.dao.impl.CategoryLocaleDAOImpl;
import com.epam.estore.database.dao.interfaces.CategoryLocaleDAO;
import com.epam.estore.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.PRODUCT_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class GetAllProductsByCategoryIdAction implements Action {
    private CategoryLocaleDAO categoryLocaleDAO = new CategoryLocaleDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        Integer categoryId = Integer.parseInt(request.getParameter(CATEGORY_PARENT_ID));
        Integer localeId = (Integer) httpSession.getAttribute(LOCALE_ID);
        List<Product> products = categoryLocaleDAO.getAllProductsByCategoryIdAndLocaleId(categoryId, localeId);
        request.setAttribute(PRODUCTS, products);

        if (products.size() != 0){
            request.getRequestDispatcher(PRODUCT_JSP).forward(request, response);
        }
    }
}
