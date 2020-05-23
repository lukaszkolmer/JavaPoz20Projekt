package com.lukaszkolmer.jobsportal.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    public String name;
    public String surname;
    public String email;
    public String password;
    public Date birthDate;
}
