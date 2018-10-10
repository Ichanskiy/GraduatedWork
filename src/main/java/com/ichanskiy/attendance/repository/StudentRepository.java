package com.ichanskiy.attendance.repository;

import com.ichanskiy.attendance.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
