package com.epam.estore.action;

import com.epam.estore.database.dao.impl.LocaleDAOImpl;
import com.epam.estore.database.dao.interfaces.LocaleDAO;
import com.epam.estore.entity.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.INDEX_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class ChangeLocaleAction implements Action {
    private LocaleDAO localeDAO = new LocaleDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);

        String selectedLanguage = request.getParameter(SELECTED_LOCALE);
        Long localeId = localeDAO.getLocaleIdByName(selectedLanguage);
        List<Locale> locales = localeDAO.getAllLocale();
        httpSession.setAttribute(LOCALE, selectedLanguage);
        httpSession.setAttribute(LOCALE_ID, localeId);
        httpSession.setAttribute(LOCALES, locales);
        response.sendRedirect(INDEX_JSP);
    }
}
