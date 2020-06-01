package com.lukaszkolmer.jobsportal.contact.repository;

import com.lukaszkolmer.jobsportal.contact.model.UserMessage;
import com.lukaszkolmer.jobsportal.jobs.exceptions.NoOfferOfGivenID;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class UserMessageRepositoryImpl {

    @Autowired
    private UserMessageRepository userMessageRepository;

    public UserMessage findMessageById(Long id) {
        return userMessageRepository.findById(id).orElseThrow(() -> new NoOfferOfGivenID(id));
    }

    public List<UserMessage> findUserMessagesBySubject(String subject) {
        return userMessageRepository.findAll().stream().filter(userMessage -> userMessage.getSubject().equals(subject)).collect(Collectors.toList());
    }

    public UserMessage addNewUserMessage(UserMessage userMessageToAdd) {
        userMessageRepository.save(userMessageToAdd);
        return userMessageToAdd;
    }

    public UserMessage removeUserMessage(UserMessage userMessageToRemove) {
        userMessageRepository.delete(userMessageToRemove);
        return userMessageToRemove;
    }

}
