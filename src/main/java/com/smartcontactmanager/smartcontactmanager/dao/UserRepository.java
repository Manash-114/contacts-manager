package com.smartcontactmanager.smartcontactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcontactmanager.smartcontactmanager.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{
    
}
