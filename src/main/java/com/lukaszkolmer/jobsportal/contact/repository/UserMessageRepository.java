package com.lukaszkolmer.jobsportal.contact.repository;

import com.lukaszkolmer.jobsportal.contact.model.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
}
