package com.epam.estore.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.estore.util.constants.PageNameConstants.INDEX_JSP;

public class LogOutUserAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession(true);
        httpSession.invalidate();
        response.sendRedirect(INDEX_JSP);
    }
}
