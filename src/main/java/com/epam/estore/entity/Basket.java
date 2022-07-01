package com.epam.estore.entity;

import java.io.Serializable;
import java.util.Objects;

public class Basket implements Serializable {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer count;
    private User user;
    private Product product;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(id, basket.id) &&
                Objects.equals(userId, basket.userId) &&
                Objects.equals(productId, basket.productId) &&
                Objects.equals(count, basket.count) &&
                Objects.equals(user, basket.user) &&
                Objects.equals(product, basket.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, count, user, product);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", count=" + count +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
