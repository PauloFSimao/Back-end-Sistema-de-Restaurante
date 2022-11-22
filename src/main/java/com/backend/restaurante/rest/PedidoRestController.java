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
import com.backend.restaurante.model.Pedido;
import com.backend.restaurante.model.Sucesso;
import com.backend.restaurante.repository.PedidoRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/pedido")
public class PedidoRestController {
	
	@Autowired
	private PedidoRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody Pedido pedido){
		if(pedido != null) {
			try {
				repository.save(pedido);
				Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
				return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			} catch (Exception e) {
				Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possivel realizar o pedido", null);
				return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível realizar o pedido", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Pedido> listar(){
		return repository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterar(@PathVariable("id") Long id, @RequestBody Pedido pedido){
		Optional<Pedido> ped = repository.findById(id);
		
		if(ped.isEmpty()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "ID inválido", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			pedido.setId(id);
			repository.save(pedido);
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
