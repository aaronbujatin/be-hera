<<<<<<< HEAD
package com.aaronbujatin.behera.repository;

import com.aaronbujatin.behera.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);


}
=======
package com.aaronbujatin.behera.repository;

import com.aaronbujatin.behera.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);


}
>>>>>>> aa5d7930261bfcda661c8514cba1651c03c65717
