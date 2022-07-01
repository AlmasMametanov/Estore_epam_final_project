package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.Category;

public interface CategoryDAO {
    void insertCategory(Category category);
    Integer getLastCategoryId();
    void removeCategory(Integer categoryId);
}
