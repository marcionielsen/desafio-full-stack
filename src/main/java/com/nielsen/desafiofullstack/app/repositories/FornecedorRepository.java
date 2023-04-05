package com.nielsen.desafiofullstack.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nielsen.desafiofullstack.app.domain.entities.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

}
