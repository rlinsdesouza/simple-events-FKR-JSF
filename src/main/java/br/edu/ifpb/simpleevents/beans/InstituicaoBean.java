package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.dao.InstituicaoDAO;
import br.edu.ifpb.simpleevents.dao.Transactional;
import br.edu.ifpb.simpleevents.entity.Instituicao;

@Named(value = "instituicaoBean")
@RequestScoped
public class InstituicaoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	InstituicaoDAO instituicaoDAO;
	
	private Instituicao instituicao;
	
	public Instituicao getInstituicao() {
		return this.instituicao;
	}
	
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	
	public InstituicaoBean() {
		this.instituicao = new Instituicao();
	}
	
	@Transactional
	public String cadastrar () {
		System.out.println(this.instituicaoDAO);
		this.instituicaoDAO.create(this.instituicao);
		return "index";
	}
	


}
