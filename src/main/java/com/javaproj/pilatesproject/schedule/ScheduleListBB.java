package com.javaproj.pilatesproject.schedule;

import com.javaproj.pilatesproject.dao.ClassDAO;
import com.javaproj.pilatesproject.dao.ScheduleDAO;
import com.javaproj.pilatesproject.entities.Schedule;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 48512
 */
@Named
@RequestScoped
public class ScheduleListBB {
    private static final String PAGE_SCHEDULE_EDIT = "scheduleEdit?faces-redirect=true";
    private List<Schedule> scheduleList;
    private Schedule newSchedule;
    private Schedule selectedSchedule;

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    ScheduleDAO scheduleDAO;

    @EJB
    ClassDAO classDAO;

    @PostConstruct
    public void init() {
        newSchedule = new Schedule();
        newSchedule.setClassId(new com.javaproj.pilatesproject.entities.Class());// Inicjalizacja obiektu Class
    }

    public Schedule getNewSchedule() {
        return newSchedule;
    }

    public void setNewSchedule(Schedule newSchedule) {
        this.newSchedule = newSchedule;
    }

    public List<Schedule> getFullList() {
        if (scheduleList == null) {
            scheduleList = scheduleDAO.findAll();
        }
        return scheduleList;
    }

    public void deleteSchedule(Long id) {
        scheduleDAO.delete(id);
        refreshScheduleList();
        flash.put("message", "Schedule successfully deleted.");
    }

    public String saveNewSchedule() {
        try {
            if (newSchedule.getClassId() == null || newSchedule.getClassId().getId() == null) {
                flash.put("error", "Class ID is required.");
                return null;
            }

            com.javaproj.pilatesproject.entities.Class existingClass = classDAO.findById(newSchedule.getClassId().getId());
            if (existingClass == null) {
                flash.put("error", "Class with given ID does not exist.");
                return null;
            }

            newSchedule.setClassId(existingClass);
            scheduleDAO.create(newSchedule);
            flash.put("message", "Schedule successfully created.");
            return "scheduleList?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            flash.put("error", "Error saving schedule.");
            return null;
        }
    }

    private void refreshScheduleList() {
        scheduleList = scheduleDAO.findAll();
    }

    public String editSchedule(Schedule schedule) {
        // 1. Pass object through flash
        flash.put("schedule", schedule);
        return PAGE_SCHEDULE_EDIT;
    }
}
