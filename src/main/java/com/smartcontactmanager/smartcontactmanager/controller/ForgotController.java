package com.smartcontactmanager.smartcontactmanager.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.resource.HttpResource;

import com.smartcontactmanager.smartcontactmanager.dao.UserRepository;
import com.smartcontactmanager.smartcontactmanager.entities.User;
import com.smartcontactmanager.smartcontactmanager.helper.Message;
import com.smartcontactmanager.smartcontactmanager.payloads.ApiResponse;
import com.smartcontactmanager.smartcontactmanager.services.EmailService;

@Controller
public class ForgotController {
    
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    
  
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


    @PostMapping("/forgot-form")
    public ResponseEntity<?> forgotFormHandle(@RequestParam("email") String email, HttpSession session ){
        User findByUserEmail = userRepository.findByUserEmail(email);
        if(findByUserEmail == null){
            return new ResponseEntity<>(new ApiResponse("User not found",201),HttpStatus.OK);
        }

         // generating otp 4 digit

        Random random = new Random();
        int otp;
        otp = random.nextInt(1000,9999);
        String subject = "OTP from SCM";
        boolean sendEmail = emailService.sendEmail(email, "OTP = "+otp, subject);
        if(sendEmail){
            session.setAttribute("otp", otp);
            return new ResponseEntity<>(new ApiResponse("Otp send to your email",201),HttpStatus.OK);
        }else
            return new ResponseEntity<>(new ApiResponse("Internal server error",500),HttpStatus.OK);
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") String otp , @RequestParam("email") String email, 
    HttpSession session,Model m){
        
        int otpfromForm = Integer.parseInt(otp);
        int otpfromsession = (Integer)session.getAttribute("otp");
        if(otpfromForm == otpfromsession){
            session.setAttribute("email", email);
            return "redirect:/change-password";
        }else{
            session.setAttribute("message", new Message("Enter a valid otp", "danger"));
            return "verify_otp";
        }
     
        
    }


    @PostMapping("/verify-otp-new/{otp}")
    public ResponseEntity<?> verifyOtpNew(@PathVariable String otp ,HttpSession session){
       
        int otpfromForm = Integer.parseInt(otp);
        int otpfromsession = (Integer)session.getAttribute("otp");
        if(otpfromForm == otpfromsession){
            return new ResponseEntity<>(new ApiResponse("true", 200),HttpStatus.OK);
        }else{
           return new ResponseEntity<>(new ApiResponse("false", 200),HttpStatus.OK);
        }
     
        
    }

    @PostMapping("/change-password/{email}/{password}")
    public ResponseEntity<?> changePassword(@PathVariable String email , @PathVariable String password , HttpSession session){
        User findByUserEmail = userRepository.findByUserEmail(email);
        findByUserEmail.setUserPassword(passwordEncoder.encode(password));
        findByUserEmail.setTermAndCondition(true);
        userRepository.save(findByUserEmail);
        return new ResponseEntity<>(new ApiResponse("Password Changed Successfully ", 200),HttpStatus.OK);
    }
}
