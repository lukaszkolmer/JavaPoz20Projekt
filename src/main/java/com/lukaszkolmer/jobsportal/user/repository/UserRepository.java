package com.lukaszkolmer.jobsportal.user.repository;

import com.lukaszkolmer.jobsportal.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
