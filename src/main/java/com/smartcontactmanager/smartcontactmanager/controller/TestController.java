package com.smartcontactmanager.smartcontactmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartcontactmanager.smartcontactmanager.dao.UserRepository;
import com.smartcontactmanager.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.smartcontactmanager.entities.User;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserRepository userRepository;
  

    // @GetMapping("/save")
    // @ResponseBody
    // public String saveUser(){
    //     Contact c = new Contact("m","n","w","i","fjdk","323","232","fjds",null);
    //     Contact c1 = new Contact("m","n","w","i","fjdk","323","232","fjds",null);
    //     User u = new User("manash","mnf@gmai.com","user",true,"1212","i am developer","default.png",null);
    //     c.setUser(u);
    //     c1.setUser(u);
    //     u.setContacts(List.of(c,c1));
    //     userRepository.save(u);
    //     return "done";
    // }

    @GetMapping("/manash")
    public void manash(@RequestParam("m") String urlvalue, @RequestParam String k){
        System.out.println("second = "+k);
        System.out.println("Url value = "+urlvalue);
    }


    @GetMapping("/t1")
    public ResponseEntity<?> test(){
        int i = 5;
        if(i%2==0)
            return new ResponseEntity<>("Done",HttpStatus.OK);
        else    
            return new ResponseEntity<>("not done",HttpStatus.CREATED);
    }

}
