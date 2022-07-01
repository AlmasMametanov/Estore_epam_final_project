package com.epam.estore.action;

import com.epam.estore.database.dao.impl.CategoryLocaleDAOImpl;
import com.epam.estore.database.dao.impl.CountryDAOImpl;
import com.epam.estore.database.dao.impl.ProductDAOImpl;
import com.epam.estore.database.dao.interfaces.CategoryLocaleDAO;
import com.epam.estore.database.dao.interfaces.CountryDAO;
import com.epam.estore.database.dao.interfaces.ProductDAO;
import com.epam.estore.entity.CategoryLocale;
import com.epam.estore.entity.Country;
import com.epam.estore.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.*;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class ChangeProductDataAction implements Action {
    ProductDAO productDAO = new ProductDAOImpl();
    CountryDAO countryDAO = new CountryDAOImpl();
    CategoryLocaleDAO categoryLocaleDAO = new CategoryLocaleDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        String productName = request.getParameter(PRODUCT_NAME);
        if (productName != null) {
            Product product = new Product();
            product.setName(request.getParameter(PRODUCT_NAME));
            product.setDescription(request.getParameter(PRODUCT_DESCRIPTION));
            product.setCost(Integer.parseInt(request.getParameter(PRODUCT_COST)));
            product.setCount(Integer.parseInt(request.getParameter(PRODUCT_COUNT)));
            product.setCountryId(Integer.parseInt(request.getParameter(COUNTRY_ID)));
            product.setCategoryId(Integer.parseInt(request.getParameter(CATEGORY_ID)));
            product.setId(Long.parseLong(request.getParameter(PRODUCT_ID)));
            productDAO.updateProduct(product);
            request.getRequestDispatcher(ADMIN_PANEL_JSP).forward(request, response);
        } else {
            Integer localeId = (Integer) httpSession.getAttribute(LOCALE_ID);
            List<Country> countries = countryDAO.getAllCountryByLocaleId(localeId);
            request.setAttribute(COUNTRIES, countries);
            List<CategoryLocale> categoryLocale = categoryLocaleDAO.getRootsOfCategory(localeId);
            request.setAttribute(CATEGORIES, categoryLocale);
            Long productId = Long.parseLong(request.getParameter(PRODUCT_ID));
            Product product = productDAO.getProductById(productId);
            request.setAttribute(PRODUCT, product);
            request.getRequestDispatcher(CHANGE_PRODUCT_JSP).forward(request, response);
        }

    }
}
