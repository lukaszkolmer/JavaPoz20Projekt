package com.lukaszkolmer.jobsportal.contact.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String userName;
    String userEmail;
    String subject;
    String message;

    public UserMessage(String userName,String userEmail,String subject, String message) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.subject = subject;
        this.message = message;
    }
}
