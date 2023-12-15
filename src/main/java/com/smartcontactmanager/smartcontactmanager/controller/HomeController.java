package com.smartcontactmanager.smartcontactmanager.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.smartcontactmanager.smartcontactmanager.entities.User;
import com.smartcontactmanager.smartcontactmanager.helper.Message;
import com.smartcontactmanager.smartcontactmanager.payloads.ApiResponse;
import com.smartcontactmanager.smartcontactmanager.services.CloudinaryFile;
import com.smartcontactmanager.smartcontactmanager.services.EmailService;
import com.smartcontactmanager.smartcontactmanager.services.UserService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String home(Model m){
        m.addAttribute("title", "Home-Smart Contact Manager");
        return "home";
    }
    
    @GetMapping("/signup")
    public String signUp(Model m){
        m.addAttribute("title", "SignUp");
        User user = new User();
        m.addAttribute("user", user);
        return "signup-new";
    }

    @PostMapping("/signup-submit")
    public String handleSignUpform(@Valid @ModelAttribute("user") User user , BindingResult result,
                                    Model m,
                                    HttpSession session
                                    ){
        
        if(result.hasErrors()){
                 return "signup-new";
        }
        
        try{ 
            user.setUserEnabled(true);
            user.setUserRole("ROLE_USER");
            user.setUserImage("https://res.cloudinary.com/dkzsuybqf/image/upload/v1702294861/profile_mui2ij.png");
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            if(userService.findUserEmail(user.getUserEmail()) != null){
                session.setAttribute("message", new Message("Email already Exists.Try with different email id", "alert-danger"));
                return "signup-new";
            }
            userService.saveUser(user);
            m.addAttribute("user", new User());
            session.setAttribute("message", new Message("Registration Successfully. Login to continue", "alert-success"));
            return "signup-new";
                    
        }catch(Exception e){
            e.printStackTrace();
            m.addAttribute("user", user);
            session.setAttribute ("message", new Message("Something went wrong ! Server error ! Try Again", "alert-danger"));
            return "signup-new";
        }
       
   }

   @GetMapping("/signin")
   public String login(Model m){
        m.addAttribute("title", "Sign In");
        return "login-new";
   }

   @PostMapping("/verify-signup-otp/{otp}")
   public ResponseEntity<?> verifyOtpNew(@PathVariable String otp ,HttpSession session){
      
       int otpfromForm = Integer.parseInt(otp);
       int otpfromsession = (Integer)session.getAttribute("sign-up-otp");
       if(otpfromForm == otpfromsession){
           return new ResponseEntity<>(new ApiResponse("true", 200),HttpStatus.OK);
       }else{
          return new ResponseEntity<>(new ApiResponse("false", 200),HttpStatus.OK);
       }
    
       
   }
   @PostMapping("/signup-otp/{email}")
   public ResponseEntity<?> sendOtpToMail(@PathVariable String email , HttpSession session){
        Random random = new Random();
        int number;
        number = random.nextInt(1000,9999);
        session.setAttribute("sign-up-otp", number);

        boolean sendEmail = emailService.sendEmail(email, "Your Otp = "+number, "Registration Verificattion");
        if(sendEmail){
            System.out.println("mail send to ");
            return new ResponseEntity<>(new ApiResponse("true", 200),HttpStatus.OK);
        }
           
        else{
            System.out.println("mail not send");
            return new ResponseEntity<>(new ApiResponse("false", 200), HttpStatus.OK);
        }
            
    }


    @PostMapping("/donate")
    @ResponseBody
    public String donate(@RequestBody Map<String,Object> data) throws RazorpayException{
        System.out.println(data.get("amount"));

        int amt = Integer.parseInt(data.get("amount").toString());

        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_ne8njhlSIl25ZY", "cwglVjpWNKlEHujh9mopKF9l");

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",amt*100);
        orderRequest.put("currency","INR");
        orderRequest.put("receipt", "receipt#1");
        JSONObject notes = new JSONObject();
        notes.put("notes_key_1","Tea, Earl Grey, Hot");
        orderRequest.put("notes",notes);

        Order order = razorpayClient.orders.create(orderRequest);
        System.out.println(order);
        return order.toString();
    }
    
    

}
