package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.Country;
import java.util.List;

public interface CountryDAO {
    List<Country> getAllCountryByLocaleId(Integer localeId);
}
