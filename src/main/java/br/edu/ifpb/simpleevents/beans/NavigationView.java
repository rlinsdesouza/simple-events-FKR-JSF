package br.edu.ifpb.simpleevents.beans;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean (name="navigation")
@SessionScoped
public class NavigationView {
	
//	private String navigation = "WEB-INF/facelets/gabaritos/navigation.xhtml";
	
	public String login() {
		return "/facelets/components/login.xhtml";
	}
	
//	public String getNavigation() {
//		return navigation;	
//	}
//	
//	public void setNavigation (String navigation) {
//		this.navigation = navigation;
//	}

}
