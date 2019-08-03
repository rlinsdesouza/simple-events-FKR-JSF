package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named (value="navigation")
@SessionScoped
public class NavigationView implements Serializable {
	
	private String navigation = "WEB-INF/facelets/gabaritos/navigation.xhtml";
	
	private static final long serialVersionUID = 1L;
	
	public String login() {
		return "/login.xhtml";
	}
	
	public String getNavigation() {
		return navigation;	
	}
	
	public void setNavigation (String navigation) {
		this.navigation = navigation;
	}

}
