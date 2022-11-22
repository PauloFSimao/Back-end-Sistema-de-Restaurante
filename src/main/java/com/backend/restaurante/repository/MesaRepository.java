package com.backend.restaurante.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.restaurante.model.Mesa;

@Repository
public interface MesaRepository extends PagingAndSortingRepository<Mesa, Long>{

}
