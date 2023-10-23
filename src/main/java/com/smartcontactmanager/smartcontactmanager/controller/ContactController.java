package com.smartcontactmanager.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartcontactmanager.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.smartcontactmanager.services.ContactService;

@RestController
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping("singlecontact/{contactId}")
    public ResponseEntity<Contact> getSingleContact(@PathVariable("contactId") String contactId){
        System.out.println("hit signle contanct");
        try{

            Contact singleContact = contactService.getSingleContact(Integer.parseInt(contactId));
            return ResponseEntity.status(HttpStatus.OK).body(singleContact);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
