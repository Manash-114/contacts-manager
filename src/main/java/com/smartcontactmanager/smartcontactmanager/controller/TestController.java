package com.smartcontactmanager.smartcontactmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartcontactmanager.smartcontactmanager.dao.UserRepository;
import com.smartcontactmanager.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.smartcontactmanager.entities.User;

@Controller
public class TestController {

    @Autowired
    private UserRepository userRepository;
  

    @GetMapping("/save")
    @ResponseBody
    public String saveUser(){
        Contact c = new Contact("m","n","w","i","fjdk","323","232","fjds",null);
        Contact c1 = new Contact("m","n","w","i","fjdk","323","232","fjds",null);
        User u = new User("manash","mnf@gmai.com","user",true,"1212","i am developer","default.png",null);
        c.setUser(u);
        c1.setUser(u);
        u.setContacts(List.of(c,c1));
        userRepository.save(u);
        return "done";
    }
}
