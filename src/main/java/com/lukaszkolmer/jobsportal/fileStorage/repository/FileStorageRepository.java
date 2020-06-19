package com.lukaszkolmer.jobsportal.fileStorage.repository;

import com.lukaszkolmer.jobsportal.fileStorage.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends JpaRepository<File,String> {
}
