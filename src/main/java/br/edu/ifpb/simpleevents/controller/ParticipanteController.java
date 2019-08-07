package br.edu.ifpb.simpleevents.controller;

import br.edu.ifpb.simpleevents.dao.ParticipanteDAO;
import br.edu.ifpb.simpleevents.dao.Transactional;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ParticipanteController implements Serializable {

    @Inject
    private ParticipanteDAO participanteDAO;

    @Transactional
    public ParticipanteComposite save (ParticipanteComposite p) {
        if (p.getId() == null)
            this.participanteDAO.create(p);
        else
            this.participanteDAO.update(p);
        return p;
    }

    public ParticipanteComposite findByEmail (String email) {
        return this.participanteDAO.findByEmail(email);
    }

    public List<ParticipanteComposite> findAll() {
        return new ArrayList<ParticipanteComposite>(this.participanteDAO.read());
    }

    @Transactional
    public void delete(ParticipanteComposite p){
        ParticipanteComposite participante = this.findByEmail(p.getEmail());
        this.participanteDAO.delete(participante);
    }
}
