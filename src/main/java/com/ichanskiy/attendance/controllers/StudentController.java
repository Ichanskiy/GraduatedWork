package com.ichanskiy.attendance.controllers;

import com.ichanskiy.attendance.entity.Student;
import com.ichanskiy.attendance.repository.StudentRepository;
import com.ichanskiy.attendance.service.IdentificationService;
import com.ichanskiy.attendance.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private IdentificationService identificationService;

    @CrossOrigin
    @GetMapping("/login")
    public ResponseEntity<Student> login(String login, String password) {
        Student student = studentRepository.getByLoginAndPassword(login, password);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/registration")
    public ResponseEntity<Student> registration(@RequestBody @Validated Student student) {
        if (studentRepository.existsByLogin(student.getLogin())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Student studentDb = registrationService.registration(student);
        if (studentDb == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(studentDb, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/identification")
    public ResponseEntity<Student> identification(String login) {
        Student student = studentRepository.getByLogin(login);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!identificationService.identificationStatusIsOk(student)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/all")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
}
