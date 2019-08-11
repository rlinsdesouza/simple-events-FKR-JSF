package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.controller.ParticipanteController;
import br.edu.ifpb.simpleevents.dao.InstituicaoDAO;
import br.edu.ifpb.simpleevents.dao.Transactional;
import br.edu.ifpb.simpleevents.entity.Instituicao;
import br.edu.ifpb.simpleevents.entity.User;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;
import br.edu.ifpb.simpleevents.security.PasswordUtil;

@Named(value = "instituicaoBean")
@ViewScoped
public class InstituicaoBean extends GenericBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Instituicao instituicao;

	private String vinculados;

	@Inject
	private ParticipanteController participanteController;

	public String getVinculados() {
		return vinculados;
	}

	public void setVinculados(String vinculados) {
		this.vinculados = vinculados;
	}

	public Instituicao getInstituicao() {
		return this.instituicao;
	}
	
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	
	public InstituicaoBean() {
		this.instituicao = new Instituicao();
	}

	@PostConstruct
	public void init () {
		Instituicao instituicao = (Instituicao) this.getFlash("instituicao");
		if (instituicao != null) {
			this.instituicao = instituicao;
			String vinculos = "";
			for (ParticipanteComposite p: this.instituicao.getParticipantes())
				vinculos += p.getEmail() + "-";
			if (vinculos.length() > 0) {
				vinculos = vinculos.substring (0, vinculos.length() - 1);
			}
			this.vinculados = vinculos;
		} else
			this.instituicao = new Instituicao();
	}

	public String save () {
		this.instituicao.setSenha(PasswordUtil.encryptMD5(this.instituicao.getSenha()));
		String[] emails = this.vinculados.split("-");
		this.instituicao.setParticipantes(new ArrayList<>());
		for(String email: emails) {
			ParticipanteComposite participante = this.participanteController.findByEmail(email);
			if (participante != null)
				this.instituicao.add(participante);
		}
		this.participanteController.save(this.instituicao);
		return "/index.xhtml?faces-redirect=true";
	}
}