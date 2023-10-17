package com.smartcontactmanager.smartcontactmanager.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String userName;

    @Column(unique = true)
    private String userEmail;
    private String userRole;
    private boolean userEnabled;
    private String userPassword;
    @Column(length = 500)
    private String userAbout;
    private String userImage;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<>();
    
    
    

    public User() {
    }
    public User(String userName, String userEmail, String userRole, boolean userEnabled, String userPassword,
            String userAbout, String userImage, List<Contact> contacts) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.userEnabled = userEnabled;
        this.userPassword = userPassword;
        this.userAbout = userAbout;
        this.userImage = userImage;
        this.contacts = contacts;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public boolean isUserEnabled() {
        return userEnabled;
    }
    public void setUserEnabled(boolean userEnabled) {
        this.userEnabled = userEnabled;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserAbout() {
        return userAbout;
    }
    public void setUserAbout(String userAbout) {
        this.userAbout = userAbout;
    }
    public String getUserImage() {
        return userImage;
    }
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
    public List<Contact> getContacts() {
        return contacts;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    
}
