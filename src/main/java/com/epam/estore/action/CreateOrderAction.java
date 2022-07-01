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
import java.util.*;

import static com.epam.estore.util.constants.PageNameConstants.CREATE_ORDER_JSP;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;

public class CreateOrderAction implements Action {
    private ProductDAO productDAO = new ProductDAOImpl();
    private BasketDAO basketDAO = new BasketDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);

        Long userId = (Long) httpSession.getAttribute(USER_ID);
        List<Basket> baskets = basketDAO.getAllBasketsByUserId(userId);
        List<Product> productsFromBasket = new ArrayList<>();
        Product productFromBasket = null;
        Integer totalCost = 0;
        for (Basket basket : baskets) {
            productFromBasket = productDAO.getProductById(basket.getProductId());
            productFromBasket.setCount(basket.getCount());
            totalCost = totalCost + (productFromBasket.getCost() * productFromBasket.getCount());
            productsFromBasket.add(productFromBasket);
        }
        request.setAttribute(TOTAL_COST, totalCost);
        request.setAttribute(PRODUCTS_FROM_BASKET, productsFromBasket);
        request.getRequestDispatcher(CREATE_ORDER_JSP).forward(request, response);
    }
}
