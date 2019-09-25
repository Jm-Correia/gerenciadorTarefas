package br.com.treino.gerenciadortarefas.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.treino.gerenciadortarefas.modelos.User;
import br.com.treino.gerenciadortarefas.repositorios.RepositorioUsuario;

@Service
public class ServicoUser  {

	@Autowired
	private RepositorioUsuario repositorioUser;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public User buscarPorEmail(String email) {
		// TODO Auto-generated method stub
		return repositorioUser.findByEmail(email);
	}
	
	public void inserir(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repositorioUser.save(user);
	}

}
