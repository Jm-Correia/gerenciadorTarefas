package br.com.treino.gerenciadortarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.treino.gerenciadortarefas.modelos.User;
import br.com.treino.gerenciadortarefas.repositorios.RepositorioUsuario;

@SpringBootApplication
public class GerenciadorTarefasApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(GerenciadorTarefasApplication.class, args);
	}



}
