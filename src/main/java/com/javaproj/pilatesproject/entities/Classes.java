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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author 48512
 */
@Entity
@Table(name = "classes")
@NamedQueries({
    @NamedQuery(name = "Classes.findAll", query = "SELECT c FROM Classes c"),
    @NamedQuery(name = "Classes.findById", query = "SELECT c FROM Classes c WHERE c.id = :id"),
    @NamedQuery(name = "Classes.findByName", query = "SELECT c FROM Classes c WHERE c.name = :name"),
    @NamedQuery(name = "Classes.findByDescription", query = "SELECT c FROM Classes c WHERE c.description = :description"),
    @NamedQuery(name = "Classes.findBySlots", query = "SELECT c FROM Classes c WHERE c.slots = :slots"),
    @NamedQuery(name = "Classes.findByCreatedAt", query = "SELECT c FROM Classes c WHERE c.createdAt = :createdAt"),
    @NamedQuery(name = "Classes.findByUpdatedAt", query = "SELECT c FROM Classes c WHERE c.updatedAt = :updatedAt")})
public class Classes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "slots")
    private int slots;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classId")
    private Collection<Schedules> schedulesCollection;

    public Classes() {
    }

    public Classes(Long id) {
        this.id = id;
    }

    public Classes(Long id, String name, String description, int slots) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slots = slots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
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

    public Collection<Schedules> getSchedulesCollection() {
        return schedulesCollection;
    }

    public void setSchedulesCollection(Collection<Schedules> schedulesCollection) {
        this.schedulesCollection = schedulesCollection;
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
        if (!(object instanceof Classes)) {
            return false;
        }
        Classes other = (Classes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javaproj.pilatesproject.entities.Classes[ id=" + id + " ]";
    }
    
}
