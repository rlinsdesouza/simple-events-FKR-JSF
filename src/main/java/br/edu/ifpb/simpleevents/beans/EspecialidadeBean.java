package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.jboss.weld.bean.builtin.FacadeInjectionPoint;

import br.edu.ifpb.simpleevents.controller.EspecialidadeController;
import br.edu.ifpb.simpleevents.entity.Especialidade;
import br.edu.ifpb.simpleevents.facade.LoginFacade;

@Named(value = "especialidadeBean")
@ViewScoped
public class EspecialidadeBean extends GenericBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Especialidade> especialidades;
	
	private Especialidade especialidade;
	
	@Inject
	private EspecialidadeController controller;
	
	@PostConstruct
	private void init() {
		Especialidade especialidade = (Especialidade) this.getFlash("especialidade");
		if (especialidade != null) {
			this.especialidade = especialidade;
		}else {
			this.especialidades = controller.consultar();
			this.especialidade = new Especialidade();
		}
	}
	
	
	public String salvar() {
		String view = null;
		try {
			controller.cadastrar(especialidade);
			this.addSuccessMessage("Salvo com sucesso");
			view = "/especialidade/list?faces-redirect=true";
			this.especialidade = new Especialidade();
		}catch(PersistenceException e){
			this.addErrorMessage("Erro ao tentar salvar a especialidade");
		}
		return view;
	}
	
	public String editar(Especialidade espec) {
		this.setEspecialidade(espec);
		this.setFlash("especialidade", especialidade);
		return "/especialidade/form?faces-redirect=true";
	}

	public String excluir(Especialidade especialidade){
		this.controller.deletar(especialidade);
		this.especialidades = this.controller.consultar();
		return "list.xhtml?faces-redirect=true";
	} 

	public List<Especialidade> getEspecialidades(){
		return this.especialidades;
	}
	
	public void setEspecialidades(ArrayList<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}
	
	public Especialidade getEspecialidade() {
		return this.especialidade;
	}
	
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
	public String list() {
		if(LoginFacade.isAuthenticated() && LoginFacade.getParticipanteLogado().getAdmin() ) {
			return "/WEB-INF/facelets/especialidade/list.xhtml";
		}else {
			return "/WEB-INF/facelets/gabaritos/forbidden.xhtml";
		}
	}
	
	public String form() {
		if(LoginFacade.isAuthenticated() && LoginFacade.getParticipanteLogado().getAdmin() ) {
			return "/WEB-INF/facelets/especialidade/form.xhtml";
		}else {
			return "/WEB-INF/facelets/gabaritos/forbidden.xhtml";
		}
	}

}
