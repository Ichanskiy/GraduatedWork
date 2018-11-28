package com.ichanskiy.attendance.repository;

import com.ichanskiy.attendance.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student getByLoginAndPassword(String login, String password);
    Student getByLogin(String login);
    boolean existsByLogin(String login);
}
