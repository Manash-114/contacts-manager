package com.smartcontactmanager.smartcontactmanager.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.smartcontactmanager.entities.User;
import com.smartcontactmanager.smartcontactmanager.helper.FileSaveHelper;
import com.smartcontactmanager.smartcontactmanager.helper.Message;
import com.smartcontactmanager.smartcontactmanager.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    @GetMapping("/index")
    public String dashboard(Model m , Principal principal){

        String userEmail = principal.getName(); // help to get userUnique value through spring security
        User user = userService.getUserByEmail(userEmail);
        m.addAttribute("user", user);
        m.addAttribute("title", "Dashboard");
        return "user/user_dashboard";
    }

    @GetMapping("/add-contact")
    public String addContact(Model m , Principal principal){
        m.addAttribute("title", "Add-Contact");
        User user = userService.getUserByEmail(principal.getName());
        m.addAttribute("user", user);
        m.addAttribute("contact", new Contact());
        return "user/add_contact";
    }

    @PostMapping("/process-contact")
    public String processForm(@Valid @ModelAttribute("contact") Contact contact , BindingResult result,Model m,Principal principal,
    @RequestParam("file") MultipartFile mFile, HttpSession session){
        
        User user = userService.getUserByEmail(principal.getName());
        if(result.hasErrors()){
            System.out.println(result);
            m.addAttribute("title", "Add-Contact");
            
            m.addAttribute("user", user);
            return "user/add_contact";
        }

        if(mFile.isEmpty()){
            System.out.println("file not selected");
            m.addAttribute("title", "Add-Contact");
            m.addAttribute("user", user);
            m.addAttribute("message", "picture not selected");
            return "user/add_contact";
        }

        try{
            contact.setUser(user);
            user.getContacts().add(contact);
            contact.setContactImage(mFile.getOriginalFilename());
            FileSaveHelper fileSaveHelper = new FileSaveHelper();
            if(fileSaveHelper.saveFile(mFile)){
                userService.saveUser(user);
                Message m1 = new Message("Contact Saved Successfully", "alert-success");
                session.setAttribute("message", m1);
                return "redirect:/user/index";
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        Message m1 = new Message("Contact not Saved Successfully", "alert-warning");
        session.setAttribute("message", m1);
        return "redirect:/user/index";
        
        
    }
}
