package com.roshan.university.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "session")
public class ClassroomSession extends AuditModel {

    private static final long serialVersionUID = -1463454838928563083L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Classroom cannot be empty")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classroom_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Classroom classroom;

    @NotNull(message = "Group cannot be empty")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group;

    @Column(name = "begin_hours", nullable = false)
    @Positive(message = "Please provide a valid begin hours number")
    @NotNull(message = "Please provide a valid begin hours")
    private Integer beginHours;

    @Column(name = "end_hours", nullable = false)
    @Positive(message = "Please provide a valid end hours number")
    @NotNull(message = "Please provide a valid end hours")
    private Integer endHours;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Classroom getClassroom() {
        return this.classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Integer getBeginHours() {
        return this.beginHours;
    }

    public void setBeginHours(Integer beginHours) {
        this.beginHours = beginHours;
    }

    public Integer getEndHours() {
        return this.endHours;
    }

    public void setEndHours(Integer endHours) {
        this.endHours = endHours;
    }

}
