package br.edu.ifpb.simpleevents.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifpb.simpleevents.dao.CandidatoVagaDAO;
import br.edu.ifpb.simpleevents.dao.EspecialidadeDAO;
import br.edu.ifpb.simpleevents.dao.EventoDAO;
import br.edu.ifpb.simpleevents.dao.ParticipanteDAO;
import br.edu.ifpb.simpleevents.dao.Transactional;
import br.edu.ifpb.simpleevents.dao.VagaDAO;
import br.edu.ifpb.simpleevents.entity.CandidatoVaga;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.Status;
import br.edu.ifpb.simpleevents.entity.StatusEvento;
import br.edu.ifpb.simpleevents.entity.User;
import br.edu.ifpb.simpleevents.entity.Vaga;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;
import br.edu.ifpb.simpleevents.entity.pattern.singleton.LogSingleton;
import br.edu.ifpb.simpleevents.facade.LoginFacade;

public class EventoController implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
    private EventoDAO eventoDAO;

    @Inject
    private ParticipanteDAO participanteDAO;

    @Inject
    private EspecialidadeDAO especDAO;

    @Inject
    private VagaDAO vagaDAO;

    @Inject
    private CandidatoVagaDAO candidatoVagaDAO;
    
    
    @Transactional
    public Evento save(Evento evento) {    	
    	ParticipanteComposite currentUser = LoginFacade.getParticipanteLogado();
        evento.setDono(currentUser);
        evento.setStatus(StatusEvento.ABERTO);
        return eventoDAO.create(evento); 
    }
    
	public ArrayList<Evento> consultar() {
		ArrayList<Evento> eventos = (ArrayList<Evento>) eventoDAO.read();
		return eventos;
	}
	
	public List<Evento> findByName (String nome) {
		return eventoDAO.findByName(nome);
	}

	@Transactional
	public Evento cadastrar(Evento evento) {
		if(evento.getId() == null) {
			eventoDAO.create(evento);
			LogSingleton.getInstance().escrever("Create - " + evento.getDescricao() );
		}else {
			eventoDAO.update(evento);
			LogSingleton.getInstance().escrever("Update - " + evento.getDescricao() );
		}
		return evento;
	}
    
    public List<Evento> findAll() {
    	return eventoDAO.read();
    }
    
    public Evento findById(Long id) {
    	return eventoDAO.read(id);
    }

    public List<Evento> findByDono(ParticipanteComposite dono) {
		return eventoDAO.findByDono(dono);
	}

    @Transactional
    public Vaga criarVaga (Vaga vaga) {
    	return vagaDAO.create(vaga);
    }

    @Transactional
    public void adicionarCandidato (Evento evento, Vaga vaga) {

		CandidatoVaga candidatoVaga;
		candidatoVaga = new CandidatoVaga();
		candidatoVaga.setVaga(vaga);
		candidatoVaga.setStatus(Status.AGUARDANDO_APROVACAO);
		candidatoVaga.setCandidato((User) LoginFacade.getParticipanteLogado());
		System.out.println(candidatoVaga);
		candidatoVagaDAO.create(candidatoVaga);
	}
    
    @Transactional
    public Evento update(Evento e) throws Exception {  
//      Evento eventoAntigo = e;
//      Evento evento = new Evento();
//      evento.setVagas(new ArrayList<>(eventoAntigo.getVagas()));
//      evento.setAvaliacaoEventos(new ArrayList<>(eventoAntigo.getAvaliacaoEventos()));
//      evento.setDono(eventoAntigo.getDono());
//      
//      ParticipanteComposite usuarioLogado = LoginFacade.getParticipanteLogado();
//      if (usuarioLogado.getId() != e.getDono().getId()) {
//          throw new Exception("você não pode alterar este evento");
//      }
//      if (especialidades != null) {
//          for (Vaga v : this.descartarVagas(evento, especialidades))
//              vagaDAO.deleteById(v.getId());
//
//          for (int i = 0; i < especialidades.size(); i++) {
//              Vaga v = evento.findVagaByEspecialidade(especialidades.get(i));
//              if (v != null) {
//                  v.setQtdVagas(quantidades.get(i));
//              } else {
//                  Vaga vaga = new Vaga(especDAO.getOne(especialidades.get(i)), quantidades.get(i), evento);
//                  vagaDAO.save(vaga);
//                  evento.add(vaga);
//              }
//          }
//      }
//      evento.setDono(userDAO.findByEmail(auth.getName()));
//      evento.setStatus(status);
      
      return eventoDAO.update(e); 
  }
  
  @Transactional  
  public Evento delete(Long id) throws Exception {
      Evento evento = eventoDAO.read(id);
      List<CandidatoVaga> candidaturas = candidatoVagaDAO.findByEvento(evento);
      System.out.println(candidaturas);
      ParticipanteComposite usuarioLogado = LoginFacade.getParticipanteLogado();
      if (usuarioLogado.getId() != evento.getDono().getId()) {
      	  throw new Exception("Você não pode deletar este evento por nao ser dono do evento");
      }
      if (evento != null && candidaturas.size()==0) {
          eventoDAO.delete(evento);
          LogSingleton.getInstance().escrever("Delete - " + evento.getDescricao() );
          return null;
      }else {
    	evento.setStatus(StatusEvento.CANCELADO);
    	eventoDAO.update(evento);
    	return evento;
      }
  }




