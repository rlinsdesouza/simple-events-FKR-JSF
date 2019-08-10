package br.edu.ifpb.simpleevents.controller;

import br.edu.ifpb.simpleevents.dao.CandidatoVagaDAO;
import br.edu.ifpb.simpleevents.entity.CandidatoVaga;

import javax.inject.Inject;

public class CandidatoVagaController {

    @Inject
    private CandidatoVagaDAO candidatoVagaDAO;

    public void create (CandidatoVaga candidatoVaga) {
        if (candidatoVaga.getId() != null)
            this.candidatoVagaDAO.create(candidatoVaga);
        else
            this.candidatoVagaDAO.update(candidatoVaga);
    }

}
