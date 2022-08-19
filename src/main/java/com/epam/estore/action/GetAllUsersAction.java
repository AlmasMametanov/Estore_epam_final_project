package com.epam.estore.action;

import com.epam.estore.database.dao.impl.UserDAOImpl;
import com.epam.estore.database.dao.interfaces.UserDAO;
import com.epam.estore.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.ADMIN_PANEL_JSP;
import static com.epam.estore.util.constants.PageNameConstants.GET_ALL_USERS_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.USERS;

public class GetAllUsersAction implements Action {
    private UserDAO userDao = new UserDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userDao.getAllUsers();
        if (!users.isEmpty()) {
            request.setAttribute(USERS, users);
            request.getRequestDispatcher(GET_ALL_USERS_JSP).forward(request, response);
        } else {
            response.sendRedirect(ADMIN_PANEL_JSP);
        }
    }
}
