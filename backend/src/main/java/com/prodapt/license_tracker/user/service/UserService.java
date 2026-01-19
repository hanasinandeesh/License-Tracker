package com.prodapt.license_tracker.user.service;

import java.util.List;
import com.prodapt.license_tracker.user.entity.User;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(Integer userId);
}
