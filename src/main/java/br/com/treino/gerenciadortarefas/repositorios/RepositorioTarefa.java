package br.com.treino.gerenciadortarefas.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.treino.gerenciadortarefas.modelos.Tarefa;

public interface RepositorioTarefa extends JpaRepository<Tarefa, Long> {

	@Query("SELECT t FROM Tarefa t WHERE t.user.email = :emailUser")
	List<Tarefa> carregarTarefasPorUser(@Param("emailUser") String email);
	
}
