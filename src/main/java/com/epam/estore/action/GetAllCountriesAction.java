package com.epam.estore.action;

import com.epam.estore.database.dao.impl.CountryDAOImpl;
import com.epam.estore.database.dao.impl.LocaleDAOImpl;
import com.epam.estore.database.dao.interfaces.CountryDAO;
import com.epam.estore.database.dao.interfaces.LocaleDAO;
import com.epam.estore.entity.Country;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.ADD_NEW_PRODUCT_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class GetAllCountriesAction implements Action {
    private CountryDAO countryDAO = new CountryDAOImpl();
    private LocaleDAO localeDAO = new LocaleDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String localeShortName = request.getParameter(LOCALE_SHORT_NAME);
        Integer localeId = localeDAO.getLocaleIdByName(localeShortName);
        List<Country> countries = countryDAO.getAllCountryByLocaleId(localeId);
        request.setAttribute(COUNTRIES, countries);
        request.getRequestDispatcher(ADD_NEW_PRODUCT_JSP).forward(request, response);
    }
}
