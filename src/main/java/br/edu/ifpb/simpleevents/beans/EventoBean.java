package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.entity.Especialidade;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.Vaga;
import br.edu.ifpb.simpleevents.facade.EspecialidadeController;
import br.edu.ifpb.simpleevents.facade.EventoController;

@Named(value = "eventos")
@ViewScoped
public class EventoBean extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EventoController evtcontrol;

	@Inject
	private EspecialidadeController espccontrol;

	private List<Especialidade> especialidades;
	private List<EscolhaVagasEvento> escolhaVagas;

	private Evento evento;
	private String dataevento;

	private Long idSelecionado;

	@PostConstruct
	private void init() {
		Evento evento = (Evento) this.getFlash("evento");
		if (evento != null) {
			this.evento = evento;
			this.especialidades = this.espccontrol.getEspecialidades();
			this.escolhaVagas = new ArrayList<EventoBean.EscolhaVagasEvento>();
			for (Especialidade especialidade : this.especialidades) {
				this.escolhaVagas.add(new EscolhaVagasEvento(especialidade, false, 0));
			}
		} else {
			this.evento = new Evento();
		}
	}

	public String form() {
		return "/WEB-INF/facelets/evento/form.xhtml";
	}

	public String detail(){
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		System.out.println(params);
		Long id = Long.parseLong(params.get("id"));
		Evento evento = evtcontrol.findById(id);
		this.evento = evento;
		this.setFlash("evento", evento);
		return "/WEB-INF/facelets/evento/detail.xhtml";
	}

	public String salvar() {
		evento.setData(converter(dataevento));
		this.evento = evtcontrol.save(evento);
		this.setFlash("evento", evento);
		return "/eventos/addvagas.xhtml";
	}

	public String salvarVagas() {
		for (EscolhaVagasEvento escolhaVagasEvento : escolhaVagas) {
			if (escolhaVagasEvento.temVaga) {
				evtcontrol.criarVaga(new Vaga(escolhaVagasEvento.especialidade, escolhaVagasEvento.quantidade, evento));
			}
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","Evento e vagas adicionados com sucesso!"));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return "/eventos/listageral.xhtml?faces-redirect=true";
	}

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
//
//    @GetMapping("/meuseventos")
//    public ModelAndView listmyevents(Authentication auth) {
//        ModelAndView modelList = new ModelAndView("evento/list");
//        User usuarioLogado = userDAO.findByEmail(auth.getName());
//        modelList.addObject("userLog", usuarioLogado);
//        modelList.addObject("eventos", eventoDAO.findByDono(usuarioLogado));
//        modelList.addObject("eventosVagas", converterListasEventos());
//        return modelList;
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
//    public ModelAndView detail(@PathVariable("id") Long id,
//                               Authentication auth) {
//        Evento evento = eventoDAO.findById(id).get();
//        User usuarioLogado = userDAO.findByEmail(auth.getName());
//        ModelAndView modelDetail = new ModelAndView("evento/detail");
//        modelDetail.addObject("eventoVagas", converterLista(evento.getVagas(), false));
//        modelDetail.addObject("userLog", usuarioLogado);
//        modelDetail.addObject("evento", evento);
//        return modelDetail;
//    }
//
//    @RequestMapping("/delete/{id}")
//    public ModelAndView delete(@PathVariable("id") Long id,
//                               Authentication auth,
//                               RedirectAttributes att) {
//        Evento evento = eventoDAO.findById(id).get();
//        User usuarioLogado = userDAO.findByEmail(auth.getName());
//        if (usuarioLogado.getId() != evento.getDono().getId()) {
//        	att.addAttribute("mensagemerro", "você não pode deletar este evento");
//            return new ModelAndView("redirect:/eventos");
//        }
//        if (evento != null) {
//            att.addFlashAttribute("deletado", "Evento deletado com sucesso!");
//            eventoDAO.deleteById(id);
//        }
//        return new ModelAndView("redirect:/eventos");
//    }
//
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
//    @GetMapping("/{id}/candidatos")
//    public ModelAndView analisarCandidatos(@PathVariable("id") Long id) {
//        ModelAndView modelForm = new ModelAndView("evento/analisecandidatos");
//        Evento evento = eventoDAO.findById(id).get();
//        List<CandidatoVaga> candidaturas = new ArrayList<CandidatoVaga>();
//        for (Vaga vaga : evento.getVagas()) {
//            for (CandidatoVaga candidatura : vaga.getCandidatoVaga()) {
//                candidaturas.add(candidatura);
//            }
//        }
//        modelForm.addObject("evento", evento);
//        modelForm.addObject("candidaturas", candidaturas);
//        return modelForm;
//    }
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

	private LocalDateTime converter(String data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(data, formatter);
		return dateTime;

	}

	// get and setters

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public List<EscolhaVagasEvento> getEscolhaVagas() {
		return escolhaVagas;
	}

	public void setEscolhaVagas(List<EscolhaVagasEvento> escolhaVagas) {
		this.escolhaVagas = escolhaVagas;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public String getDataevento() {
		return dataevento;
	}

	public void setDataevento(String dataevento) {
		this.dataevento = dataevento;
	}

	public Long getIdSelecionado() {
		return idSelecionado;
	}

	public void setIdSelecionado(Long idSelecionado) {
		this.idSelecionado = idSelecionado;
	}

	//	inner class para escolha de quantidade de vagas por especialidade no switch

	public class EscolhaVagasEvento {
		private Especialidade especialidade;
		private boolean temVaga;
		private int quantidade;

		public EscolhaVagasEvento(Especialidade especialidade, boolean temVaga, int quantidade) {
			super();
			this.especialidade = especialidade;
			this.temVaga = temVaga;
			this.quantidade = quantidade;
		}

		public Especialidade getEspecialidade() {
			return especialidade;
		}

		public void setEspecialidade(Especialidade especialidade) {
			this.especialidade = especialidade;
		}

		public boolean isTemVaga() {
			return temVaga;
		}

		public void setTemVaga(boolean temVaga) {
			this.temVaga = temVaga;
		}

		public int getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(int quantidade) {
			this.quantidade = quantidade;
		}

	}

}
