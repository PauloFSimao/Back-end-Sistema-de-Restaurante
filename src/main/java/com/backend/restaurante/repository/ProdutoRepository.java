package com.backend.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.restaurante.model.Produto;


@Repository
public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Long>{

	@Query("SELECT p FROM Produto p WHERE p.nome LIKE %:nome%")
	public List<Produto> buscaNome(@Param("nome") String nome);
	
}
