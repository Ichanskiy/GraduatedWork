package com.ichanskiy.attendance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "teacher")
@EqualsAndHashCode(callSuper = true)
public class Teacher extends BaseObject {

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "teachers")
    private Set<Subject> subjects;

//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
//            fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Subject subjects;
}