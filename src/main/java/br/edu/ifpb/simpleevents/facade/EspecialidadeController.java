package br.edu.ifpb.simpleevents.facade;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifpb.simpleevents.dao.EspecialidadeDAO;
import br.edu.ifpb.simpleevents.entity.Especialidade;

public class EspecialidadeController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EspecialidadeDAO especDAO;
	
	
	public List<Especialidade> getEspecialidades () {
		return especDAO.read();
	}
	
	
//	@RequestMapping("/form")
//	public ModelAndView form(Especialidade especialidade) {
//		return new ModelAndView("especialidade/form");
//	}
//	
//	@RequestMapping(method=RequestMethod.POST, value="/save")
//	public ModelAndView save(@Valid Especialidade especialidade, 
//			BindingResult result){
//		if (result.hasErrors()) {
//            return new ModelAndView("/form");
//        }
//		especDAO.saveAndFlush(especialidade);
//        return new ModelAndView("redirect:/especialidades");
//	}
//
//
//	@RequestMapping(method=RequestMethod.GET)
//	public ModelAndView list() {
//		ModelAndView modelList = new ModelAndView("especialidade/list");
//		modelList.addObject("especialidades", especDAO.findAll());
//		return modelList;
//	}
//
//	@RequestMapping("/delete/{id}")
//	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes att) {
//		Optional<Especialidade> optionalEspecialidade = especDAO.findById(id);
//		if (optionalEspecialidade != null) {
//			att.addFlashAttribute("deletado", "Especialidade deletada com sucesso!");
//			especDAO.deleteById(id);
//		}
//		return new ModelAndView("redirect:/especialidades");
//	}
//	
//	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
//	public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes att) {
//		ModelAndView modelForm = new ModelAndView("especialidade/form");
//		modelForm.addObject("especialidade", especDAO.findById(id).get());
//		return modelForm;
//	}
//	
//	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
//	public ModelAndView update(@PathVariable("id") Long id, 
//			@Valid Especialidade especialidade,
//			BindingResult result) {
//	    if (result.hasErrors()) {
//	        especialidade.setId(id);
//	        return new ModelAndView("especialidade/form");
//	    }
//	         
//	    especDAO.save(especialidade);
//        return new ModelAndView("redirect:/especialidades");
//	}
	
}
