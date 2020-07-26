package com.lukaszkolmer.jobsportal.userToUserMessage.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    String attachmentLink;
    String sender;
    String receiver;
    LocalDate sentDate = null;
    LocalDate receivedDate = null;
    boolean alreadyRead = false;

    public UserToUserMessage(String title, String message, String sender,String receiver,String attachmentLink) {
            this.title = title;
            this.message = message;
            this.sender = sender;
            this.receiver = receiver;
            this.attachmentLink = attachmentLink;

    }

}
