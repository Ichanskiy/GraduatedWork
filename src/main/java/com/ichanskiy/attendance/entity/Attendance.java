package com.ichanskiy.attendance.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attendance")
public class Attendance extends BaseObject {

    @Column(name = "date")
    private Date date;

    @Column(name = "presence")
    private Boolean presence;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Student student;
}