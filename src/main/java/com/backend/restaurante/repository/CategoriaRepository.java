package com.backend.restaurante.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.restaurante.model.Categoria;

@Repository
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long>{

}
