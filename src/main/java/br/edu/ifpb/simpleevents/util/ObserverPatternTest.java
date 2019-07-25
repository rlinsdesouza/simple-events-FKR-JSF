package br.edu.ifpb.simpleevents.util;

import java.time.LocalDateTime;

import br.edu.ifpb.simpleevents.desingPattern.Observer.Observer;
import br.edu.ifpb.simpleevents.desingPattern.Observer.Subject;
import br.edu.ifpb.simpleevents.entity.CandidatoVaga;
import br.edu.ifpb.simpleevents.entity.Especialidade;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.NotificacaoObserver;
import br.edu.ifpb.simpleevents.entity.Status;
import br.edu.ifpb.simpleevents.entity.User;
import br.edu.ifpb.simpleevents.entity.Vaga;

public class ObserverPatternTest {
	public static void main(String[] args) {
		Especialidade especialidade = new Especialidade(Long.getLong("1"), "Atendente", "teste");
		Evento evento = new Evento("Xurras", LocalDateTime.now(), "Casa das primas");
		Vaga vaga = new Vaga(especialidade, 10, evento);
		Subject vagaSubject = new CandidatoVaga(Long.getLong("1"), vaga,0, null, new User());

		Observer ob1 = new NotificacaoObserver(vagaSubject);

		System.out.println("Primeiro status vaga = Aguardando aprovacao");	
		((CandidatoVaga) vagaSubject).setStatus(Status.AGUARDANDO_APROVACAO);
		System.out.println("\nSegundo status vaga = APROVADO");	
		((CandidatoVaga) vagaSubject).setStatus(Status.APROVADO);
	}
}
