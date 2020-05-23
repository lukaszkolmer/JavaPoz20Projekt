package com.lukaszkolmer.jobsportal.jobs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDetails {

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