//    @Transactional
//	public void avaliarEvento(@PathVariable("id") Long id,
//			Principal userlogado,
//			@RequestParam("notaAvaliacaoEvento") int notaAvaliacaoEvento,
//			RedirectAttributes att
//			) {
//		CandidatoVaga candidatura = candidatoVagaDAO.findById(id).get();
//		AvaliacaoEvento avaliacao = new AvaliacaoEvento();
//		Evento evento = eventoDAO.findById(candidatura.getVaga().getEvento().getId()).get();
//		avaliacao.setEvento(evento);
//		avaliacao.setNotaAvaliacaoEvento(notaAvaliacaoEvento);
//		avaliacao.setParticipante(userDAO.findByEmail(userlogado.getName()));
//		avaliacao = avaliacaoEventoDAO.save(avaliacao);
//		evento.add(avaliacao);
//		eventoDAO.save(evento);   
//		ModelAndView model = new ModelAndView("redirect:/candidaturas/meustrabalhos");
//		att.addFlashAttribute("mensagem", "Avaliado com sucesso!");
//		return model;
//	}

//    @RequestMapping(method = RequestMethod.GET)
//    public ModelAndView list(Authentication auth) {
//        ModelAndView modelList = new ModelAndView("evento/list");
//        User usuarioLogado = userDAO.findByEmail(auth.getName());
//        List<Evento> eventos = eventoDAO.findAll();
//        modelList.addObject("userLog", usuarioLogado);
//        modelList.addObject("eventos", eventos);
//        modelList.addObject("eventosVagas", converterListasEventos());
//        return modelList;
//    }

