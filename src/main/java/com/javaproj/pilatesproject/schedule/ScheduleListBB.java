package com.javaproj.pilatesproject.schedule;

import com.javaproj.pilatesproject.dao.ScheduleDAO;
import com.javaproj.pilatesproject.entities.Schedule;
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
    private Schedule newSchedule = new Schedule();
    private Schedule selectedSchedule;  // Dodajemy pole dla edytowanego karnetu

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    ScheduleDAO scheduleDAO;

    public Schedule getNewSchedule() {
        return newSchedule;
    }

    public void setNewSchedule(Schedule newSchedule) {
        this.newSchedule = newSchedule;
    }

    public Schedule getSelectedSchedule() {
        return selectedSchedule;
    }

    public void setSelectedSchedule(Schedule selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }

    public List<Schedule> getFullList() {
        if (scheduleList == null) {
            scheduleList = scheduleDAO.findAll();
        }
        return scheduleList;
    }

    public void deleteSchedule(Long id) {
        scheduleDAO.delete(id);
        scheduleList = scheduleDAO.findAll();
        flash.put("message", "Schedule successfully deleted.");
    }

    public String saveNewSchedule() {
        try {
            // Logowanie danych przed zapisaniem
            System.out.println("Class ID: " + newSchedule.getClassId());
            System.out.println("Start Time: " + newSchedule.getStartTime());
            System.out.println("End Time: " + newSchedule.getEndTime());
            System.out.println("Day: " + newSchedule.getDay());

            // Tworzenie nowego grafiku
            scheduleDAO.create(newSchedule);
            flash.put("message", "Schedule successfully created.");
            return "scheduleList?faces-redirect=true"; // Upewnij się, że przekierowanie działa
        } catch (Exception e) {
            e.printStackTrace();
            flash.put("error", "Error saving schedule.");
            return null; // Zwracamy null, aby zostać na tej samej stronie w przypadku błędu
        }
    }

    // Metoda edytująca karnet
    public String saveEditedSchedule() {
        System.out.println("saveEditedSchedule method called");
        if (selectedSchedule != null) {
            scheduleDAO.update(selectedSchedule);  // Zaktualizowanie karnetu w bazie
            flash.put("message", "Schedule successfully updated.");
        }
        return "scheduleList?faces-redirect=true";
    }

    public String editSchedule(Schedule schedule) {
        // 1. Pass object through flash
        flash.put("schedule", schedule);
        return PAGE_SCHEDULE_EDIT;
    }
}
