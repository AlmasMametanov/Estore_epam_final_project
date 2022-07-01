package com.epam.estore.entity;

import java.io.Serializable;
import java.util.Objects;

public class Category implements Serializable {
    private Integer id;
    private Integer parentId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(parentId, category.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", parentId=" + parentId +
                '}';
    }
}
