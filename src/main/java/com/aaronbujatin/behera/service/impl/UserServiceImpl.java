package com.aaronbujatin.behera.service.impl;

import com.aaronbujatin.behera.entity.Role;
import com.aaronbujatin.behera.entity.User;
import com.aaronbujatin.behera.exception.InvalidArgumentException;
import com.aaronbujatin.behera.repository.RoleRepository;
import com.aaronbujatin.behera.repository.UserRepository;
import com.aaronbujatin.behera.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        Boolean isUsernameExist = userRepository.existsByUsername(user.getUsername());

        if(!isUsernameExist){
            Optional<Role> role = roleRepository.findByName("ROLE_USER");
            user.setRoles(Collections.singleton(role.orElseThrow(() -> new InvalidArgumentException("Role not found!"))));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new InvalidArgumentException("Username already exist!");
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
        return userRepository.existsByUsername(username);
    }
}
