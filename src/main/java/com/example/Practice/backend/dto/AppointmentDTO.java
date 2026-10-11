package com.example.Practice.backend.dto;

public class AppointmentDTO {

    public Long id;

    public Long teacherId;

    public Long studentId;

    public String consultationDay;

    public String consultationTime;

    public String status;

    // Для головної сторінки студента
    public String teacherName;

    // Для головної сторінки викладача
    public String studentName;

    public AppointmentDTO() {
    }

    public AppointmentDTO(
            Long id,
            Long teacherId,
            Long studentId,
            String consultationDay,
            String consultationTime,
            String status,
            String teacherName,
            String studentName
    ) {

        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;

        this.consultationDay = consultationDay;
        this.consultationTime = consultationTime;

        this.status = status;

        this.teacherName = teacherName;
        this.studentName = studentName;
    }

}