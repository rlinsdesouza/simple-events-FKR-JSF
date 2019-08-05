package br.edu.ifpb.simpleevents.beans;



import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.dao.Transactional;
import br.edu.ifpb.simpleevents.facade.FakerFacade;

@Named(value="faker")
public class FakerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FakerFacade faker;
	
	@Transactional
	public String create() throws NoSuchAlgorithmException, InvalidKeySpecException {
		return faker.createDataFaker();
	}

}
