package com.lukaszkolmer.jobsportal.jobs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JobDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;
    public String description;
    public String responsibility; // zmienic na liste odpowiedzialnosci
    public String qualifications; // zmienic na liste kwalifikacji
    public String benefits;

    public String location;
    public String salary; // potem może rozbić na 2 inty? minimum - maximum
    public String vacancy;
    public LocalDate publishDate;
    public String jobNature; // part-time/full-time etc

    // public Owner owner;
    // public Image image; ???
}
