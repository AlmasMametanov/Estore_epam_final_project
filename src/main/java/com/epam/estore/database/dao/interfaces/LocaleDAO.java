package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.Locale;

import java.util.List;

public interface LocaleDAO {
    String getLocaleByShortName(String localeName);
    Integer getLocaleIdByName(String localeName);
    List<Locale> getAllLocale();
}
