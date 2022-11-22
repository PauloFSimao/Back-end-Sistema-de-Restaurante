package com.backend.restaurante.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.restaurante.model.Cliente;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long>{

}
