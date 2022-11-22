package com.backend.restaurante.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	@OneToOne
	private Categoria categoria;
	
	@NotNull
	private double preco;
	
	private String descricao;
	
	@NotNull
	private int quantidade;
}
