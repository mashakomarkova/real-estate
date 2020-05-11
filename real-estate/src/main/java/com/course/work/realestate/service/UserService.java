package com.course.work.realestate.service;

import com.course.work.realestate.entity.User;

public interface UserService {
    User findUserByUsername(String username);
    void createUser(User user);
}
