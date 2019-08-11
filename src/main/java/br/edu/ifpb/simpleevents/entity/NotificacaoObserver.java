package br.edu.ifpb.simpleevents.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.simpleevents.entity.pattern.observer.Observer;
import br.edu.ifpb.simpleevents.entity.pattern.observer.Subject;


public class NotificacaoObserver extends Observer {
	private Map<Long,List<String>> notificacoesNaoLidas = new HashMap<Long, List<String>>();
	private Map<Long,List<String>> notificacoesLidas = new HashMap<Long, List<String>>();
	
	public NotificacaoObserver (Subject candidatoVaga){
		this.candidatoVaga = candidatoVaga;
		this.candidatoVaga.attach(this);
	}
	
	@Override
	public void update() {
		CandidatoVaga vaga = ((CandidatoVaga) this.candidatoVaga);
		String notificacao = "A vaga no evento "+vaga.getVaga().getEvento().getDescricao()+" mudou o status para "+vaga.getStatus();
		if(notificacoesNaoLidas.containsKey(vaga.getCandidato().getId())) {
			List<String> notificacoesUser = notificacoesNaoLidas.get(vaga.getCandidato().getId());	
			notificacoesUser.add(notificacao);
			notificacoesNaoLidas.put(vaga.getCandidato().getId(),notificacoesUser);
		}else {
			ArrayList<String> notificacoes = new ArrayList<String>();
			notificacoes.add(notificacao);
			notificacoesNaoLidas.put(vaga.getCandidato().getId(), notificacoes);
		}
	}
		
}
