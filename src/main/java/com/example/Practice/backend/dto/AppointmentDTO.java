package com.example.Practice.backend.dto;

public class AppointmentDTO {

    public Long id;

    public Long teacherId;

    public Long studentId;

    public String consultationDay;

    public String consultationTime;

    public String status;

    public AppointmentDTO() {
    }

    public AppointmentDTO(
            Long id,
            Long teacherId,
            Long studentId,
            String consultationDay,
            String consultationTime,
            String status
    ) {

        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;

        this.consultationDay = consultationDay;
        this.consultationTime = consultationTime;

        this.status = status;

    }

}