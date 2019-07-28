package br.edu.ifpb.simpleevents.beans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean (name="SPABean")
@SessionScoped
public class SPABean {
	
	private String navigation = "/facelets/gabaritos/navigation.xhtml";
	
	public String getNavigation() {
		return navigation;	
	}
	
	public void setNavigation (String navigation) {
		this.navigation = navigation;
	}

}
