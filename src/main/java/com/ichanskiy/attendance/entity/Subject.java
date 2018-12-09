package com.ichanskiy.attendance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "Subject")
@EqualsAndHashCode(callSuper = true)
public class Subject extends BaseObject {

    @Column(name = "login")
    private String question;

    private Integer name;

    private Integer countTest;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "teachers_subject",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id")}
    )
    private Set<Teacher> teachers;
}