package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.Status;
import java.util.List;

public interface StatusDAO {
    Status getStatusById(Integer statusId);
    Integer getStatusIdByNameAndLocaleId(String statusName, Integer localeId);
    List<Status> getAllStatusByLocaleId(Integer localeId);
}
