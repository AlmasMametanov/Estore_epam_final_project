package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.Status;
import java.util.List;

public interface StatusDAO {
    Status getStatusById(Long statusId);
    Long getStatusIdByNameAndLocaleId(String statusName, Long localeId);
    List<Status> getAllStatusByLocaleId(Long localeId);
}
