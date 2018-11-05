package com.ichanskiy.attendance.controllers;

import com.ichanskiy.attendance.entity.Student;
import com.ichanskiy.attendance.repository.StudentRepository;
import com.ichanskiy.attendance.service.StudentService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

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
        return new ResponseEntity<>(studentService.registration(student), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/all")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        File finalFile = new File("test.jpg");
        FileUtils.writeByteArrayToFile(finalFile, file.getBytes());
        return new ResponseEntity<>("Done", HttpStatus.OK);
    }
}
