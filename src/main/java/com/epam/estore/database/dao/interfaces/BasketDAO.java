package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.Basket;

import java.util.List;

public interface BasketDAO {
    void insertBasket(Basket basket);
    List<Basket> getAllBasketsByUserId(Long userId);
    void removeProductFromBasket(Long productId, Long userId);
    void removeBasket(Long userId);
}
