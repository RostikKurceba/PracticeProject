package com.example.Practice.backend.service;

import com.example.Practice.backend.dto.AuthResponse;
import com.example.Practice.backend.dto.LoginRequest;
import com.example.Practice.backend.dto.RegisterRequest;
import com.example.Practice.backend.entity.Student;
import com.example.Practice.backend.entity.Teacher;
import com.example.Practice.backend.repository.StudentRepository;
import com.example.Practice.backend.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final StudentRepository studentRepo;
    private final TeacherRepository teacherRepo;

    public AuthService(StudentRepository studentRepo,
                       TeacherRepository teacherRepo) {
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
    }

    public AuthResponse register(RegisterRequest req) {

        String token = UUID.randomUUID().toString();

        if (req.role.equals("student")) {

            Student s = new Student();
            s.firstName = req.firstName;
            s.lastName = req.lastName;
            s.middleName = req.middleName;
            s.email = req.email;
            s.password = req.password;

            studentRepo.save(s);

            return new AuthResponse(
                    s.id,
                    token,
                    "student",
                    s.lastName,
                    s.firstName,
                    s.middleName
            );
        }

        Teacher t = new Teacher();
        t.firstName = req.firstName;
        t.lastName = req.lastName;
        t.middleName = req.middleName;
        t.email = req.email;
        t.password = req.password;
        t.subject = req.subject;

        teacherRepo.save(t);

        return new AuthResponse(
                t.id,
                token,
                "teacher",
                t.lastName,
                t.firstName,
                t.middleName
        );
    }

    public AuthResponse login(LoginRequest req, String role) {

        String token = UUID.randomUUID().toString();

        if (role.equals("student")) {

            Student s = studentRepo.findByEmail(req.email)
                    .orElseThrow(() -> new RuntimeException("Not found"));

            if (!s.password.equals(req.password))
                throw new RuntimeException("Wrong password");

            return new AuthResponse(
                    s.id,
                    token,
                    "student",
                    s.lastName,
                    s.firstName,
                    s.middleName
            );
        }

        Teacher t = teacherRepo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (!t.password.equals(req.password))
            throw new RuntimeException("Wrong password");

        return new AuthResponse(
                t.id,
                token,
                "teacher",
                t.lastName,
                t.firstName,
                t.middleName
        );
    }
}