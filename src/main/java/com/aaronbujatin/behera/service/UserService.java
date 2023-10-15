<<<<<<< HEAD
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
=======
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
>>>>>>> aa5d7930261bfcda661c8514cba1651c03c65717
