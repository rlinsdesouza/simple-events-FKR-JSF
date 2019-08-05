package br.edu.ifpb.simpleevents.facade;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import com.github.javafaker.Faker;

import br.edu.ifpb.simpleevents.dao.CandidatoVagaDAO;
import br.edu.ifpb.simpleevents.dao.EspecialidadeDAO;
import br.edu.ifpb.simpleevents.dao.EventoDAO;
import br.edu.ifpb.simpleevents.dao.ParticipanteDAO;
import br.edu.ifpb.simpleevents.dao.UserDAO;
import br.edu.ifpb.simpleevents.dao.VagaDAO;
import br.edu.ifpb.simpleevents.entity.CandidatoVaga;
import br.edu.ifpb.simpleevents.entity.Especialidade;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.Status;
import br.edu.ifpb.simpleevents.entity.StatusEvento;
import br.edu.ifpb.simpleevents.entity.User;
import br.edu.ifpb.simpleevents.entity.Vaga;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;
import br.edu.ifpb.simpleevents.security.PasswordUtil;


public class FakerFacade {
//	https://github.com/DiUS/java-faker
	Faker faker = new Faker();
	private PasswordUtil password = new PasswordUtil();
	
	@Inject
	private EspecialidadeDAO especialidadedao;
	@Inject
	private UserDAO userdao;
	@Inject
	public EventoDAO eventoDAO;
	@Inject
	public VagaDAO vagaDAO;
	@Inject
	public CandidatoVagaDAO candidaturaDAO;
	
	@Inject
	public ParticipanteDAO participanteDAO;


	
	public String createDataFaker() throws NoSuchAlgorithmException, InvalidKeySpecException {
		createDataEspecialidade();
		createDataParticipantes();
		createDataEvents();
		createDataVagas();
		createDataCandidatoVaga();
		return null;
	}
	
	
	public void createDataEspecialidade () {
		Especialidade especialidade;
		for (int i = 0; i < 30; i++) {
			especialidade = new Especialidade();
			especialidade.setNome(faker.company().profession());
			especialidade.setDescricao(faker.lorem().characters(100));
			especialidadedao.create(especialidade);
		}
		
	}
	
	public void createDataParticipantes () throws NoSuchAlgorithmException, InvalidKeySpecException {
		User user;
		user = new User();
		user.setNome("admin");
		user.setEmail("admin@test");
		user.setAdmin(true);
		user.setSenha(password.encryptMD5("admin"));
		userdao.create(user);
		for (int i = 0; i < 100; i++) {
			user = new User();
			user.setNome(faker.name().fullName());
			user.setEmail(user.getNome()+"@teste");
			user.setTelefone(faker.phoneNumber().cellPhone());
			user.setDatanascimento(faker.date().birthday(18, 60));
			user.setSenha(password.encryptMD5(user.getNome().toLowerCase()));
			userdao.create(user);
		}
		
	}
	
	public void createDataEvents () {
		List<ParticipanteComposite> participantes = participanteDAO.read();
		Evento evento;
		for (int i = 0; i < 100; i++) {
			Random rand = new Random();
			evento = new Evento();
			evento.setDescricao(faker.lorem().sentence());
			evento.setData(LocalDateTime.now().plusDays(10));
			evento.setLocal(faker.address().fullAddress());
			evento.setDono(participantes.get(rand.nextInt(participantes.size())));
			evento.setStatus(StatusEvento.values()[rand.nextInt(StatusEvento.values().length)]);
			eventoDAO.create(evento);
		}
	}

	public void createDataVagas () {
		List<Especialidade> especialidades = especialidadedao.read();
		List<Evento> eventos = eventoDAO.read();
		Vaga vaga;
		for (int i = 0; i < 200; i++) {
			Random rand = new Random();
			vaga = new Vaga();
			vaga.setEvento(eventos.get(rand.nextInt(eventos.size())));
			vaga.setQtdVagas(rand.nextInt(10)+1);
//			List<Vaga> vagasExistentes = eventoDAO.findById(vaga.getEvento().getId()).get().getVagas();

//			Boolean testeEspecialidade = false;
			Especialidade novaEspecialidade;
			novaEspecialidade = especialidades.get(rand.nextInt(especialidades.size()));
//			do {
//				System.out.println(vagasExistentes);
//				novaEspecialidade = especialidades.get(rand.nextInt(especialidades.size()));
//				System.out.println(novaEspecialidade.getNome());
//				testeEspecialidade = false;
//				for (Vaga vagaExistente : vagasExistentes) {
//					System.out.println(vagaExistente.getEspecialidade().getNome());
//					if(vagaExistente.getEspecialidade().equals(novaEspecialidade)) {
//						testeEspecialidade = false;
//					}
//				}
//				System.out.println(testeEspecialidade);
//			} while (testeEspecialidade);
			vaga.setEspecialidade(novaEspecialidade);
			vagaDAO.create(vaga);
		}
	}

	public void createDataCandidatoVaga () {
		List<Vaga> vagas = vagaDAO.read();
		List<User> usuarios = userdao.read();
		CandidatoVaga candidatura;
		for (int i = 0; i < 300; i++) {
			Random rand = new Random();
			candidatura = new CandidatoVaga();
			candidatura.setVaga(vagas.get(rand.nextInt(vagas.size())));
			candidatura.setStatus(Status.AGUARDANDO_APROVACAO);
			List<CandidatoVaga> candidaturasExistentes = vagaDAO.read(candidatura.getVaga().getId()).getCandidatoVaga();

			Boolean testeCandidato = false;
			User novoCandidato;
			do {
				novoCandidato = usuarios.get(rand.nextInt(usuarios.size()));
				testeCandidato = false;
				for (CandidatoVaga candidaturaExistente : candidaturasExistentes) {
					if(candidaturaExistente.getCandidato().equals(novoCandidato)) {
						testeCandidato = true;
					}
				}
			} while (testeCandidato);
			candidatura.setCandidato(novoCandidato);
			candidaturaDAO.create(candidatura);
		}
	}

}
