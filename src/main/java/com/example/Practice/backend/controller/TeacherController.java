package com.example.Practice.backend.controller;

import com.example.Practice.backend.dto.TeacherDTO;
import com.example.Practice.backend.service.TeacherService;
import org.springframework.web.bind.annotation.*;
import com.example.Practice.backend.dto.TeacherProfileDTO;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@CrossOrigin
public class TeacherController {

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<TeacherDTO> search(

            @RequestParam String subject

    ){

        return service.searchTeachers(subject);

    }

    @GetMapping("/{id}")
    public TeacherProfileDTO getTeacher(
            @PathVariable Long id
    ){

        return service.getTeacher(id);

    }

}