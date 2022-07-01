package com.epam.estore.action;

import com.epam.estore.database.dao.impl.UserDAOImpl;
import com.epam.estore.database.dao.interfaces.UserDAO;
import com.epam.estore.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.estore.util.constants.PageNameConstants.GET_USER_DATA_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class GetUserByIdAction implements Action {
    private UserDAO userDAO = new UserDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.valueOf(request.getParameter(USER_ID));
        User user = userDAO.getUserById(userId);
        if (user != null) {
            request.setAttribute(USER, user);
            request.getRequestDispatcher(GET_USER_DATA_JSP).forward(request, response);
        }
    }
}
