package com.backend.restaurante.rest;

import java.util.List;
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
import com.backend.restaurante.model.Produto;
import com.backend.restaurante.model.Sucesso;
import com.backend.restaurante.repository.ProdutoRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/produto")
public class ProdutoRestController {
	
	@Autowired
	private ProdutoRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody Produto produto){
		if(produto != null) {
			try {
				repository.save(produto);
				Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
				return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			} catch (Exception e) {
				Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível salvar o produto", null);
				return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível salvar o produto", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Produto> listar(){
		return repository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterar(@PathVariable("id") Long id, @RequestBody Produto produto){
		Optional<Produto> prod = repository.findById(id);
		
		if(prod.isEmpty()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "ID inválido", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			produto.setId(prod.get().getId());
			repository.save(produto);
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
	
	@RequestMapping(value = "/{nome}", method = RequestMethod.GET)
	public List<Produto> buscarPorNome(@PathVariable("nome") String nome){
		return repository.buscaNome(nome);
	}
}
