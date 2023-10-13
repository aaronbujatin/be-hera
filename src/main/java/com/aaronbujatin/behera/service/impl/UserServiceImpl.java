package com.aaronbujatin.behera.service.impl;

import com.aaronbujatin.behera.entity.User;
import com.aaronbujatin.behera.repository.UserRepository;
import com.aaronbujatin.behera.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Username id " + id + " was not found!")
        );
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = new User();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(updatedUser);
    }

    @Override
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User Successfully deleted";
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.existByUsername(username);
    }
}
