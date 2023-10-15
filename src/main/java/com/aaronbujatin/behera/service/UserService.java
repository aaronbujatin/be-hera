package com.aaronbujatin.behera.service;

import com.aaronbujatin.behera.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User getUserById(Long id);

    List<User> getAllUser();

    User updateUser(User user);

    String deleteUser(Long id);

    boolean isUsernameExist(String username);

    User getUser();




}
