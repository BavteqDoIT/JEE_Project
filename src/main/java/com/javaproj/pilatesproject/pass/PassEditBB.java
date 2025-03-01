package com.javaproj.pilatesproject.pass;

import com.javaproj.pilatesproject.dao.PassDAO;
import com.javaproj.pilatesproject.entities.Pass;
import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

@Named
@ViewScoped
public class PassEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PASS_LIST = "passList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Pass pass = new Pass();
	private Pass loaded = null;
        
	@EJB
	PassDAO passDAO;
        
	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Pass getPass() {
		return pass;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Pass) flash.get("pass");
		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			pass = loaded;
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
			if (pass.getId()== null) {
				passDAO.create(pass);
			} else {
				// existing record
				passDAO.update(pass);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PASS_LIST;
	}
}
