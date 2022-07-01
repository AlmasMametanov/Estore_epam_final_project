package com.epam.estore.action;

import com.epam.estore.database.dao.impl.UserDAOImpl;
import com.epam.estore.database.dao.interfaces.UserDAO;
import com.epam.estore.entity.User;
import com.epam.estore.util.encodePassword.Encoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.epam.estore.util.constants.ErrorConstants.*;
import static com.epam.estore.util.constants.PageNameConstants.LOGIN_JSP;
import static com.epam.estore.util.constants.PageNameConstants.SIGNUP_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;
import static com.epam.estore.validator.Validator.validatePassword;
import static com.epam.estore.validator.Validator.validatePhoneNumber;

public class SignUpUserAction implements Action {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private User user = new User();
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        user.setFirstName(request.getParameter(FIRST_NAME));
        user.setLastName(request.getParameter(LAST_NAME));
        user.setLogin(request.getParameter(LOGIN));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            user.setBirthday(dateFormat.parse(request.getParameter(BIRTHDAY)));
        } catch (ParseException e) {
            logger.warn(e);
            throw new RuntimeException(e);
        }
        user.setPhoneNumber(request.getParameter(PHONE_NUMBER));
        user.setAddress(request.getParameter(ADDRESS));
        user.setPassword(Encoder.encodePassword(request.getParameter(PASSWORD)));

        if (!validatePhoneNumber(user.getPhoneNumber())) {
            request.setAttribute(PHONE_FORMAT_INCORRECT, ERROR_PHONE_NUMBER);
            request.getRequestDispatcher(SIGNUP_JSP).forward(request, response);
        } else if (!validatePassword(user.getPassword())) {
            request.setAttribute(PASSWORD_FORMAT_INCORRECT, ERROR_PASSWORD);
            request.getRequestDispatcher(SIGNUP_JSP).forward(request, response);
        } else {
            userDAO.insertUser(user);
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
        }
    }
}
