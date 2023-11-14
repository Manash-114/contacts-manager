package com.smartcontactmanager.smartcontactmanager.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.smartcontactmanager.dao.UserRepository;
import com.smartcontactmanager.smartcontactmanager.entities.User;
import com.smartcontactmanager.smartcontactmanager.helper.Message;
import com.smartcontactmanager.smartcontactmanager.services.EmailService;

@Controller
public class ForgotController {
    
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/forgot")
    public String openForm(){
        return "forgot_email_form";
    }

    @PostMapping("/send-otp")
    public String handleDoForgot(@RequestParam("email") String email , Model m, HttpSession session){
        User findByUserEmail = userRepository.findByUserEmail(email);
        
        if(findByUserEmail == null){
            Message message = new Message("user not found using this email", "danger");
            session.setAttribute("message", message);
            return "redirect:/forgot";
        }

        // generating otp 4 digit
        Random random = new Random(1111);
        int otp = random.nextInt(9998);
        session.setAttribute("message", new Message("We have sent a otp in your registerd email.", "success"));
        String subject = "OTP from SCM";
        boolean sendEmail = emailService.sendEmail(email, "OTP = "+otp, subject);
        if(sendEmail){
            session.setAttribute("otp", otp);
            m.addAttribute("email", email);
            return "verify_otp";
        }
            
        else
            return "redirect:/forgot";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") String otp , @RequestParam("email") String email, 
    HttpSession session,Model m){
        
        int otpfromForm = Integer.parseInt(otp);
        int otpfromsession = (Integer)session.getAttribute("otp");

        System.out.println(otpfromForm);
        System.out.println(otpfromsession);

        if(otpfromForm == otpfromsession){
            session.setAttribute("email", email);
            return "redirect:/change-password";
        }else{
            session.setAttribute("message", new Message("Enter a valid otp", "danger"));
            return "verify_otp";
        }
     
        
    }

    @GetMapping("/change-password")
    public String changePasswordView(){
        return "change_password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("password") String password , HttpSession session){
        String email = (String)session.getAttribute("email");
        User findByUserEmail = userRepository.findByUserEmail(email);
        findByUserEmail.setUserPassword(passwordEncoder.encode(password));
        findByUserEmail.setTermAndCondition(true);
        userRepository.save(findByUserEmail);
        return "redirect:/signin?change=password changed successfully";
    }
}
