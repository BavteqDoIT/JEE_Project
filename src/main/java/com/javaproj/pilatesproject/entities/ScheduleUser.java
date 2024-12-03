/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javaproj.pilatesproject.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 48512
 */
@Entity
@Table(name = "schedule_user")
@NamedQueries({
    @NamedQuery(name = "ScheduleUser.findAll", query = "SELECT s FROM ScheduleUser s"),
    @NamedQuery(name = "ScheduleUser.findByCreatedAt", query = "SELECT s FROM ScheduleUser s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "ScheduleUser.findByUpdatedAt", query = "SELECT s FROM ScheduleUser s WHERE s.updatedAt = :updatedAt"),
    @NamedQuery(name = "ScheduleUser.findById", query = "SELECT s FROM ScheduleUser s WHERE s.id = :id")})
public class ScheduleUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Schedules scheduleId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userId;

    public ScheduleUser() {
    }

    public ScheduleUser(Integer id) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Schedules getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Schedules scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
        if (!(object instanceof ScheduleUser)) {
            return false;
        }
        ScheduleUser other = (ScheduleUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javaproj.pilatesproject.entities.ScheduleUser[ id=" + id + " ]";
    }
    
}
