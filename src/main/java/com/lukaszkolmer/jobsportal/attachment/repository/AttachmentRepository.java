package com.lukaszkolmer.jobsportal.attachment.repository;

import com.lukaszkolmer.jobsportal.attachment.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
