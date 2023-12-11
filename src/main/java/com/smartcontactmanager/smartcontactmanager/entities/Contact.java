package com.smartcontactmanager.smartcontactmanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contactId;
    
    @NotBlank(message = "please enter contact name")
    private String contactName;
    @NotBlank(message = "please enter nick name")
    private String contactNickname;
    @NotBlank(message = "please enter work details")
    private String contactWork;

    private String contactImage;
    @Column(length = 500)

    @Size(min = 10,max = 10 , message = "Enter a valid mobile number")
    private String contactPhoneNumber;
    @Size(min = 10 ,max = 10 , message = "Enter a valid mobile number")
    private String contactAlternatePhoneNumber;
    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "Enter a valid email address")
    private String contactEmail;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    
    @JsonIgnore
    private User user;

    public Contact() {
    }

    
    public Contact(String contactName, String contactNickname, String contactWork, String contactImage, String contactPhoneNumber, String contactAlternatePhoneNumber,
            String contactEmail, User user) {
        this.contactName = contactName;
        this.contactNickname = contactNickname;
        this.contactWork = contactWork;
        this.contactImage = contactImage;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactAlternatePhoneNumber = contactAlternatePhoneNumber;
        this.contactEmail = contactEmail;
        this.user = user;
    }


    public int getContactId() {
        return contactId;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public String getContactNickname() {
        return contactNickname;
    }
    public void setContactNickname(String contactNickname) {
        this.contactNickname = contactNickname;
    }
    public String getContactWork() {
        return contactWork;
    }
    public void setContactWork(String contactWork) {
        this.contactWork = contactWork;
    }
    public String getContactImage() {
        return contactImage;
    }
    public void setContactImage(String contactImage) {
        this.contactImage = contactImage;
    }
    
    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }
    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }
    public String getContactAlternatePhoneNumber() {
        return contactAlternatePhoneNumber;
    }
    public void setContactAlternatePhoneNumber(String contactAlternatePhoneNumber) {
        this.contactAlternatePhoneNumber = contactAlternatePhoneNumber;
    }
    public String getContactEmail() {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Contact [contactId=" + contactId + ", contactName=" + contactName + ", contactNickname="
                + contactNickname + ", contactWork=" + contactWork + ", contactImage=" + contactImage
                + ", contactDescription=" + contactDescription + ", contactPhoneNumber=" + contactPhoneNumber
                + ", contactAlternatePhoneNumber=" + contactAlternatePhoneNumber + ", contactEmail=" + contactEmail
                + ", user=" + user + "]";
    }

    
    


}
