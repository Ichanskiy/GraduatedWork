package com.ichanskiy.attendance.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode
abstract class BaseObject implements Serializable {

    private static final long serialVersionUID = 1380193823299805010L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
