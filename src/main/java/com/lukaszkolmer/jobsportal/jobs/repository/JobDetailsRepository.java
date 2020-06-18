package com.lukaszkolmer.jobsportal.jobs.repository;

import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDetailsRepository extends JpaRepository<JobDetails, Long> {

}
