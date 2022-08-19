package com.epam.estore.action;

import com.epam.estore.database.dao.impl.CategoryLocaleDAOImpl;
import com.epam.estore.database.dao.interfaces.CategoryLocaleDAO;
import com.epam.estore.entity.CategoryLocale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.INDEX_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class GetCategoriesAction implements Action {
    private CategoryLocaleDAO categoryLocaleDAO = new CategoryLocaleDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        Long localeId = (Long) httpSession.getAttribute(LOCALE_ID);
        List<CategoryLocale> categoryLocale = categoryLocaleDAO.getRootsOfCategory(localeId);
        if (!categoryLocale.isEmpty()) {
            request.setAttribute(CATEGORIES, categoryLocale);
            request.getRequestDispatcher(INDEX_JSP).forward(request, response);
        } else {
            response.sendRedirect(INDEX_JSP);
        }
    }
}
