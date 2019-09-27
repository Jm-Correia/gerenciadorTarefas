package br.com.treino.gerenciadortarefas.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treino.gerenciadortarefas.modelos.User;

public interface RepositorioUsuario extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
}
