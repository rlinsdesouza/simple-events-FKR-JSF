package br.edu.ifpb.simpleevents.controller;

import br.edu.ifpb.simpleevents.dao.ParticipanteDAO;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.AssertFalse;
import java.io.Serializable;
import java.util.List;

@Named
public class ParticipanteController implements Serializable {

    @Inject
    private ParticipanteDAO participanteDAO;

    public ParticipanteComposite create (ParticipanteComposite p) {
        if (p.getId() == null)
            this.participanteDAO.create(p);
        else
            this.participanteDAO.update(p);
        return p;
    }

    public List<ParticipanteComposite> read() {
        return this.participanteDAO.getParticipantes();
    }

    public void delete(ParticipanteComposite p){
        this.participanteDAO.delete(p);
    }
}
