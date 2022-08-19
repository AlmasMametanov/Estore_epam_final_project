package com.epam.estore.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class CategoryLocale implements Serializable {
    private Long categoryId;
    private Long localeId;
    private String name;
    private Locale locale;
    private CategoryLocale categoryLocale;
    private Category category;
    private List<CategoryLocale> subcategories;

    public List<CategoryLocale> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<CategoryLocale> subcategories) {
        this.subcategories = subcategories;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryLocale getCategoryLocale() {
        return categoryLocale;
    }

    public void setCategoryLocale(CategoryLocale categoryLocale) {
        this.categoryLocale = categoryLocale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getLocaleId() {
        return localeId;
    }

    public void setLocaleId(Long localeId) {
        this.localeId = localeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryLocale that = (CategoryLocale) o;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(localeId, that.localeId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(locale, that.locale) &&
                Objects.equals(categoryLocale, that.categoryLocale) &&
                Objects.equals(category, that.category) &&
                Objects.equals(subcategories, that.subcategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, localeId, name, locale, categoryLocale, category, subcategories);
    }

    @Override
    public String toString() {
        return "CategoryLocale{" +
                "categoryId=" + categoryId +
                ", localeId=" + localeId +
                ", name='" + name + '\'' +
                ", locale=" + locale +
                ", categoryLocale=" + categoryLocale +
                ", category=" + category +
                ", subcategories=" + subcategories +
                '}';
    }
}
