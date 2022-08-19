package com.epam.estore.entity;

import java.io.Serializable;
import java.util.Objects;

public class Locale implements Serializable {
    private Long id;
    private String shortName;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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
        Locale locale = (Locale) o;
        return Objects.equals(id, locale.id) &&
                Objects.equals(shortName, locale.shortName) &&
                Objects.equals(name, locale.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, name);
    }

    @Override
    public String toString() {
        return "Locale{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
