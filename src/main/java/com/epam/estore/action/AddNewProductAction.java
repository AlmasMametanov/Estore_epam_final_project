package com.epam.estore.action;

import com.epam.estore.database.dao.impl.*;
import com.epam.estore.database.dao.interfaces.*;
import com.epam.estore.entity.CategoryLocale;
import com.epam.estore.entity.Country;
import com.epam.estore.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.ADD_NEW_PRODUCT_JSP;
import static com.epam.estore.util.constants.PageNameConstants.ADMIN_PANEL_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class AddNewProductAction implements Action {
    private ProductDAO productDAO = new ProductDAOImpl();
    private CountryDAO countryDAO = new CountryDAOImpl();
    private CategoryLocaleDAO categoryLocaleDAO = new CategoryLocaleDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession httpSession = request.getSession(true);

        Product product = new Product();
        String productName = request.getParameter(PRODUCT_NAME);
        if (productName != null) {
            product.setName(productName);
            product.setDescription(request.getParameter(PRODUCT_DESCRIPTION));
            product.setCost(Integer.parseInt(request.getParameter(PRODUCT_COST)));
            product.setCount(Integer.parseInt(request.getParameter(PRODUCT_COUNT)));
            product.setCountryId(Integer.parseInt(request.getParameter(COUNTRY_ID)));
            product.setCategoryId(Integer.parseInt(request.getParameter(CATEGORY_ID)));
            productDAO.insertProduct(product);
            request.getRequestDispatcher(ADMIN_PANEL_JSP).forward(request, response);
        } else {
            Integer localeId = (Integer) httpSession.getAttribute(LOCALE_ID);
            List<Country> countries = countryDAO.getAllCountryByLocaleId(localeId);
            request.setAttribute(COUNTRIES, countries);
            List<CategoryLocale> categoryLocale = categoryLocaleDAO.getRootsOfCategory(localeId);
            request.setAttribute(CATEGORIES, categoryLocale);
            request.getRequestDispatcher(ADD_NEW_PRODUCT_JSP).forward(request, response);
        }
    }
}
