package com.javaproj.pilatesproject.classes;

import com.javaproj.pilatesproject.dao.ClassDAO;
import com.javaproj.pilatesproject.entities.Class;
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
public class ClassEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_CLASS_LIST = "classList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Class classes = new Class();
	private Class loaded = null;

	@EJB
	ClassDAO classesDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Class getClasses() {
		return classes;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Class) flash.get("classes");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			classes = loaded;
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
			if (classes.getId()== null) {
				classesDAO.create(classes);
			} else {
				// existing record
				classesDAO.update(classes);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_CLASS_LIST;
	}
}
