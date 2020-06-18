package com.lukaszkolmer.jobsportal.jobs.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class JobDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String category;
    public String title;
    public String description;
    public String responsibility;
    public String qualifications;
    public String benefits;
    public String location;
    public String salary;
    public String vacancy;
    public LocalDate publishDate;
    public String jobNature; // part-time/full-time etc
    public String owner;
    public boolean isActive = true;

    public JobDetails(String category, String title, String description, String responsibility, String qualifications, String benefits,
                      String location, String salary, String vacancy, LocalDate publishDate, String jobNature, String owner) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.responsibility = responsibility;
        this.qualifications = qualifications;
        this.benefits = benefits;
        this.location = location;
        this.salary = salary;
        this.vacancy = vacancy;
        this.publishDate = publishDate;
        this.jobNature = jobNature;
        this.owner = owner;
    }

    // public Image image; ???
}
