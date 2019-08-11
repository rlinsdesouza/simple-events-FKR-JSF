package br.edu.ifpb.simpleevents.controller;

import java.io.Serializable;

import javax.inject.Inject;

import br.edu.ifpb.simpleevents.dao.ParticipanteDAO;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;
import br.edu.ifpb.simpleevents.security.PasswordUtil;

public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ParticipanteDAO participanteDAO;
	
	
	public ParticipanteComposite isValido(String usuario, String senha) {

		ParticipanteComposite user = participanteDAO.findByEmail(usuario);
		
		if (user == null || !PasswordUtil.encryptMD5(senha).equals(user.getSenha())) {
			user = null;
		}

		return user;
	}
}
