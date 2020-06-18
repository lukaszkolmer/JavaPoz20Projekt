package com.lukaszkolmer.jobsportal.userToUserMessage.repository;

import com.lukaszkolmer.jobsportal.userToUserMessage.model.UserToUserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToUserMessageRepository extends JpaRepository<UserToUserMessage,Long> {
}


