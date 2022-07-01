package com.epam.estore.action;

import com.epam.estore.database.dao.impl.BasketDAOImpl;
import com.epam.estore.database.dao.impl.ProductDAOImpl;
import com.epam.estore.database.dao.interfaces.BasketDAO;
import com.epam.estore.database.dao.interfaces.ProductDAO;
import com.epam.estore.entity.Basket;
import com.epam.estore.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.estore.util.constants.PageNameConstants.BASKET_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class GetAllProductsOfBasketByUserIdAction implements Action {
    private BasketDAO basketDAO = new BasketDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        Long userId = (Long) httpSession.getAttribute(USER_ID);
        List<Basket> baskets = basketDAO.getAllBasketsByUserId(userId);
        List<Product> productsFromBasketByUserId = new ArrayList<>();
        Product productFromBasket = null;
        for (Basket basket : baskets) {
            productFromBasket = productDAO.getProductById(basket.getProductId());
            productFromBasket.setCount(basket.getCount());
            productsFromBasketByUserId.add(productFromBasket);
        }
        request.setAttribute(PRODUCTS_FROM_BASKET_BY_USER_ID, productsFromBasketByUserId);
        request.getRequestDispatcher(BASKET_JSP).forward(request, response);
    }

}
