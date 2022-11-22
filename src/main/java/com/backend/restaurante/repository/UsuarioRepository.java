package com.backend.restaurante.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.restaurante.model.Usuario;

import feign.Param;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{

	@Query("SELECT u FROM Usuario u WHERE u.login = :login")
	Optional<Usuario> retornaLogin(@Param("login") String login);

}
