package com.javaproj.pilatesproject.user;

import com.javaproj.pilatesproject.dao.UsersDAO;
import com.javaproj.pilatesproject.entities.Users;
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
public class UserListBB {
    private static final String PAGE_USER_EDIT = "userEdit?faces-redirect=true";
    private String surname;
    private List<Users> userList;
    private Users newUser = new Users();
    private Users selectedUser;  // Dodajemy pole dla edytowanego użytkownika

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    UsersDAO usersDAO;

    public Users getNewUser() {
        return newUser;
    }

    public void setNewUser(Users newUser) {
        this.newUser = newUser;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<Users> getFullList() {
        if (userList == null) {
            userList = usersDAO.findAll();
        }
        return userList;
    }

    public void deleteUser(Long id) {
        usersDAO.delete(id);
        userList = usersDAO.findAll();
        flash.put("message", "User successfully deleted.");
    }

    public String saveNewUser() {
        System.out.println("saveNewUser method called");
        usersDAO.create(newUser);
        flash.put("message", "User successfully created.");
        return "userList?faces-redirect=true";
    }

    // Metoda edytująca użytkownika
    public String saveEditedUser() {
        System.out.println("saveEditedUser method called");
        if (selectedUser != null) {
            usersDAO.update(selectedUser);  // Zaktualizowanie użytkownika w bazie
            flash.put("message", "User successfully updated.");
        }
        return "userList?faces-redirect=true";
    }
    
    public String editUser(Users user){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("user", user);
		
		return PAGE_USER_EDIT;
	}
}
