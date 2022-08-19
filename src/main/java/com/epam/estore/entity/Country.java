package com.epam.estore.entity;

import java.io.Serializable;
import java.util.Objects;

public class Country implements Serializable {
    private Long id;
    private Long localeId;
    private String name;
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
