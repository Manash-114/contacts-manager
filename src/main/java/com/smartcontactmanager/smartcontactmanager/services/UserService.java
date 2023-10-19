package com.smartcontactmanager.smartcontactmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.smartcontactmanager.dao.UserRepository;
import com.smartcontactmanager.smartcontactmanager.entities.User;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findUserEmail(String email){
        return userRepository.findByUserEmail(email);
    }

    public User getUserByEmail(String email){
        return userRepository.getUserByUserEmail(email);
    }
}
