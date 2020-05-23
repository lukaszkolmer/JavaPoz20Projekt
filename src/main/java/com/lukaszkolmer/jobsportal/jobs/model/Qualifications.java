package com.lukaszkolmer.jobsportal.jobs.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class Qualifications {

  public List<String> qualifications = new ArrayList<>();

}
