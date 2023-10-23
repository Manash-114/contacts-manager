package com.smartcontactmanager.smartcontactmanager.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.smartcontactmanager.dao.ContactRepository;
import com.smartcontactmanager.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.smartcontactmanager.entities.User;
import com.smartcontactmanager.smartcontactmanager.helper.FileSaveHelper;
import com.smartcontactmanager.smartcontactmanager.helper.Message;
import com.smartcontactmanager.smartcontactmanager.services.ContactService;
import com.smartcontactmanager.smartcontactmanager.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
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
            
            String imageName = new Date().getTime()+mFile.getOriginalFilename();
            contact.setContactImage(imageName);
            FileSaveHelper fileSaveHelper = new FileSaveHelper();
            if(fileSaveHelper.saveFile(mFile,imageName)){
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

    @GetMapping("/view/{page}")
    public String viewContact(Model m,Principal p, @PathVariable("page") int pageNo){
        String email = p.getName();
        User findUserEmail = userService.findUserEmail(email);
        
        //current page - > pageNo start with 0;
        //per page ->7 
        Pageable pageable = PageRequest.of(pageNo,7);
        
        Page<Contact> listOfContacts = contactService.findContactByUserIdPage(findUserEmail.getUserId(), pageable);
        
        m.addAttribute("contact_list", listOfContacts);
        m.addAttribute("title", "View");
        m.addAttribute("currentPage", pageNo);
        m.addAttribute("totalPages", listOfContacts.getTotalPages());
         m.addAttribute("singleContact", new Contact());
        if(pageNo >=listOfContacts.getTotalPages())
            System.out.println("invalid page no = "+pageNo);
        return "user/view_contact";
    }

    @GetMapping("/view/{page}/{contactId}")
    public String viewSingleContact(Model m,Principal p, @PathVariable("page") int pageNo, @PathVariable("contactId") int contactId){
        String email = p.getName();
        User findUserEmail = userService.findUserEmail(email);
        
        System.out.println("hit eye");
        //current page - > pageNo start with 0;
        //per page ->7 
        Pageable pageable = PageRequest.of(pageNo,7);
        
        Page<Contact> listOfContacts = contactService.findContactByUserIdPage(findUserEmail.getUserId(), pageable);
        
        Contact singleContact = contactService.getSingleContact(contactId);
        
        m.addAttribute("contact_list", listOfContacts);
        m.addAttribute("title", "View");
        m.addAttribute("currentPage", pageNo);
        m.addAttribute("totalPages", listOfContacts.getTotalPages());
        m.addAttribute("singleContact", singleContact);
        
        if(pageNo >=listOfContacts.getTotalPages())
            System.out.println("invalid page no = "+pageNo);
        return "user/view_contact";
    }
}
