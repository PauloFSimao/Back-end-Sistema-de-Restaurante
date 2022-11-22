package com.backend.restaurante.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.restaurante.model.Reserva;

@Repository
public interface ReservaRepository extends PagingAndSortingRepository<Reserva, Long>{

}
