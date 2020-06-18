package com.lukaszkolmer.jobsportal.user.model;

import com.lukaszkolmer.jobsportal.userToUserMessage.model.UserToUserMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String username;
    public String email;
    public String password;
    public String role; // user, mod, admin

   /* @OneToMany(fetch= FetchType.LAZY, cascade=CascadeType.ALL)
    public List<UserToUserMessage> sentUserToUserMessages = new ArrayList<>();
    @OneToMany(fetch= FetchType.LAZY, cascade=CascadeType.ALL)
    public List<UserToUserMessage> receivedUserToUserMessages = new ArrayList<>();
*/

    public User(String email, String username, String password, String role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
