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
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author 48512
 */
@Entity
@Table(name = "schedules")
@NamedQueries({
    @NamedQuery(name = "Schedules.findAll", query = "SELECT s FROM Schedules s"),
    @NamedQuery(name = "Schedules.findById", query = "SELECT s FROM Schedules s WHERE s.id = :id"),
    @NamedQuery(name = "Schedules.findByDate", query = "SELECT s FROM Schedules s WHERE s.date = :date"),
    @NamedQuery(name = "Schedules.findByStartTime", query = "SELECT s FROM Schedules s WHERE s.startTime = :startTime"),
    @NamedQuery(name = "Schedules.findByEndTime", query = "SELECT s FROM Schedules s WHERE s.endTime = :endTime"),
    @NamedQuery(name = "Schedules.findByScheduleSlots", query = "SELECT s FROM Schedules s WHERE s.scheduleSlots = :scheduleSlots"),
    @NamedQuery(name = "Schedules.findByCreatedAt", query = "SELECT s FROM Schedules s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Schedules.findByUpdatedAt", query = "SELECT s FROM Schedules s WHERE s.updatedAt = :updatedAt")})
public class Schedules implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "schedule_slots")
    private int scheduleSlots;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scheduleId")
    private Collection<ScheduleUser> scheduleUserCollection;
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Classes classId;

    public Schedules() {
    }

    public Schedules(Long id) {
        this.id = id;
    }

    public Schedules(Long id, Date date, Date startTime, Date endTime, int scheduleSlots) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduleSlots = scheduleSlots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getScheduleSlots() {
        return scheduleSlots;
    }

    public void setScheduleSlots(int scheduleSlots) {
        this.scheduleSlots = scheduleSlots;
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

    public Collection<ScheduleUser> getScheduleUserCollection() {
        return scheduleUserCollection;
    }

    public void setScheduleUserCollection(Collection<ScheduleUser> scheduleUserCollection) {
        this.scheduleUserCollection = scheduleUserCollection;
    }

    public Classes getClassId() {
        return classId;
    }

    public void setClassId(Classes classId) {
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
        if (!(object instanceof Schedules)) {
            return false;
        }
        Schedules other = (Schedules) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javaproj.pilatesproject.entities.Schedules[ id=" + id + " ]";
    }
    
}
