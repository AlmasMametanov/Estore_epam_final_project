package com.epam.estore.action;

import com.epam.estore.database.dao.impl.UserDAOImpl;
import com.epam.estore.database.dao.interfaces.UserDAO;
import com.epam.estore.entity.User;
import com.epam.estore.util.encodePassword.Encoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.estore.util.constants.PageNameConstants.*;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class LogInUserAction implements Action {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);

        String email = request.getParameter(EMAIL);
        String encodePassword = Encoder.encodePassword(request.getParameter(PASSWORD));
        User user = userDAO.getUserByLoginPassword(email, encodePassword);
        if (user != null && user.getIsBanned() == false) {
            setAttributes(httpSession, user, email);
            checkIfAdmin(user, request, response);
        } else {
            request.getRequestDispatcher(INDEX_JSP).forward(request, response);
        }
    }

    private void setAttributes(HttpSession httpSession, User user, String email) {
        httpSession.setAttribute(USER, user);
        httpSession.setAttribute(USER_ID, user.getId());
        httpSession.setAttribute(FIRST_NAME, user.getFirstName());
        httpSession.setAttribute(LAST_NAME, user.getLastName());
        httpSession.setAttribute(EMAIL, email);
        httpSession.setAttribute(BIRTHDAY, user.getBirthday());
        httpSession.setAttribute(PHONE_NUMBER, user.getPhoneNumber());
        httpSession.setAttribute(ADDRESS, user.getAddress());
        httpSession.setAttribute(IS_ADMIN, user.getIsAdmin());
        httpSession.setAttribute(IS_BANNED, user.getIsBanned());
    }

    private void checkIfAdmin(User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (user.getIsAdmin() == true) {
            request.getRequestDispatcher(ADMIN_PANEL_JSP).forward(request, response);
        } else {
            request.getRequestDispatcher(INDEX_JSP).forward(request, response);
        }
    }
}
