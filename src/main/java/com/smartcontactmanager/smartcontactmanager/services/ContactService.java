package com.smartcontactmanager.smartcontactmanager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.smartcontactmanager.dao.ContactRepository;
import com.smartcontactmanager.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.smartcontactmanager.entities.User;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getContactsByUserEmail(int  userId){
        List<Contact> listOfContacts = new ArrayList<>();

        listOfContacts =  contactRepository.findContactByUserId(userId);
        return listOfContacts;
    }

    public List<Contact> getContactsByUser(User user){
        List<Contact> listOfContacts = new ArrayList<>();

        listOfContacts =  contactRepository.findByUser(user);
        return listOfContacts;
    }

    public Page<Contact> findContactByUserIdPage(int  userId , Pageable pageable){
        Page<Contact> listOfContacts;

        listOfContacts = contactRepository.findContactByUserIdPage(userId, pageable);

        return listOfContacts;
    }
}
