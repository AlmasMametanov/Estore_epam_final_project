package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.Product;
import java.util.List;

public interface ProductDAO {
    void insertProduct(Product product);
    Product getProductById(Long productId);
    List<Product> getAllProductsByName(String productName, Long localeId);
    void updateCountById(Integer count, Long productId);
    void updateProduct(Product product);
    void removeProduct(Long productId);
}