//    @RequestMapping("/edit/{id}")
//    public ModelAndView edit(@PathVariable("id") Long id,
//                             RedirectAttributes att,
//                             Authentication auth) {
//        ModelAndView modelForm = new ModelAndView("evento/form");
//        Evento evento = eventoDAO.findById(id).get();
//        User usuarioLogado = userDAO.findByEmail(auth.getName());
//        if (usuarioLogado.getId() != evento.getDono().getId()) {
//            att.addAttribute("mensagemerro", "você não pode alterar este evento");
//            return new ModelAndView("redirect:/");
//        }
//        modelForm.addObject("especialidades", especDAO.findAll());
//        modelForm.addObject("evento", evento);
//        return modelForm;
//    }
//
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
//    public ModelAndView update(
//            Authentication auth,
//            @PathVariable("id") Long id,
//            RedirectAttributes att,
//            @Valid Evento evento,
//            BindingResult result,
//            @RequestParam(value = "status") StatusEvento status,
//            @RequestParam(value = "especialidades", required = false) List<Long> especialidades,
//            @RequestParam(value = "quantidades", required = false) List<Integer> quantidades) {
//        
//
//        Evento eventoAntigo = eventoDAO.findById(id).get();
//        evento.setVagas(new ArrayList<>(eventoAntigo.getVagas()));
//        evento.setAvaliacaoEventos(new ArrayList<>(eventoAntigo.getAvaliacaoEventos()));
//        evento.setDono(eventoAntigo.getDono());
//        
//    	User usuarioLogado = userDAO.findByEmail(auth.getName());
//        if (usuarioLogado.getId() != evento.getDono().getId()) {
//            att.addAttribute("mensagemerro", "você não pode alterar este evento");
//            return new ModelAndView("redirect:/eventos");
//        }
//        
//    	if (result.hasErrors()) {
//            evento.setId(id);
//            ModelAndView modelForm = new ModelAndView("evento/form");
//            return modelForm.addObject("especialidades", especDAO.findAll());
//        }
//        if (especialidades != null) {
//            for (Vaga v : this.descartarVagas(evento, especialidades))
//                vagaDAO.deleteById(v.getId());
//
//            for (int i = 0; i < especialidades.size(); i++) {
//                Vaga v = evento.findVagaByEspecialidade(especialidades.get(i));
//                if (v != null) {
//                    v.setQtdVagas(quantidades.get(i));
//                } else {
//                    Vaga vaga = new Vaga(especDAO.getOne(especialidades.get(i)), quantidades.get(i), evento);
//                    vagaDAO.save(vaga);
//                    evento.add(vaga);
//                }
//            }
//        }
//        evento.setDono(userDAO.findByEmail(auth.getName()));
//        evento.setStatus(status);
//        eventoDAO.save(evento);
//        return new ModelAndView("redirect:/eventos/meuseventos");
//    }
//
//    @GetMapping("/candidatar/{id}")
//    public ModelAndView exibirCandidatar(@PathVariable("id") Long id) {
//        Evento evento = eventoDAO.findById(id).get();
//        List<Especialidade> especialidadesdisponiveis = new ArrayList<Especialidade>();
//        ModelAndView modelForm = new ModelAndView("evento/candidatarEvento");
//        modelForm.addObject("evento", evento);
//        for (Vaga vaga : this.getVagasDisponiveis(evento)) {
//            especialidadesdisponiveis.add(vaga.getEspecialidade());
//        }
//        modelForm.addObject("especialidades", especialidadesdisponiveis);
//        return modelForm;
//    }
//
//    @PostMapping("/candidatar/{id}")
//    public ModelAndView candidatar(@PathVariable("id") Long id,
//                                   @RequestParam("especialidades") List<Long> especialidades,
//                                   Principal user,
//                                   RedirectAttributes att) {
//        Evento evento = eventoDAO.getOne(id);
//        CandidatoVaga candidatoVaga;
//        Boolean vagaValida = false;
//
//        for (Long especialidade : especialidades) {
//            vagaValida = false;
//            for (Vaga vaga : this.getVagasDisponiveis(evento)) {
//                if (vaga.getEspecialidade().getId() == especialidade) {
//                    candidatoVaga = new CandidatoVaga();
//                    candidatoVaga.setVaga(vaga);
//                    candidatoVaga.setStatus(Status.AGUARDANDO_APROVACAO);
//                    candidatoVaga.setCandidato(userDAO.findByEmail(user.getName()));
//                    candidatoVagaDAO.save(candidatoVaga);
//                    vagaValida = true;
//                }
//            }
//            if (!vagaValida) {
//                att.addFlashAttribute("mensagemerro", String.format("Não foi possivel se candidatar a vaga %s: VAGA JÁ ESGOTADA!",
//                        especDAO.findById(especialidade).get().getNome()));
//            }
//
//        }
//        att.addFlashAttribute("mensagem", "Se tornou candidato com sucesso!");
//
//        return new ModelAndView("redirect:/");
//    }
//
//    private List<Vaga> getVagasDisponiveis(Evento evento) {
//        List<Vaga> vagas = evento.getVagas();
//        List<Vaga> vagasdisponiveis = new ArrayList<Vaga>();
//
//        for (Vaga vaga : vagas) {
//            int qntAprovadoPendente = 0;
//            for (CandidatoVaga candidatoVaga : vaga.getCandidatoVaga()) {
//                if (candidatoVaga.getStatus().name().equals("APROVADO") || candidatoVaga.getStatus().name().equals("AGUARDANDO_APROVACAO")) {
//                    qntAprovadoPendente++;
//                }
//            }
//            if (vaga.getQtdVagas() > qntAprovadoPendente) {
//                vagasdisponiveis.add(vaga);
//            }
//        }
//        return vagasdisponiveis;
//    }
//

//
//
//    private ArrayList<Vaga> descartarVagas(Evento evento, List<Long> especialidades) {
//        ArrayList<Vaga> vagas = new ArrayList<>(evento.getVagas());
//        for (Long i : especialidades)
//            for (int j = 0; j < vagas.size(); j++) {
//                if (vagas.get(j).getEspecialidade().getId() == i) {
//                    vagas.remove(vagas.get(j));
//                    j--;
//                }
//            }
//        evento.getVagas().removeAll(vagas);
//        return vagas;
//    }
//
//    private Map<Long, Map<Long, String>> converterListasEventos () {
//        Map<Long, Map<Long, String>> eventosVagas = new HashMap<>();
//        for (Evento e : eventoDAO.findAll()) {
//        		eventosVagas.put(e.getId(), converterLista(e.getVagas(), true));
//        }
//        return eventosVagas;
//    }
//
//    private Map<Long, String> converterLista(List<Vaga> vagas, boolean aprovado) {
//
//        Map<Long, String> vagasCandidatos = new HashMap<>();
//    	if(vagas != null) {
//	        for (Vaga v : vagas) {
//	            for (CandidatoVaga c : v.getCandidatoVaga()) {
//	                if (aprovado) {
//	                    if (c.getStatus().name().equals("APROVADO")) {
//	                        vagasCandidatos.put(c.getId(), c.getCandidato().getNome() + " - " + v.getEspecialidade().getNome());
//	                    }
//	                } else {
//	                    vagasCandidatos.put(c.getId(), c.getCandidato().getNome() + " - " +
//	                            (c.getNotaDesempenho() == -1 ? "Aguardando Avaliação": c.getNotaDesempenho()));
//	                }
//	            }
//	        }
//    	}
//        return vagasCandidatos;
//    }
	
