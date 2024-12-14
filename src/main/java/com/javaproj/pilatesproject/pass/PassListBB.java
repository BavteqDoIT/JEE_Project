package com.javaproj.pilatesproject.pass;

import com.javaproj.pilatesproject.dao.PassDAO;
import com.javaproj.pilatesproject.entities.Pass;
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
public class PassListBB {
    private static final String PAGE_PASS_EDIT = "passEdit?faces-redirect=true";
    private List<Pass> passList;
    private Pass newPass = new Pass();
    private Pass selectedPass;  // Dodajemy pole dla edytowanego karnetu

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    PassDAO passDAO;

    public Pass getNewPass() {
        return newPass;
    }

    public void setNewPass(Pass newPass) {
        this.newPass = newPass;
    }

    public Pass getSelectedPass() {
        return selectedPass;
    }

    public void setSelectedPass(Pass selectedPass) {
        this.selectedPass = selectedPass;
    }

    public List<Pass> getFullList() {
        if (passList == null) {
            passList = passDAO.findAll();
        }
        return passList;
    }

    public void deletePass(Long id) {
        passDAO.delete(id);
        passList = passDAO.findAll();
        flash.put("message", "Pass successfully deleted.");
    }

    public String saveNewPass() {
        System.out.println("saveNewPassmethod called");
        passDAO.create(newPass);
        flash.put("message", "Pass successfully created.");
        return "passList?faces-redirect=true";
    }

    // Metoda edytująca karnet
    public String saveEditedPass() {
        System.out.println("saveEditedPass method called");
        if (selectedPass != null) {
            passDAO.update(selectedPass);  // Zaktualizowanie karnetu w bazie
            flash.put("message", "Pass successfully updated.");
        }
        return "passList?faces-redirect=true";
    }
    
    public String editPass(Pass pass){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("pass", pass);
		
		return PAGE_PASS_EDIT;
	}
}
