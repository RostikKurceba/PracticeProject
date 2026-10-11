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

    public AppointmentService(
            AppointmentRepository appointmentRepo,
            TeacherRepository teacherRepo,
            StudentRepository studentRepo
    ) {

        this.appointmentRepo = appointmentRepo;
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
    }

    public List<AppointmentDTO> getTeacherCalendar(Long teacherId) {

        List<Appointment> appointments =
                appointmentRepo.findByTeacherId(teacherId);

        List<AppointmentDTO> result = new ArrayList<>();

        for (Appointment appointment : appointments) {

            String studentName = "";

            if (appointment.student != null) {

                studentName =
                        appointment.student.lastName + " "
                                + appointment.student.firstName.substring(0, 1) + "."
                                + appointment.student.middleName.substring(0, 1) + ".";
            }

            result.add(

                    new AppointmentDTO(

                            appointment.id,

                            appointment.teacher.id,

                            appointment.student == null
                                    ? null
                                    : appointment.student.id,

                            appointment.consultationDay,

                            appointment.consultationTime,

                            appointment.status,

                            null,

                            studentName

                    )

            );

        }

        return result;
    }

    public AppointmentDTO bookAppointment(
            Long teacherId,
            Long studentId,
            String day,
            String time
    ) {

        if (appointmentRepo
                .findByTeacherIdAndConsultationDayAndConsultationTime(
                        teacherId,
                        day,
                        time
                )
                .isPresent()) {

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

        String teacherName =
                teacher.lastName + " "
                        + teacher.firstName.substring(0, 1) + "."
                        + teacher.middleName.substring(0, 1) + ".";

        String studentName =
                student.lastName + " "
                        + student.firstName.substring(0, 1) + "."
                        + student.middleName.substring(0, 1) + ".";

        return new AppointmentDTO(

                appointment.id,

                teacher.id,

                student.id,

                day,

                time,

                "BOOKED",

                teacherName,

                studentName

        );
    }

    public List<AppointmentDTO> getStudentAppointments(Long studentId) {

        List<Appointment> appointments =
                appointmentRepo.findByStudentId(studentId);

        List<AppointmentDTO> result = new ArrayList<>();

        for (Appointment appointment : appointments) {

            String teacherName =
                    appointment.teacher.lastName + " "
                            + appointment.teacher.firstName.substring(0, 1) + "."
                            + appointment.teacher.middleName.substring(0, 1) + ".";

            result.add(

                    new AppointmentDTO(

                            appointment.id,

                            appointment.teacher.id,

                            appointment.student.id,

                            appointment.consultationDay,

                            appointment.consultationTime,

                            appointment.status,

                            teacherName,

                            null

                    )

            );

        }

        return result;
    }

}