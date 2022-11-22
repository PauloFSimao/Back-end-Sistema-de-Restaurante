package com.backend.restaurante.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@OneToOne
	private Cliente cliente;
	
	@NotNull
	@OneToMany
	private List<Mesa> mesa;
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Calendar dataHora;
}
