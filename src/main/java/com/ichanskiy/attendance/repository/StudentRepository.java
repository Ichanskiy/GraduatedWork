package com.ichanskiy.attendance.repository;

import com.ichanskiy.attendance.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student getByLoginAndPassword(String login, String password);
    Student getByLogin(String login);
    NamesOnly getById(Integer id);
    boolean existsByLogin(String login);
    <T> Collection<T> findByPassword(String pas, Class<T> type);
}
