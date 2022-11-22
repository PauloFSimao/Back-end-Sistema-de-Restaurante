package com.backend.restaurante.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import aj.org.objectweb.asm.Type;
import lombok.Data;

@Data
@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	@Column(unique = true)
	private String cpf;
	
	private String endereco;
	
	private String telefone;
	
	private Calendar dataNascimento;
	
	
	private Calendar dataCadastro;
}
