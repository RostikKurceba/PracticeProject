package com.example.Practice.backend.repository;

import com.example.Practice.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByTeacherId(Long teacherId);

    List<Appointment> findByStudentId(Long studentId);

    Optional<Appointment>

    findByTeacherIdAndConsultationDayAndConsultationTime(

            Long teacherId,

            String consultationDay,

            String consultationTime

    );

}