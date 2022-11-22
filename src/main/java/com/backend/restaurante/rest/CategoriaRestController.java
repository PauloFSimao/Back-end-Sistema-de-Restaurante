package com.backend.restaurante.rest;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

import com.backend.restaurante.model.Categoria;
import com.backend.restaurante.model.Erro;
import com.backend.restaurante.model.Sucesso;
import com.backend.restaurante.repository.CategoriaRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/categoria")
public class CategoriaRestController {
	
	@Autowired
	private CategoriaRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody Categoria categoria){
		if(categoria != null) {
			repository.save(categoria);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível salvar a categoria", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "", method =  RequestMethod.GET)
	public Iterable<Categoria> buscaTodos(){
		return repository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterar(@RequestBody Categoria categoria, @PathVariable("id") Long id){
		Optional<Categoria> cat = repository.findById(id);
		
		if(cat.isEmpty()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "ID inválido", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			cat.get().setNome(categoria.getNome());
			repository.save(cat.get());
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
