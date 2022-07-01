package com.epam.estore.entity;

import java.io.Serializable;
import java.util.Objects;

public class Status implements Serializable {
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
        Status status = (Status) o;
        return Objects.equals(id, status.id) &&
                Objects.equals(localeId, status.localeId) &&
                Objects.equals(name, status.name) &&
                Objects.equals(locale, status.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localeId, name, locale);
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", localeId=" + localeId +
                ", name='" + name + '\'' +
                ", locale=" + locale +
                '}';
    }
}
