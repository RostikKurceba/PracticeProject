package com.example.Practice.backend.dto;

public class TeacherProfileDTO {

    public Long id;

    public String lastName;
    public String firstName;
    public String middleName;

    public String subject;

    public TeacherProfileDTO() {
    }

    public TeacherProfileDTO(Long id,
                             String lastName,
                             String firstName,
                             String middleName,
                             String subject) {

        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.subject = subject;

    }

}