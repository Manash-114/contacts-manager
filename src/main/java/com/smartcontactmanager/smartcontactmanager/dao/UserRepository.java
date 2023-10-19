package com.smartcontactmanager.smartcontactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontactmanager.smartcontactmanager.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{
    public User findByUserEmail(String email);

    @Query("select u from User u where u.userEmail = :email")
    public User getUserByUserEmail(@Param("email")String email);
}
