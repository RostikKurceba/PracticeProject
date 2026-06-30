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
       КАЛЕНДАР ВИКЛАДАЧА
       ========================= */

    public List<AppointmentDTO> getTeacherCalendar(Long teacherId) {

        List<Appointment> appointments =
                appointmentRepo.findByTeacherId(teacherId);

        List<AppointmentDTO> result = new ArrayList<>();

        for (Appointment appointment : appointments) {

            result.add(new AppointmentDTO(

                    appointment.id,

                    appointment.teacher.id,

                    appointment.student == null ? null : appointment.student.id,

                    appointment.consultationDay,

                    appointment.consultationTime,

                    appointment.status

            ));
        }

        return result;
    }

    /* =========================
       ЗАПИС НА КОНСУЛЬТАЦІЮ
       ========================= */

    public AppointmentDTO bookAppointment(Long teacherId,
                                          Long studentId,
                                          String day,
                                          String time) {

        if (appointmentRepo
                .findByTeacherIdAndConsultationDayAndConsultationTime(
                        teacherId,
                        day,
                        time
                ).isPresent()) {

            throw new RuntimeException("This time is already booked");
        }

        Teacher teacher =
                teacherRepo.findById(teacherId)
                        .orElseThrow(() ->
                                new RuntimeException("Teacher not found"));

        Student student =
                studentRepo.findById(studentId)
                        .orElseThrow(() ->
                                new RuntimeException("Student not found"));

        Appointment appointment = new Appointment();

        appointment.teacher = teacher;
        appointment.student = student;
        appointment.consultationDay = day;
        appointment.consultationTime = time;
        appointment.status = "BOOKED";

        appointmentRepo.save(appointment);

        return new AppointmentDTO(

                appointment.id,

                teacher.id,

                student.id,

                day,

                time,

                "BOOKED"

        );
    }

    /* =========================
       МОЇ КОНСУЛЬТАЦІЇ
       ========================= */

    public List<AppointmentDTO> getStudentAppointments(Long studentId) {

        List<Appointment> appointments =
                appointmentRepo.findByStudentId(studentId);

        List<AppointmentDTO> result = new ArrayList<>();

        for (Appointment appointment : appointments) {

            result.add(new AppointmentDTO(

                    appointment.id,

                    appointment.teacher.id,

                    appointment.student == null ? null : appointment.student.id,

                    appointment.consultationDay,

                    appointment.consultationTime,

                    appointment.status

            ));
        }

        return result;
    }

}