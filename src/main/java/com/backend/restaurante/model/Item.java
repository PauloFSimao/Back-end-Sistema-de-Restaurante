package com.backend.restaurante.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@OneToOne
	private Produto produto;
	
	@NotNull
	private int quantidade;
	
	@OneToOne
	private Pedido pedido;
}
