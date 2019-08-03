package br.edu.ifpb.simpleevents.beans;


import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named (value="SPABean")
@SessionScoped
public class SPABean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String navigation = "/WEB-INF/facelets/gabaritos/navigation.xhtml";
	
	public String getNavigation() {
		return navigation;	
	}
	
	public void setNavigation (String navigation) {
		this.navigation = navigation;
	}

}
