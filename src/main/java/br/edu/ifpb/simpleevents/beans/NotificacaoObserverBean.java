package br.edu.ifpb.simpleevents.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.entity.CandidatoVaga;
import br.edu.ifpb.simpleevents.entity.pattern.observer.Observer;
import br.edu.ifpb.simpleevents.entity.pattern.observer.Subject;

@Named(value="notificacoesUser")
@ApplicationScoped
public class NotificacaoObserverBean {
//extends Observer {
	private String mensagem = "teste";
	private Map<Long,List<String>> notificacoesNaoLidas = new HashMap<Long, List<String>>();
	private Map<Long,List<String>> notificacoesLidas = new HashMap<Long, List<String>>();
	
//	public NotificacaoObserverBean (Subject candidatoVaga){
//		this.candidatoVaga = candidatoVaga;
//		this.candidatoVaga.attach(this);
//	}
	
//	@Override
//	public void update() {
//		CandidatoVaga vaga = ((CandidatoVaga) this.candidatoVaga);
//		String notificacao = "A vaga no evento "+vaga.getVaga().getEvento().getDescricao()+" mudou o status para "+vaga.getStatus();
//		if(notificacoesNaoLidas.containsKey(vaga.getCandidato().getId())) {
//			List<String> notificacoesUser = notificacoesNaoLidas.get(vaga.getCandidato().getId());	
//			notificacoesUser.add(notificacao);
//			notificacoesNaoLidas.put(vaga.getCandidato().getId(),notificacoesUser);
//		}else {
//			ArrayList<String> notificacoes = new ArrayList<String>();
//			notificacoes.add(notificacao);
//			notificacoesNaoLidas.put(vaga.getCandidato().getId(), notificacoes);
//		}
//		System.out.println(notificacoesNaoLidas.get(vaga.getCandidato().getId()));
//	}
//	
	public String notificar() {
		System.out.println("teste");
		return "/WEB-INF/facelets/gabaritos/observer.xhtml";
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
		
}
