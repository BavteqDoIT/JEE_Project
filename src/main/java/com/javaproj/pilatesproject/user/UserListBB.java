package com.javaproj.pilatesproject.user;

import com.javaproj.pilatesproject.dao.UserDAO;
import com.javaproj.pilatesproject.entities.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

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
    private List<User> userList;
    private User newUser = new User();
    private User selectedUser;  // Dodajemy pole dla edytowanego użytkownika
    private LazyDataModel<User> lazyModel;

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    UserDAO userDAO;
    
    @PostConstruct
    public void init() {
        lazyModel = new LazyDataModel<User>() {
            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return (int) userDAO.countUsers();
            }

            @Override
            public List<User> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

                List<User> users = userDAO.findUsers(offset, pageSize);
                return users;
            }

        };
    }

    public LazyDataModel<User> getLazyModel(){
        return lazyModel;
    }
    
    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<User> getFullList() {
        if (userList == null) {
            userList = userDAO.findAll();
        }
        return userList;
    }

    public void deleteUser(Long id) {
        userDAO.delete(id);
        userList = userDAO.findAll();
        flash.put("message", "User successfully deleted.");
    }

    public String saveNewUser() {
        System.out.println("saveNewUser method called");
        userDAO.create(newUser);
        flash.put("message", "User successfully created.");
        return "userList?faces-redirect=true";
    }

    // Metoda edytująca użytkownika
    public String saveEditedUser() {
        System.out.println("saveEditedUser method called");
        if (selectedUser != null) {
            userDAO.update(selectedUser);  // Zaktualizowanie użytkownika w bazie
            flash.put("message", "User successfully updated.");
        }
        return "userList?faces-redirect=true";
    }
    
    public String editUser(User user){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("user", user);
		
		return PAGE_USER_EDIT;
	}
}
