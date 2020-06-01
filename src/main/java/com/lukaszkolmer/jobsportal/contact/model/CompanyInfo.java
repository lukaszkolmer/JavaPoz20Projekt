package com.lukaszkolmer.jobsportal.contact.model;

public class CompanyInfo {

    private static final CompanyInfo instance = new CompanyInfo();

    private CompanyInfo(){}

    public static CompanyInfo getInstance(){
        return instance;
    }


    final public String name = "Greatest Jobs Ever inc.";
    final public  String owner = "Stefan Siarzewski";
    final public String address = "City 17, Russia";
    final public  String email = "testAddres@test.com";
    final public  String phoneNumber = "+48 111 222 333";
}
