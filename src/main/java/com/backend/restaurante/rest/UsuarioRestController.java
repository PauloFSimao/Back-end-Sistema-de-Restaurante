package com.backend.restaurante.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.restaurante.model.Erro;
import com.backend.restaurante.model.Sucesso;
import com.backend.restaurante.model.Usuario;
import com.backend.restaurante.repository.UsuarioRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioRepository repository;
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody Usuario usuario){
		if(usuario != null) {
			try {
				
				// criptografando os dados
				String login = encoder.encode(usuario.getLogin());
				String senha = encoder.encode(usuario.getSenha());
				
				// setando os valores criptografados
				usuario.setLogin(login);
				usuario.setSenha(senha);
				
				repository.save(usuario);
				Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
				return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			} catch (Exception e) {
				Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível cadastrar o usuário", null);
				return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possivel cadastrar o usuário", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Usuario> listar(){
		return repository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterar(@PathVariable("id") Long id, @RequestBody Usuario usuario){
		Optional<Usuario> user = repository.findById(id);
		
		if(user.isEmpty()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "ID inválido", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			// criptografando os dados
			String login = encoder.encode(usuario.getLogin());
			String senha = encoder.encode(usuario.getSenha());
			
			// setando os valores criptografados
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setId(id);
			repository.save(usuario);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deletar(@PathVariable("id") Long id){
		try {
			repository.deleteById(id);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		} catch (Exception e) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "ID inválido", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public Boolean validaSenha(String usuarioCrip, String senha) {
		Boolean valido = encoder.matches(usuarioCrip, senha);
		return valido;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> logar(@RequestBody Usuario usuario){
		Optional<Usuario> user = repository.retornaLogin(usuario.getLogin());
		if(user.isEmpty()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível logar", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			Boolean senhaValida = validaSenha(user.get().getSenha(), usuario.getSenha());
			
			if(senhaValida == true && !user.isEmpty()) {
				Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
				return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			}
		}
	}
}
