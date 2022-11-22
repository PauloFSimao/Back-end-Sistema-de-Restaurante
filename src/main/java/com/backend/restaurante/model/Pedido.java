package com.backend.restaurante.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@OneToOne
	private Cliente cliente;
	
	@NotNull
	private double preco;
	
	@NotNull
	@OneToMany(mappedBy = "produto")
	private List<Item> item;
	
}
