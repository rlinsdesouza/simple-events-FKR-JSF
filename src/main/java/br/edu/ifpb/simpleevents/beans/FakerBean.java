package br.edu.ifpb.simpleevents.beans;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.facade.FakerFacade;

@Named
public class FakerBean {
	
	@Inject
	private FakerFacade faker;
	
	public String create() {
		return faker.createDataFaker();
	}

}
