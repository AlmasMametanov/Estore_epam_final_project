package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.User;
import java.util.List;

public interface UserDAO {
    void insertUser(User user);
    List<User> getAllUsers();
    User getUserById(Long userId);
    User getUserByLoginPassword(String login, String password);
    void updateUserBanStatus(User user);
}
