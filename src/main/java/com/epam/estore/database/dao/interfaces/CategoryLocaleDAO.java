package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.CategoryLocale;
import com.epam.estore.entity.Product;
import java.util.List;

public interface CategoryLocaleDAO {
    void insertCategoryLocale(CategoryLocale categoryLocale);
    List<CategoryLocale> getRootsOfCategory(Long localeId);
    List<CategoryLocale> getSubcategoriesOfRootCategory(Long parentId, Long localeId);
    List<Product> getAllProductsByCategoryIdAndLocaleId(Long subcategoryId, Long localeId);
    void removeCategoryLocale(Long categoryLocaleId);
}
