package com.smartcontactmanager.smartcontactmanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contactId;
    private String contactName;
    private String contactNickname;
    private String contactWork;
    private String contactImage;
    @Column(length = 500)
    private String contactDescription;
    private String contactPhoneNumber;
    private String contactAlternatePhoneNumber;
    private String contactEmail;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Contact() {
    }

    
    public Contact(String contactName, String contactNickname, String contactWork, String contactImage,
            String contactDescription, String contactPhoneNumber, String contactAlternatePhoneNumber,
            String contactEmail, User user) {
        this.contactName = contactName;
        this.contactNickname = contactNickname;
        this.contactWork = contactWork;
        this.contactImage = contactImage;
        this.contactDescription = contactDescription;
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
    public String getContactDescription() {
        return contactDescription;
    }
    public void setContactDescription(String contactDescription) {
        this.contactDescription = contactDescription;
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

    


}
