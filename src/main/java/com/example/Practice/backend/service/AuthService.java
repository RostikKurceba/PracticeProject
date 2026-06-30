package com.example.Practice.backend.service;

import com.example.Practice.backend.dto.LoginRequest;
import com.example.Practice.backend.dto.RegisterRequest;
import com.example.Practice.backend.entity.Student;
import com.example.Practice.backend.entity.Teacher;
import com.example.Practice.backend.repository.StudentRepository;
import com.example.Practice.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    // ---------- REGISTER ----------
    public Object register(String role, RegisterRequest req) {

        if (role.equals("student")) {

            Student s = new Student();
            s.setLastName(req.lastName);
            s.setFirstName(req.firstName);
            s.setMiddleName(req.middleName);
            s.setEmail(req.email);
            s.setPassword(req.password);

            return studentRepo.save(s);
        }

        Teacher t = new Teacher();
        t.setLastName(req.lastName);
        t.setFirstName(req.firstName);
        t.setMiddleName(req.middleName);
        t.setEmail(req.email);
        t.setPassword(req.password);
        t.setSubject(req.subject);

        return teacherRepo.save(t);
    }

    // ---------- LOGIN ----------
    public Object login(String role, LoginRequest req) {

        if (role.equals("student")) {

            Student s = studentRepo.findByEmail(req.email)
                    .orElseThrow(() -> new RuntimeException("No user"));

            if (!s.getPassword().equals(req.password))
                throw new RuntimeException("Wrong password");

            return s;
        }

        Teacher t = teacherRepo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("No user"));

        if (!t.getPassword().equals(req.password))
            throw new RuntimeException("Wrong password");

        return t;
    }
}