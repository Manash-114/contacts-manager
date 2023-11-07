package com.smartcontactmanager.smartcontactmanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontactmanager.smartcontactmanager.dao.ContactRepository;
import com.smartcontactmanager.smartcontactmanager.dao.UserRepository;
import com.smartcontactmanager.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.smartcontactmanager.entities.User;

@RestController
public class SearchController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    //search handler
    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(Principal p , @PathVariable String query ){
        User user = userRepository.findByUserEmail(p.getName());
        List<Contact> allContact = contactRepository.findByContactNameContainingAndUser(query, user);
        return new ResponseEntity<>(allContact,HttpStatus.OK);
    }
}
