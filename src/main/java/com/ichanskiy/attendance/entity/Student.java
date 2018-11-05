package com.ichanskiy.attendance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "students")
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseObject {

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "groups")
    private String group;

    @Column(name = "photoId")
    private Long photoId;

}