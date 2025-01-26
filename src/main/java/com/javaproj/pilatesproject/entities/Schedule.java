/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javaproj.pilatesproject.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author 48512
 */
@Entity
@Table(name = "schedule")
@NamedQueries({
    @NamedQuery(name = "Schedule.findAll", query = "SELECT s FROM Schedule s"),
    @NamedQuery(name = "Schedule.findById", query = "SELECT s FROM Schedule s WHERE s.id = :id"),
    @NamedQuery(name = "Schedule.findByCreatedAt", query = "SELECT s FROM Schedule s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Schedule.findByUpdatedAt", query = "SELECT s FROM Schedule s WHERE s.updatedAt = :updatedAt"),
    @NamedQuery(name = "Schedule.findByStartTime", query = "SELECT s FROM Schedule s WHERE s.startTime = :startTime"),
    @NamedQuery(name = "Schedule.findByEndTime", query = "SELECT s FROM Schedule s WHERE s.endTime = :endTime"),
    @NamedQuery(name = "Schedule.findByDay", query = "SELECT s FROM Schedule s WHERE s.day = :day")})
public class Schedule implements Serializable {

    @Size(max = 255)
    @Column(name = "start_time")
    private String startTime;
    @Size(max = 255)
    @Column(name = "end_time")
    private String endTime;
    @Size(max = 12)
    @Column(name = "day")
    private String day;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scheduleId")
    private Collection<ScheduleUser> scheduleUserCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @ManyToOne
    private Class classId;

    public Schedule() {
    }

    public Schedule(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public Class getClassId() {
        return classId;
    }

    public void setClassId(Class classId) {
        this.classId = classId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Schedule)) {
            return false;
        }
        Schedule other = (Schedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javaproj.pilatesproject.entities.Schedule[ id=" + id + " ]";
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Collection<ScheduleUser> getScheduleUserCollection() {
        return scheduleUserCollection;
    }

    public void setScheduleUserCollection(Collection<ScheduleUser> scheduleUserCollection) {
        this.scheduleUserCollection = scheduleUserCollection;
    }
    
}
