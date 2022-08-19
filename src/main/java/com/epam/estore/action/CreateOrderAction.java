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

import static com.epam.estore.util.constants.PageNameConstants.*;
import static com.epam.estore.util.constants.ParameterNamesConstants.*;
import static com.epam.estore.util.constants.ErrorConstants.*;

public class CreateOrderAction implements Action {
    private ProductDAO productDAO = new ProductDAOImpl();
    private BasketDAO basketDAO = new BasketDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);

        Long userId = (Long) httpSession.getAttribute(USER_ID);
        List<Basket> baskets = basketDAO.getAllBasketsByUserId(userId);
        List<Product> productListFromBaskets = new ArrayList<>();
        Product product = null;
        Integer totalCost = 0;
        Boolean notEnoughProduct = null;
        for (Basket basket : baskets) {
            product = productDAO.getProductById(basket.getProductId());
            notEnoughProduct = isProductCountAvailable(product, basket, request, response);
            if (notEnoughProduct == true)
                break;
            totalCost = totalCost + (product.getCost() * product.getCount());
            productListFromBaskets.add(product);
        }
        pageToGoTo(notEnoughProduct, totalCost, productListFromBaskets, request, response);
    }

    private void pageToGoTo(Boolean notEnoughProduct, Integer totalCost, List<Product> productListFromBaskets,
                            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (notEnoughProduct == false) {
            request.setAttribute(TOTAL_COST, totalCost);
            request.setAttribute(PRODUCTS_FROM_BASKET, productListFromBaskets);
            request.getRequestDispatcher(CREATE_ORDER_JSP).forward(request, response);
        } else {
            request.getRequestDispatcher(INDEX_JSP).forward(request, response);
        }
    }

    private Boolean isProductCountAvailable(Product product, Basket basket, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (product.getCount() >= basket.getCount()) {
            product.setCount(basket.getCount());
            return false;
        } else if (product.getCount() > 0){
            basketDAO.updateProductCountInBasket(product.getCount(), basket.getId());
            request.setAttribute(PRODUCT_MORE_THEN_ZERO, ERROR_PRODUCT_MORE_THEN_ZERO);;
        } else {
            basketDAO.removeBasketByUserId(basket.getProductId(), basket.getUserId());
            request.setAttribute(PRODUCT_EQUAL_ZERO, ERROR_PRODUCT_EQUAL_ZERO);
        }
        return true;
    }
}
