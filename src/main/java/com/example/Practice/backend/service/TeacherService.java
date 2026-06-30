package com.example.Practice.backend.service;

import com.example.Practice.backend.dto.TeacherDTO;
import com.example.Practice.backend.entity.Teacher;
import com.example.Practice.backend.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import com.example.Practice.backend.dto.TeacherProfileDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public List<TeacherDTO> searchTeachers(String subject){

        List<Teacher> teachers =
                repository.findBySubjectContainingIgnoreCase(subject);

        List<TeacherDTO> result = new ArrayList<>();

        for(Teacher teacher : teachers){

            String initials =
                    teacher.firstName.substring(0,1)
                            + "."
                            + teacher.middleName.substring(0,1)
                            + ".";

            result.add(

                    new TeacherDTO(

                            teacher.id,

                            teacher.lastName,

                            initials,

                            teacher.subject

                    )

            );

        }

        return result;

    }

    public TeacherProfileDTO getTeacher(Long id){

        Teacher teacher = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Teacher not found"));

        return new TeacherProfileDTO(

                teacher.id,

                teacher.lastName,

                teacher.firstName,

                teacher.middleName,

                teacher.subject

        );

    }

}