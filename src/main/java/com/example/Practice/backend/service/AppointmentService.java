package com.example.Practice.backend.service;

import com.example.Practice.backend.dto.AppointmentDTO;
import com.example.Practice.backend.entity.Appointment;
import com.example.Practice.backend.entity.Student;
import com.example.Practice.backend.entity.Teacher;
import com.example.Practice.backend.repository.AppointmentRepository;
import com.example.Practice.backend.repository.StudentRepository;
import com.example.Practice.backend.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final TeacherRepository teacherRepo;
    private final StudentRepository studentRepo;

    public AppointmentService(AppointmentRepository appointmentRepo,
                              TeacherRepository teacherRepo,
                              StudentRepository studentRepo) {

        this.appointmentRepo = appointmentRepo;
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
    }

    /* =========================
       GET TEACHER CALENDAR
       ========================= */
    public List<AppointmentDTO> getTeacherCalendar(Long teacherId) {

        List<Appointment> list =
                appointmentRepo.findByTeacherId(teacherId);

        List<AppointmentDTO> result = new ArrayList<>();

        for (Appointment a : list) {

            result.add(new AppointmentDTO(
                    a.id,
                    a.teacher.id,
                    a.student != null ? a.student.id : null,
                    a.consultationDay,
                    a.consultationTime,
                    a.status
            ));
        }

        return result;
    }

    /* =========================
       CREATE FREE SLOT (teacher)
       ========================= */
    public AppointmentDTO createSlot(Long teacherId,
                                     String day,
                                     String time) {

        Teacher teacher =
                teacherRepo.findById(teacherId)
                        .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Appointment appointment = new Appointment();

        appointment.teacher = teacher;
        appointment.consultationDay = day;
        appointment.consultationTime = time;
        appointment.status = "FREE";

        appointmentRepo.save(appointment);

        return new AppointmentDTO(
                appointment.id,
                teacherId,
                null,
                day,
                time,
                "FREE"
        );
    }

    /* =========================
       BOOK APPOINTMENT (student)
       ========================= */
    public AppointmentDTO bookAppointment(Long teacherId,
                                          Long studentId,
                                          String day,
                                          String time) {

        Appointment appointment =
                appointmentRepo
                        .findByTeacherIdAndConsultationDayAndConsultationTime(
                                teacherId,
                                day,
                                time
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Slot not found"));

        if (!"FREE".equals(appointment.status)) {
            throw new RuntimeException("Already booked");
        }

        Student student =
                studentRepo.findById(studentId)
                        .orElseThrow(() -> new RuntimeException("Student not found"));

        appointment.student = student;
        appointment.status = "BOOKED";

        appointmentRepo.save(appointment);

        return new AppointmentDTO(
                appointment.id,
                teacherId,
                studentId,
                day,
                time,
                "BOOKED"
        );
    }

    /* =========================
       GET STUDENT APPOINTMENTS
       ========================= */
    public List<AppointmentDTO> getStudentAppointments(Long studentId) {

        List<Appointment> list =
                appointmentRepo.findByStudentId(studentId);

        List<AppointmentDTO> result = new ArrayList<>();

        for (Appointment a : list) {

            result.add(new AppointmentDTO(
                    a.id,
                    a.teacher.id,
                    studentId,
                    a.consultationDay,
                    a.consultationTime,
                    a.status
            ));
        }

        return result;
    }
}