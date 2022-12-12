package com.example.auth;

import com.example.auth.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository {

    public User findByUsername(String username) {
        User user = new User();
        user.setId("1");
        user.setUsername(username);
        user.setPassword("123");
        return user;
    }

}
