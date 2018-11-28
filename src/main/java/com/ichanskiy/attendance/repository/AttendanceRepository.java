package com.ichanskiy.attendance.repository;

import com.ichanskiy.attendance.entity.Attendance;
import com.ichanskiy.attendance.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> getAllByStudentAndDateBetween(Student student, Date from, Date to);
    List<Attendance> getAllByStudent(Student student);
}
