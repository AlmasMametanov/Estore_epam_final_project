package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.Basket;

import java.util.List;

public interface BasketDAO {
    void insertBasket(Basket basket);
    List<Basket> getAllBasketsByUserId(Long userId);
    void updateProductCountInBasket(Integer productCount, Long basketId);
    void removeBasketByUserId(Long productId, Long userId);
    void removeAllBasketsByUserId(Long userId);
}
