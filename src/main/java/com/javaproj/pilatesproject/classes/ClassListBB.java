package com.javaproj.pilatesproject.classes;

import com.javaproj.pilatesproject.dao.ClassDAO;
import com.javaproj.pilatesproject.entities.Class;
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
public class ClassListBB {
    private static final String PAGE_CLASS_EDIT = "classEdit?faces-redirect=true";
    private List<Class> classesList;
    private Class newClass = new Class();
    private Class selectedClass;  // Dodajemy pole dla edytowanych zajęć

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    ClassDAO classesDAO;

    public Class getNewClass() {
        return newClass;
    }

    public void setNewClass(Class newClass) {
        this.newClass = newClass;
    }

    public Class getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(Class selectedClass) {
        this.selectedClass = selectedClass;
    }

    public List<Class> getFullList() {
        if (classesList == null) {
            classesList = classesDAO.findAll();
        }
        return classesList;
    }

    public void deleteClass(Long id) {
        classesDAO.delete(id);
        classesList = classesDAO.findAll();
        flash.put("message", "Class successfully deleted.");
    }

    public String saveNewClass() {
        System.out.println("saveNewPassmethod called");
        classesDAO.create(newClass);
        flash.put("message", "Class successfully created.");
        return "classList?faces-redirect=true";
    }

    // Metoda edytująca karnet
    public String saveEditedClass() {
        System.out.println("saveEditedClass method called");
        if (selectedClass != null) {
            classesDAO.update(selectedClass);  // Zaktualizowanie zajęć w bazie
            flash.put("message", "Class successfully updated.");
        }
        return "classesList?faces-redirect=true";
    }
    
    public String editClass(Class classes){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("classes", classes);
		
		return PAGE_CLASS_EDIT;
	}
}
