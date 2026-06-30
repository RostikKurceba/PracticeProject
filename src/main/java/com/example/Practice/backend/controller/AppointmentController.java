package com.example.Practice.backend.controller;

import com.example.Practice.backend.dto.AppointmentDTO;
import com.example.Practice.backend.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@CrossOrigin
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping("/teacher/{teacherId}")
    public List<AppointmentDTO> getTeacherCalendar(
            @PathVariable Long teacherId
    ) {
        return service.getTeacherCalendar(teacherId);
    }

    @PostMapping("/book")
    public AppointmentDTO book(
            @RequestParam Long teacherId,
            @RequestParam Long studentId,
            @RequestParam String day,
            @RequestParam String time
    ) {
        return service.bookAppointment(
                teacherId,
                studentId,
                day,
                time
        );
    }

    @GetMapping("/student/{studentId}")
    public List<AppointmentDTO> getStudentAppointments(
            @PathVariable Long studentId
    ) {
        return service.getStudentAppointments(studentId);
    }
}