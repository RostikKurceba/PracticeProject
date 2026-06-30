package com.example.Practice.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    public Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "student_id")
    public Student student;

    @Column(name = "consultation_day")
    public String consultationDay;

    @Column(name = "consultation_time")
    public String consultationTime;

    public String status;

}