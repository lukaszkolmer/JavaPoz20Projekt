package com.lukaszkolmer.jobsportal.attachment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String url;
    String name;
    String ownerUsername;

    public Attachment(String name, String url,String ownerUsername) {
        this.name = name;
        this.url = url;
        this.ownerUsername = ownerUsername;
    }
}