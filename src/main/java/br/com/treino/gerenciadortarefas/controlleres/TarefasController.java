package br.com.treino.gerenciadortarefas.controlleres;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import br.com.treino.gerenciadortarefas.modelos.Tarefa;
import br.com.treino.gerenciadortarefas.modelos.User;
import br.com.treino.gerenciadortarefas.repositorios.RepositorioTarefa;
import br.com.treino.gerenciadortarefas.servicos.ServicoUser;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

	@Autowired
	private RepositorioTarefa repositorioTarefa;
	
	@Autowired
	private ServicoUser servicoUser;

	@GetMapping("/listar")
	public ModelAndView listar(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/tarefas/listar");
		String emailUser = request.getUserPrincipal().getName();
		//mv.addObject("tarefa", repositorioTarefa.findAll(new Sort(Sort.Direction.ASC,"titulo")));
		mv.addObject("tarefa", repositorioTarefa.carregarTarefasPorUser(emailUser));
		return mv;
	}
	
	@GetMapping("/inserir")
	public ModelAndView inserir() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tarefas/inserir");
		mv.addObject("tarefa", new Tarefa());
		return mv;
	}
	
	@PostMapping("/inserir")
	public ModelAndView inserir(@Valid Tarefa tarefa, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		if(tarefa.getDataExpiracao() == null) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração é obrigatório");
			
		}
		
		if(tarefa.getDataExpiracao().before(new Date())) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração não pode ser anterior à data atual.");
		}
		if(result.hasErrors()) {
			mv.setViewName("tarefas/inserir");
			mv.addObject("tarefa",tarefa);
		}else {
			String emailUser = request.getUserPrincipal().getName();
			User usuarioLogado = servicoUser.buscarPorEmail(emailUser);
			tarefa.setUser(usuarioLogado);
			mv.setViewName("redirect:/tarefas/listar");
			repositorioTarefa.save(tarefa);
		}
		return mv;
	}

	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tarefas/alterar");
		Tarefa tarefa = repositorioTarefa.getOne(id);
		mv.addObject("tarefa", tarefa);
		return mv;
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid Tarefa tarefa, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		
		if(tarefa.getDataExpiracao() == null) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração é obrigatório");
			
		}
		if(tarefa.getDataExpiracao().before(new Date())) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração não pode ser anterior à data atual.");
		}
		if(result.hasErrors()) {
			mv.setViewName("tarefas/alterar");
			mv.addObject("tarefa",tarefa);
		}else {
			mv.setViewName("redirect:/tarefas/listar");
			repositorioTarefa.save(tarefa);
		}
		return mv;
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id) {
		repositorioTarefa.deleteById(id);
		return "redirect:/tarefas/listar";
	}
	
	@GetMapping("/concluir/{id}")
	public String concluir(@PathVariable("id") Long id) {
		Tarefa tarefa = repositorioTarefa.getOne(id);
		tarefa.setConcluida(true);
		repositorioTarefa.save(tarefa);
		return "redirect:/tarefas/listar";
	}
	
}
