package br.edu.ifpb.simpleevents.facade;

import br.edu.ifpb.simpleevents.beans.LoginBean;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class LoginFacade implements Serializable {

    private static ParticipanteComposite participanteLogado;


    public static boolean isAuthenticated () {
        if (participanteLogado != null)
            return true;
        return false;
    }

    public static String getParticipanteName () {
        return participanteLogado.getEmail().split("@")[0];
    }

    public static void setParticipanteLogado(ParticipanteComposite p) {
        participanteLogado = p;
    }

    public ParticipanteComposite getParticipanteLogado() {
        return participanteLogado;
    }

}
