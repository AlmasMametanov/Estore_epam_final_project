package com.epam.estore.action;

import com.epam.estore.database.dao.impl.CategoryDAOImpl;
import com.epam.estore.database.dao.impl.CategoryLocaleDAOImpl;
import com.epam.estore.database.dao.interfaces.CategoryDAO;
import com.epam.estore.database.dao.interfaces.CategoryLocaleDAO;
import com.epam.estore.entity.Category;
import com.epam.estore.entity.CategoryLocale;
import com.epam.estore.entity.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.ADD_NEW_CATEGORY_JSP;
import static com.epam.estore.util.constants.PageNameConstants.ADMIN_PANEL_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class AddNewCategoryAction implements Action {
    private CategoryDAO categoryDAO = new CategoryDAOImpl();
    private CategoryLocaleDAO categoryLocaleDAO = new CategoryLocaleDAOImpl();
    private Category category = new Category();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        String categoryName = request.getParameter(CATEGORY_NAME);
        if (categoryName != null) {
            List<String> categoryLocaleNames = Arrays.asList(request.getParameterValues(CATEGORY_NAME));
            setValuesIntoCategoryAndInsertIntoDatabase(request);
            Long newCategoryLocaleId = categoryDAO.getLastCategoryId();
            List<Locale> locales = (List<Locale>) httpSession.getAttribute(LOCALES);
            setValuesIntoCategoryLocaleAndInsertIntoDatabase(categoryLocaleNames, newCategoryLocaleId, locales);
            request.getRequestDispatcher(ADMIN_PANEL_JSP).forward(request, response);
        } else {
            Long localeId = (Long) httpSession.getAttribute(LOCALE_ID);
            List<CategoryLocale> categories = categoryLocaleDAO.getRootsOfCategory(localeId);
            request.setAttribute(CATEGORIES, categories);
            request.getRequestDispatcher(ADD_NEW_CATEGORY_JSP).forward(request, response);
        }
    }

    private void setValuesIntoCategoryAndInsertIntoDatabase(HttpServletRequest request) {
        category.setParentId(Long.valueOf(request.getParameter(CATEGORY_PARENT_ID)));
        categoryDAO.insertCategory(category);
    }

    private void setValuesIntoCategoryLocaleAndInsertIntoDatabase(List<String> categoryLocaleNames, Long newCategoryLocaleId,
                                                                  List<Locale> locales) {
        CategoryLocale categoryLocale;
        for (int i = 0; i < categoryLocaleNames.size(); i++) {
            categoryLocale = new CategoryLocale();
            categoryLocale.setCategoryId(newCategoryLocaleId);
            categoryLocale.setLocaleId(locales.get(i).getId());
            categoryLocale.setName(categoryLocaleNames.get(i));
            categoryLocaleDAO.insertCategoryLocale(categoryLocale);
        }
    }
}
