package com.example.backend0.service;

import com.example.backend0.entity.User;
import com.example.backend0.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description
 **/
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User save(User user){
        if(user!=null){
            return userRepository.save(user);
        }
        return null;
    }
}
