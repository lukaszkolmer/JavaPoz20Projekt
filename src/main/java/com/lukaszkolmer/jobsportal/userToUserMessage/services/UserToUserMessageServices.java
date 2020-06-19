package com.lukaszkolmer.jobsportal.userToUserMessage.services;

import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.userToUserMessage.exceptions.NoUserToUserMessageOfGivenID;
import com.lukaszkolmer.jobsportal.userToUserMessage.model.UserToUserMessage;
import com.lukaszkolmer.jobsportal.userToUserMessage.repository.UserToUserMessageRepository;
import com.lukaszkolmer.jobsportal.jobs.exceptions.NoOfferOfGivenID;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
public class UserToUserMessageServices {


    UserToUserMessageRepository userToUserMessageRepository;

    @Autowired
    public UserToUserMessageServices(UserToUserMessageRepository userToUserMessageRepository) {
        this.userToUserMessageRepository = userToUserMessageRepository;
    }

    public UserToUserMessage addNewUserToUserMessage(UserToUserMessage userToUserMessage) {
        userToUserMessageRepository.save(userToUserMessage);
        return userToUserMessage;
    }

    public UserToUserMessage findUserToUserMessageById(Long id) {
        return userToUserMessageRepository.findById(id).orElseThrow(() -> new NoUserToUserMessageOfGivenID(id));
    }

    public List<UserToUserMessage> findUserToUserMessageBySender(String sender) {
        return userToUserMessageRepository.findAll().stream().filter(userToUserMessage -> userToUserMessage.getSender().equals(sender)).collect(Collectors.toList());
    }
    public List<UserToUserMessage> findUserToUserMessageByReceiver(String receiver) {
        return userToUserMessageRepository.findAll().stream().filter(userToUserMessage -> userToUserMessage.getReceiver().equals(receiver)).collect(Collectors.toList());
    }
    public UserToUserMessage markMessageAsAlreadyRead(Long id) {
        UserToUserMessage userToUserMessage = userToUserMessageRepository.getOne(id);
        userToUserMessage.setAlreadyRead(true);
        userToUserMessageRepository.save(userToUserMessage);
        return userToUserMessage;
    }
    public UserToUserMessage markMessageAsNotRead(Long id) {
        UserToUserMessage userToUserMessage = userToUserMessageRepository.getOne(id);
        userToUserMessage.setAlreadyRead(false);
        userToUserMessageRepository.save(userToUserMessage);
        return userToUserMessage;
    }

}
