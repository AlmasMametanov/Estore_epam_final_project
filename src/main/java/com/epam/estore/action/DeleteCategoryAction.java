package com.epam.estore.action;

import com.epam.estore.database.dao.impl.CategoryDAOImpl;
import com.epam.estore.database.dao.impl.CategoryLocaleDAOImpl;
import com.epam.estore.database.dao.interfaces.CategoryDAO;
import com.epam.estore.database.dao.interfaces.CategoryLocaleDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.estore.util.constants.PageNameConstants.ADMIN_PANEL_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.CATEGORY_ID;

public class DeleteCategoryAction implements Action {
    private CategoryLocaleDAO categoryLocaleDAO = new CategoryLocaleDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long categoryId = Long.valueOf(request.getParameter(CATEGORY_ID));
        categoryLocaleDAO.removeCategoryLocale(categoryId);
        categoryDAO.removeCategory(categoryId);
        response.sendRedirect(ADMIN_PANEL_JSP);
    }
}
