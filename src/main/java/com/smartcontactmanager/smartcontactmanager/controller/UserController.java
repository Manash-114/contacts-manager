package com.smartcontactmanager.smartcontactmanager.controller;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.smartcontactmanager.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.smartcontactmanager.entities.User;
import com.smartcontactmanager.smartcontactmanager.helper.FileSaveHelper;
import com.smartcontactmanager.smartcontactmanager.helper.Message;
import com.smartcontactmanager.smartcontactmanager.services.CloudinaryFile;
import com.smartcontactmanager.smartcontactmanager.services.ContactService;
import com.smartcontactmanager.smartcontactmanager.services.UserService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CloudinaryFile cloudinaryFile;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;

   

    //handler for userdashboard
    @GetMapping("/index")
    public String dashboard(Model m , Principal principal){

        String userEmail = principal.getName(); // help to get userUnique value through spring security
        User user = userService.getUserByEmail(userEmail);
        m.addAttribute("user", user);
        m.addAttribute("title", "Dashboard");
        return "user/user_dashboard";
    }

    //handler for open add contact form
    @GetMapping("/add-contact")
    public String addContact(Model m , Principal principal){
        m.addAttribute("title", "Add-Contact");
        User user = userService.getUserByEmail(principal.getName());
        m.addAttribute("user", user);
        m.addAttribute("contact", new Contact());
        return "user/add_contact";
    }

    //handler for addcontact form
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

    //handler for open viewContact
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

    
    //handler for open update form

    @GetMapping("/update/{contactId}")
    public String update(Principal p,Model m , @PathVariable("contactId") int contactId){
        User user = userService.findUserEmail(p.getName());
        Contact singleContact = contactService.getSingleContact(contactId);

        if(user.getUserId() != singleContact.getUser().getUserId()){
            return "error";
        }

        m.addAttribute("contact", singleContact);
            m.addAttribute("title", "Update");
            return "user/update_contact";
        
    }

    @PostMapping("/update-contact/{contactId}")
    public String updateContact(@Valid@ModelAttribute("contact") Contact contact, BindingResult result
    ,Model m,@RequestParam("file") MultipartFile mFile, HttpSession session, @PathVariable("contactId") int contactId,Principal p){
       
        if(result.hasErrors()){
            System.out.println(result);
            m.addAttribute("title", "Update");
            return "user/update_contact";
        }
        try{
            Contact oldContact = contactService.getSingleContact(contactId);
            String imageName = new Date().getTime()+mFile.getOriginalFilename();
            if(!mFile.isEmpty()){
                FileSaveHelper fileSaveHelper = new FileSaveHelper();
                String oldImageName = oldContact.getContactImage();
                if(fileSaveHelper.deleteFile(oldImageName)){
                   if(fileSaveHelper.saveFile(mFile, imageName)){
                       contact.setContactImage(imageName); 
                   }
                }
                
            }else{
                //no file update select
                contact.setContactImage(oldContact.getContactImage());
            }
                User user = userService.getUserByEmail(p.getName());
                contact.setUser(user);
                contact.setContactId(contactId);
                contactService.updateContact(contact);
                Message m1 = new Message("Contact Updated Successfully", "alert-success");
                session.setAttribute("message", m1);
                return "redirect:/user/index";
            
        }catch(Exception e){
            e.printStackTrace();
        }
        Message m1 = new Message("Contact not updated Successfully", "alert-warning");
        session.setAttribute("message", m1);
        return "redirect:/user/index";
    }

    @PostMapping("/is-verify/{email}")
    public ResponseEntity<?> updateIsVeriryEmail(@PathVariable String email){
        User user = userService.findUserEmail(email);
        user.setVerifyEmail(true);
        user.setTermAndCondition(true);
        userService.saveUser(user);
        return new ResponseEntity<>("done",HttpStatus.OK);
    }


    @PostMapping("/updateProfile")
    public String changeImage(@RequestParam("image") MultipartFile multipartFile , Principal p){

        
        String email = p.getName();
        User user = userService.findUserEmail(email);
        
        Map upload = cloudinaryFile.upload(multipartFile);
        System.out.println(upload.get("url"));
        user.setUserImage((String)upload.get("url"));
        user.setTermAndCondition(true);
        userService.saveUser(user);
        return "redirect:/user/index";
    }
    
}
