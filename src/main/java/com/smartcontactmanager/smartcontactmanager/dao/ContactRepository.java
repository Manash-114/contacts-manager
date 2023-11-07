package com.smartcontactmanager.smartcontactmanager.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontactmanager.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.smartcontactmanager.entities.User;

import java.util.List;


public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query("from Contact as c where c.user.userId = :userid")
    public List<Contact> findContactByUserId(@Param("userid") int userId);
    
    public List<Contact> findByUser(User user);

    @Query("from Contact as c where c.user.userId = :userid")
    public Page<Contact> findContactByUserIdPage(@Param("userid") int userId , Pageable pageable);

    public List<Contact> findByContactNameContainingAndUser(String keywords ,User user);
}
