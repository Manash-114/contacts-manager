package com.smartcontactmanager.smartcontactmanager.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.smartcontactmanager.entities.User;
import com.smartcontactmanager.smartcontactmanager.helper.Message;
import com.smartcontactmanager.smartcontactmanager.services.UserService;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/")
    public String home(Model m){
        m.addAttribute("title", "Home-Smart Contact Manager");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model m){
        m.addAttribute("title", "About");
        return "about";
    }

    @GetMapping("/signup")
    public String signUp(Model m){
        m.addAttribute("title", "SignUp");
        User user = new User();
        m.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/do_register")
    public String handleSignUpform(@Valid @ModelAttribute("user") User user , BindingResult result,
                                    Model m,
                                    HttpSession session
                                    ){
        
        
        if(result.hasErrors()){
                 return "signup";
        }
        
        try{ 
            user.setUserEnabled(true);
            user.setUserRole("USER");
            user.setUserImage("default.png");
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            if(userService.findUserEmail(user.getUserEmail()) != null){
                session.setAttribute("message", new Message("Email already Exists.Try with different email id", "alert-danger"));
                return "signup";
            }
            userService.saveUser(user);
            m.addAttribute("user", new User());
            session.setAttribute("message", new Message("Registration Successfully. Login to continue", "alert-success"));
            return "signup";
                    
        }catch(Exception e){
            e.printStackTrace();
            m.addAttribute("user", user);
            session.setAttribute ("message", new Message("Something went wrong ! Server error ! Try Again", "alert-danger"));
            return "signup";
        }
       
   }

}
