package br.com.treino.gerenciadortarefas.controlleres;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.treino.gerenciadortarefas.modelos.User;
import br.com.treino.gerenciadortarefas.servicos.ServicoUser;

@Controller
public class ContaControle {
	
	@Autowired
	private ServicoUser servicoUser;

	@GetMapping("/login")
	public String login() {
		return "conta/login";
	}
	
	@GetMapping("/registration")
	public ModelAndView registrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("conta/registrar");
		mv.addObject("usuario", new User());
		return mv;
	}
	
	@PostMapping("/registration")
	public ModelAndView registrar(@Valid User user, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		User usuario = servicoUser.buscarPorEmail(user.getEmail());
		if(usuario!=null) {
			result.rejectValue("email", "", "Usuário já cadastrado");
		}
		if(result.hasErrors()) {
			mv.setViewName("conta/registrar");
			mv.addObject("usuario", user);
		}else {
			servicoUser.inserir(user);
			mv.setViewName("redirect:/login");
		}
		return mv;
	}
	
}
