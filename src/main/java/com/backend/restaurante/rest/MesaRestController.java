package com.backend.restaurante.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.restaurante.model.Erro;
import com.backend.restaurante.model.Mesa;
import com.backend.restaurante.model.Sucesso;
import com.backend.restaurante.repository.MesaRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/mesa")
public class MesaRestController {
	
	@Autowired
	private MesaRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody Mesa mesa){
		if(mesa != null) {
			try {
				repository.save(mesa);
				Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
				return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível salvar a mesa", null);
				return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível salvar a mesa", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Mesa> listar(){
		return repository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterar(@PathVariable("id") Long id, @RequestBody Mesa mesa){
		Optional<Mesa> me = repository.findById(id);
		
		if(me.isEmpty()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível alterar a mesa", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			me.get().setLugares(mesa.getLugares());
			me.get().setNome(mesa.getNome());
			repository.save(me.get());
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
	
}
