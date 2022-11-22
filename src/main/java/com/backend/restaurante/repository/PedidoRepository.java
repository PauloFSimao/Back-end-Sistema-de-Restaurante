package com.backend.restaurante.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.restaurante.model.Pedido;

@Repository
public interface PedidoRepository extends PagingAndSortingRepository<Pedido, Long>{

}
