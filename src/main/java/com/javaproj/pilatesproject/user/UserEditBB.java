package com.javaproj.pilatesproject.user;

import com.javaproj.pilatesproject.dao.UserDAO;
import com.javaproj.pilatesproject.entities.Pass;
import com.javaproj.pilatesproject.entities.User;
import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Named
@ViewScoped
public class UserEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_USER_LIST = "userList?faces-redirect=true";
        private static final String PAGE_USER_PASS = "passList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private User user = new User();
	private User loaded = null;
        
        private Pass pass = new Pass();
        private Pass loadedPass = null;

	@EJB
	UserDAO userDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public User getUser() {
		return user;
	}
        
        public Pass getPass() {
            return pass;
        }

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (User) flash.get("user");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			user = loaded;
			// session.removeAttribute("person");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("personList.xhtml");
			// context.responseComplete();
			// }
		}

	}

	public String saveData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (user.getId()== null) {
				userDAO.create(user);
			} else {
				// existing record
				userDAO.update(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_USER_LIST;
	}
        
        public void onLoadPass() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (User) flash.get("user");
                loadedPass = (Pass) flash.get("pass");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			user = loaded;
                        pass = loadedPass;
                        
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("personList.xhtml");
			// context.responseComplete();
			// }
		}

	}
        
        public void refreshSession(User updatedUser) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

            // Tworzymy obiekt RemoteClient, aby przechować zaktualizowane dane
            RemoteClient<User> client = new RemoteClient<>();
            client.setDetails(updatedUser);

            // Pamiętaj o przypisaniu roli (jeśli jest to wymagane)
            String role = userDAO.getUserRoleFromDatabase(updatedUser);
            if (role != null) {
                client.getRoles().clear();  // Czyść poprzednie role
                client.getRoles().add(role);
            }

            // Zapisz obiekt RemoteClient w sesji
            client.store(request);
        }
        
        public String savePassData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_USER_PASS;
		}
                    if (user.getPassesId() == null) {
                            user.setPassesId(new Pass()); // Tworzenie nowego obiektu, jeśli null
                        user.getPassesId().setId(pass.getId());
                        }
                        
			// session.removeAttribute("person");
                        refreshSession(user);
		try {
			if (user.getId()== null) {
				userDAO.create(user);
			} else {
				// existing record
				userDAO.update(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_USER_PASS;
	}
}
