package com.smartcontactmanager.smartcontactmanager.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String handleSignUpform(@Valid @ModelAttribute("user") User user ,
                                    @RequestParam(value = "agreement",defaultValue = "false") 
                                    boolean termAndCondition,
                                    Model m,
                                    HttpSession session,
                                    BindingResult result){
        try{
            if(!termAndCondition)
                throw new Exception("You have not agreed the term and condition");
            

            System.out.println(result);
            user.setUserEnabled(true);
            user.setUserRole("USER");
            user.setUserImage("default.png");
            
            userService.saveUser(user);
            m.addAttribute("user", new User());
            session.setAttribute("message", new Message("Successfully Registerd", "alert-success"));
            return "signup";
                    
        }catch(Exception e){
            e.printStackTrace();
            m.addAttribute("user", user);
            session.setAttribute ("message", new Message("Something went wrong"+e.getMessage(), "alert-danger"));
            return "signup";
        }
       
   }

}
