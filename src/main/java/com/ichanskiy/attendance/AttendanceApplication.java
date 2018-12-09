package com.ichanskiy.attendance;

import com.ichanskiy.attendance.repository.NamesOnly;
import com.ichanskiy.attendance.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class AttendanceApplication implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(AttendanceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        NamesOnly namesOnly = studentRepository.getById(122);
    }
}
