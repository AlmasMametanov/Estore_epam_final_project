package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.CategoryLocale;
import com.epam.estore.entity.Product;
import java.util.List;

public interface CategoryLocaleDAO {
    void insertCategoryLocale(CategoryLocale categoryLocale);
    List<CategoryLocale> getRootsOfCategory(Integer localeId);
    List<CategoryLocale> getSubcategoriesOfRootCategory(Integer parentId, Integer localeId);
    List<Product> getAllProductsByCategoryIdAndLocaleId(Integer subcategoryId, Integer localeId);
    void removeCategoryLocale(Integer categoryLocaleId);
}
