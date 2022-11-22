package com.backend.restaurante.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.backend.restaurante.model.Item;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long>{

}
