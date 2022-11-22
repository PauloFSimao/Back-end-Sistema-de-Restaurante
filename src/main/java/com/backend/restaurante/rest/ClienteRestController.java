package com.backend.restaurante.rest;

import java.time.LocalDate;
import java.util.Calendar;
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

import com.backend.restaurante.model.Cliente;
import com.backend.restaurante.model.Erro;
import com.backend.restaurante.model.Sucesso;
import com.backend.restaurante.repository.ClienteRepository;


@CrossOrigin
@RestController
@RequestMapping("/api/cliente")
public class ClienteRestController {
	
	@Autowired
	private ClienteRepository repository;
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
		
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody Cliente cliente){
		System.out.println(cliente);
		if(cliente != null) {
			try {
				Calendar data = Calendar.getInstance();
				cliente.setDataCadastro(data);
				
				// criptografando os dados pra salvar no banco de dados
				String cpf = encoder.encode(cliente.getCpf());
				String endereco = encoder.encode(cliente.getEndereco());
				String telefone = encoder.encode(cliente.getTelefone());
				
				// setando os valores criptografados no cliente
				cliente.setCpf(cpf);
				cliente.setEndereco(endereco);
				cliente.setTelefone(telefone);
				
				
				repository.save(cliente);
				Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
				return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível salvar o cliente", null);
				return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível salvar a categoria", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Cliente> listar(){
		return repository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterar(@PathVariable("id") Long id, @RequestBody Cliente cliente){
		
		Optional<Cliente> cli = repository.findById(id);
		
		if(cli.isEmpty()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "ID inválido", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			try {
				// criptografando os dados pra salvar no banco de dados
				String cpf = encoder.encode(cliente.getCpf());
				String endereco = encoder.encode(cliente.getEndereco());
				String telefone = encoder.encode(cliente.getTelefone());
				
				// setando os valores criptografados no cliente
				cliente.setCpf(cpf);
				cliente.setEndereco(endereco);
				cliente.setTelefone(telefone);
				cliente.setId(cli.get().getId());
				repository.save(cliente);
				Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
				return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			} catch (Exception e) {
				Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "ID inválido", null);
				return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
			}
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
}
