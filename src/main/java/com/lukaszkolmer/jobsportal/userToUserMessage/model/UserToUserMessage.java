package com.lukaszkolmer.jobsportal.userToUserMessage.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;

@Data
@Entity
@NoArgsConstructor
public class UserToUserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String message;
    File file;
    String sender;
    String receiver;
    boolean read = false;

    public UserToUserMessage(String title, String message, File file, String sender,String receiver) {
        this.title = title;
        this.message = message;
        this.file = file;
        this.sender = sender;
        this.receiver = receiver;

    }
}
