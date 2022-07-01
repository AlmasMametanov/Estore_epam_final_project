package com.epam.estore.entity;

import java.io.Serializable;
import java.util.Objects;

public class Country implements Serializable {
    private Integer id;
    private Integer localeId;
    private String name;
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocaleId() {
        return localeId;
    }

    public void setLocaleId(Integer localeId) {
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
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(localeId, country.localeId) &&
                Objects.equals(name, country.name) &&
                Objects.equals(locale, country.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localeId, name, locale);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", localeId=" + localeId +
                ", name='" + name + '\'' +
                ", locale=" + locale +
                '}';
    }
}
