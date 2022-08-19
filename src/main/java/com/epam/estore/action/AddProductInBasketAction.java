package com.epam.estore.action;

import com.epam.estore.database.dao.impl.BasketDAOImpl;
import com.epam.estore.database.dao.interfaces.BasketDAO;
import com.epam.estore.entity.Basket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.INDEX_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class AddProductInBasketAction implements Action {
    private BasketDAO basketDAO = new BasketDAOImpl();
    private Basket basket = new Basket();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);

        Long productId = Long.parseLong(request.getParameter(PRODUCT_ID));
        Long userId = (Long) httpSession.getAttribute(USER_ID);
        List<Basket> baskets = basketDAO.getAllBasketsByUserId(userId);
        Boolean inBasket = isProductInBasket(baskets, productId);

        if (inBasket == false) {
            setValuesIntoBasketAndInsertIntoDatabase(basket, userId, request, response);
            response.sendRedirect(INDEX_JSP);
        } else {
            response.sendRedirect(INDEX_JSP);
        }
    }

    private Boolean isProductInBasket(List<Basket> baskets, Long productId) {
        for (Basket basket : baskets) {
            if (productId.equals(basket.getProductId())) {
                return true;
            }
        }
        return false;
    }

    private void setValuesIntoBasketAndInsertIntoDatabase(Basket basket, Long userId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        basket.setUserId(userId);
        basket.setProductId(Long.parseLong(request.getParameter(PRODUCT_ID)));
        basket.setCount(Integer.parseInt(request.getParameter(PRODUCT_COUNT)));
        basketDAO.insertBasket(basket);
    }
}
