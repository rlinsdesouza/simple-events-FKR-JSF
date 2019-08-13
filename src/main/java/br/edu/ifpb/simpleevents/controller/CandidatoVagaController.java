package br.edu.ifpb.simpleevents.controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifpb.simpleevents.dao.AvaliacaoEventoDAO;
import br.edu.ifpb.simpleevents.dao.CandidatoVagaDAO;
import br.edu.ifpb.simpleevents.dao.EventoDAO;
import br.edu.ifpb.simpleevents.dao.Transactional;
import br.edu.ifpb.simpleevents.dao.VagaDAO;
import br.edu.ifpb.simpleevents.entity.AvaliacaoEvento;
import br.edu.ifpb.simpleevents.entity.CandidatoVaga;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.User;
import br.edu.ifpb.simpleevents.entity.Vaga;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;
import br.edu.ifpb.simpleevents.facade.LoginFacade;

public class CandidatoVagaController implements Serializable {
	private static final long serialVersionUID = 1L;

    @Inject
    private CandidatoVagaDAO candidatoVagaDAO;
    
    @Inject
    private AvaliacaoEventoDAO avaliacoesDAO;
    
    @Inject
    private VagaDAO vagaDAO;
    
    @Inject
    private EventoDAO eventoDAO;

    public void create (CandidatoVaga candidatoVaga) {
        if (candidatoVaga.getId() != null)
            this.candidatoVagaDAO.create(candidatoVaga);
        else
            this.candidatoVagaDAO.update(candidatoVaga);
    }
    
    public List<CandidatoVaga> getCandidaturas (ParticipanteComposite user) {
    	return candidatoVagaDAO.findByCandidato(user);
    }
    
    public CandidatoVaga findById (Long id) {
    	return candidatoVagaDAO.read(id);
    }
    
    public List<AvaliacaoEvento> getAvaliacoesPorUsuario (ParticipanteComposite user) {
    	return avaliacoesDAO.findByParticipante(user);
    }
    
    @Transactional
    public boolean delete(Long id) throws Exception {
		CandidatoVaga trabalho = candidatoVagaDAO.read(id);
		Vaga vaga = vagaDAO.read(trabalho.getVaga().getId());
		ParticipanteComposite usuarioLogado = LoginFacade.getParticipanteLogado();
		if (usuarioLogado.getId() != trabalho.getCandidato().getId()) {
            throw new Exception("você não pode alterar este evento");
        }
		if (trabalho != null) {
			vaga.remove(trabalho);
			candidatoVagaDAO.delete(trabalho);
			return true;			
		}
		return false;
	}
    
	@Transactional
	public boolean avaliarEvento(CandidatoVaga candidatura, Evento evento,int notaAvaliacaoEvento) {
		AvaliacaoEvento avaliacao = new AvaliacaoEvento();
		avaliacao.setEvento(evento);
		avaliacao.setNotaAvaliacaoEvento(notaAvaliacaoEvento);
		avaliacao.setParticipante((User) LoginFacade.getParticipanteLogado());
		avaliacao = avaliacoesDAO.create(avaliacao);
		evento.add(avaliacao);
		eventoDAO.update(evento);
		return true;
	}

}
