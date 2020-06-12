package com.lukaszkolmer.jobsportal.user.repository;

import com.lukaszkolmer.jobsportal.user.exceptions.NoUserOfGivenID;
import com.lukaszkolmer.jobsportal.user.model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserRepositoryImpl {

    UserRepository userRepository;

    @Autowired
    public UserRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setDatabase() {
        User admin = new User("admin@admin.com", "admin", "admin", "ADMIN");
        User user = new User("user@user.com", "user", "user", "USER");
        userRepository.save(admin);
        userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoUserOfGivenID(id));
    }

    public User addNewJobOffer(User userToAdd) {
        userRepository.save(userToAdd);
        return userToAdd;
    }

    public User removeJobOffer(User userToRemove) {
        userRepository.delete(userToRemove);
        return userToRemove;
    }

    public User findByUsername(String username) {
        return userRepository.findAll().stream()
                .filter(user -> user.username.equals(username))
                .findAny()
                .orElse(null);
    }

    public User changeUserEmail(Long id, String newEmail) {
        User user = userRepository.getOne(id);
        user.setEmail(newEmail);
        userRepository.save(user);
        return user;
    }


}
