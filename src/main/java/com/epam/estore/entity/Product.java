package com.epam.estore.entity;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Integer cost;
    private Integer count;
    private Integer countryId;
    private Integer categoryId;
    private Country country;
    private CategoryLocale categoryLocale;
    private String countryName;
    private String categoryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public CategoryLocale getCategoryLocale() {
        return categoryLocale;
    }

    public void setCategoryLocale(CategoryLocale categoryLocale) {
        this.categoryLocale = categoryLocale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(cost, product.cost) &&
                Objects.equals(count, product.count) &&
                Objects.equals(countryId, product.countryId) &&
                Objects.equals(categoryId, product.categoryId) &&
                Objects.equals(country, product.country) &&
                Objects.equals(categoryLocale, product.categoryLocale) &&
                Objects.equals(countryName, product.countryName) &&
                Objects.equals(categoryName, product.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, cost, count, countryId, categoryId, country, categoryLocale, countryName, categoryName);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", count=" + count +
                ", countryId=" + countryId +
                ", categoryId=" + categoryId +
                ", country=" + country +
                ", categoryLocale=" + categoryLocale +
                ", countryName=" + countryName +
                ", categoryName=" + categoryName +
                '}';
    }
}
