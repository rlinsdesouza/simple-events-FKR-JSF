package br.edu.ifpb.simpleevents.controller;

import java.util.List;

import javax.inject.Inject;

import br.edu.ifpb.simpleevents.dao.AvaliacaoEventoDAO;
import br.edu.ifpb.simpleevents.dao.CandidatoVagaDAO;
import br.edu.ifpb.simpleevents.entity.AvaliacaoEvento;
import br.edu.ifpb.simpleevents.entity.CandidatoVaga;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

public class CandidatoVagaController {

    @Inject
    private CandidatoVagaDAO candidatoVagaDAO;
    
    @Inject
    private AvaliacaoEventoDAO avaliacoesDAO;

    public void create (CandidatoVaga candidatoVaga) {
        if (candidatoVaga.getId() != null)
            this.candidatoVagaDAO.create(candidatoVaga);
        else
            this.candidatoVagaDAO.update(candidatoVaga);
    }
    
    public List<CandidatoVaga> getCandidaturas (ParticipanteComposite user) {
    	return candidatoVagaDAO.findByCandidato(user);
    }
    
    public List<AvaliacaoEvento> getAvaliacoesPorUsuario (ParticipanteComposite user) {
    	return avaliacoesDAO.findByParticipante(user);
    }

}
