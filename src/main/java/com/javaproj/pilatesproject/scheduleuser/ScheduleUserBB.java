package com.javaproj.pilatesproject.schedule;

import com.javaproj.pilatesproject.dao.ClassDAO;
import com.javaproj.pilatesproject.dao.PassDAO;
import com.javaproj.pilatesproject.dao.ScheduleDAO;
import com.javaproj.pilatesproject.dao.ScheduleUserDAO;
import com.javaproj.pilatesproject.dao.UserDAO;
import com.javaproj.pilatesproject.entities.Schedule;
import com.javaproj.pilatesproject.entities.ScheduleUser;
import com.javaproj.pilatesproject.entities.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ScheduleUserBB {
    private ScheduleUser newScheduleUser = new ScheduleUser();
    private  boolean var;

    @EJB
    ScheduleUserDAO scheduleUserDAO;
    
    @EJB
    UserDAO userDAO;
    
    @EJB
    ScheduleDAO scheduleDAO;
    
    @EJB
    PassDAO passDAO;

    public void signUserToSchedule(Long scheduleId, Long userId) {
        newScheduleUser.setUserId(userDAO.findById(userId));
        newScheduleUser.setScheduleId(scheduleDAO.findById(scheduleId));
        scheduleUserDAO.create(newScheduleUser);
        
        User user = findUserById(userId);
        Schedule schedule = findScheduleById(scheduleId);
        
        if (newScheduleUser.getUserId() == null) {
                            newScheduleUser.setUserId(new User()); // Tworzenie nowego obiektu, jeśli null
                        newScheduleUser.getUserId().setId(user.getId());
                        }
        
        if (newScheduleUser.getScheduleId() == null) {
                            newScheduleUser.setScheduleId(new Schedule()); // Tworzenie nowego obiektu, jeśli null
                        newScheduleUser.getScheduleId().setId(schedule.getId());
                        }
    }
    
    public User findUserById(Long id) {
        return userDAO.findById(id);
    }
    
    public Schedule findScheduleById(Long id) {
        return scheduleDAO.findById(id);
    }
    
    public boolean checkIfSigned(Long scheduleId, Long userId) {
        Schedule schedule = findScheduleById(scheduleId);
        User user = findUserById(userId);

        // Sprawdź, czy użytkownik jest już zapisany
        ScheduleUser existingScheduleUser = scheduleUserDAO.findByScheduleAndUser(schedule, user);
        if (existingScheduleUser != null) {
            return false; // Użytkownik nie jest już zapisany
        }   
        return checkSlots(schedule);
}
    
    public boolean checkSlots(Schedule schedule) {
        // Oblicz liczbę obecnych użytkowników zapisanych na zajęcia
        int currentUsers = scheduleUserDAO.countUsers(schedule);

        // Pobierz maksymalną liczbę miejsc z klasy przypisanej do harmonogramu
        int maxSlots = schedule.getClassId().getSlots();
        return currentUsers < maxSlots;
    }
    
    public boolean checkPass(Long passId) {
        System.out.println(passDAO.checkPass(passId));
        return passDAO.checkPass(passId);
    }
    
    public boolean checkToSignOut(Long scheduleId, Long userId) {
        Schedule schedule = findScheduleById(scheduleId);
        User user = findUserById(userId);

        // Sprawdź, czy użytkownik jest już zapisany
        ScheduleUser existingScheduleUser = scheduleUserDAO.findByScheduleAndUser(schedule, user);
        if (existingScheduleUser != null) {
            return true;
        }
        return false;
    }
    
    public void deleteScheduleUser(Long scheduleId, Long userId) {
        Schedule schedule = findScheduleById(scheduleId);
        User user = findUserById(userId);
        ScheduleUser existingScheduleUser = scheduleUserDAO.findByScheduleAndUser(schedule, user);
        if (existingScheduleUser != null) {
            scheduleUserDAO.delete(existingScheduleUser.getId());
        }
    }
    
}
