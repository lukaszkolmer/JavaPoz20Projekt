package com.lukaszkolmer.jobsportal.jobs.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Responsibilities {
    public List<String> responsibilities = new ArrayList<>();
}