//	private Map<Long,String> fazerDicionarioAvaliacoeUsuario(List<AvaliacaoEvento> avaliacoesDoUsuario) {
//		Map<Long,String> mapAvaliacoes = new HashMap<Long, String>();
//		for (AvaliacaoEvento avaliacaoEvento : avaliacoesDoUsuario) {
//			mapAvaliacoes.put(avaliacaoEvento.getEvento().getId(), Integer.toString(avaliacaoEvento.getNotaAvaliacaoEvento()));
//		}
//		return mapAvaliacoes;
//	}
//	
	

    @Transactional
	public CandidatoVaga aprovarCandidato (Long id) throws Exception {
		CandidatoVaga candidatura = candidatoVagaDAO.read(id);
		Boolean validacao = validarCandidatura(candidatura.getVaga(), candidatura.getCandidato());
		Evento evento = candidatura.getVaga().getEvento();
		User usuarioLogado = (User) LoginFacade.getParticipanteLogado();
		if (usuarioLogado.getId() != evento.getDono().getId()) {
			throw new Exception("você não pode alterar este evento");	
        }
        
		if(validacao) {
			candidatura.setStatus(Status.APROVADO);
			candidatoVagaDAO.update(candidatura);
			
			if(verificaVagasCompletas(candidatura.getVaga().getEvento())) {
				fechaEventoPorVagasPreenchidas(candidatura.getVaga().getEvento());
			}
			return candidatura;
		}else {
			candidatura.setStatus(Status.NAO_APROVADO);
			candidatoVagaDAO.update(candidatura);
			return null;
		}	
	}
    
	@Transactional
	public CandidatoVaga reprovarCandidato (Long id) throws Exception {
		CandidatoVaga candidatura = candidatoVagaDAO.read(id);
		Evento evento = candidatura.getVaga().getEvento();
		User usuarioLogado = (User) LoginFacade.getParticipanteLogado();
		if (usuarioLogado.getId() != evento.getDono().getId()) {
            throw new Exception("você não pode alterar este evento");
        }
		candidatura.setStatus(Status.NAO_APROVADO);
		candidatoVagaDAO.update(candidatura);
//		att.addFlashAttribute("mensagemsecundaria", "Candidato reprovado com sucesso!");	
		return candidatura;
	}
	
	private boolean validarCandidatura (Vaga vaga, User candidato) {
		
		int qntAprovados = 0;
		
		for (CandidatoVaga candidatura : vaga.getCandidatoVaga()) {
			if(candidatura.getStatus() == Status.APROVADO) {
				qntAprovados++;
			}
		}
		
		if (qntAprovados < vaga.getQtdVagas()) {
			List<Vaga> vagas = vaga.getEvento().getVagas();
			
			for (Vaga vagaanalise : vagas) {
				List<CandidatoVaga> candidaturas = vagaanalise.getCandidatoVaga();
				for (CandidatoVaga candidatura : candidaturas) {
					if(candidatura.getCandidato().equals(candidato) && candidatura.getStatus() == Status.APROVADO) {
						return false;
					}
				}
			}
			return true;	
		}
		return false;
	}
	
	private boolean verificaVagasCompletas(Evento e) {
		List<Vaga> vagas = e.getVagas();
		int qntpreenchida;
		boolean teste = false;
		for (Vaga vaga : vagas) {
			qntpreenchida = 0; 
			for (CandidatoVaga candidatura : vaga.getCandidatoVaga()) {
				if(candidatura.getStatus() == Status.APROVADO) {
					qntpreenchida++;
				}
			}
			if(qntpreenchida == vaga.getQtdVagas()) {
				teste = true;
			}else {
				return false;
			}
		}
		if(teste) {
			return true;
		}
		return false;
	}
	
	private void fechaEventoPorVagasPreenchidas(Evento e) {
		e.setStatus(StatusEvento.FECHADO);
		eventoDAO.update(e);
		List<Vaga> vagas = e.getVagas();
		for (Vaga vaga : vagas) {
			for (CandidatoVaga candidatura : vaga.getCandidatoVaga()) {
				if(candidatura.getStatus()==Status.AGUARDANDO_APROVACAO) {
					candidatura.setStatus(Status.NAO_APROVADO);
					candidatoVagaDAO.update(candidatura);
				}
			}
			
		}
	}
}
