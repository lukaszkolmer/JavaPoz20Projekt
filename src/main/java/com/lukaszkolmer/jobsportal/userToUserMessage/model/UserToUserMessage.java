package com.lukaszkolmer.jobsportal.userToUserMessage.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.time.LocalDate;

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
    LocalDate sentDate = null;
    LocalDate receivedDate = null;
    boolean alreadyRead = false;

    public UserToUserMessage(String title, String message, File file, String sender,String receiver) {
        this.title = title;
        this.message = message;
        this.file = file;
        this.sender = sender;
        this.receiver = receiver;

    }

}
