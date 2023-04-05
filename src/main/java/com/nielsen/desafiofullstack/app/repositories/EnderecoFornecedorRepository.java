package com.nielsen.desafiofullstack.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nielsen.desafiofullstack.app.domain.entities.EnderecoFornecedor;
import com.nielsen.desafiofullstack.app.domain.entities.Fornecedor;

public interface EnderecoFornecedorRepository extends JpaRepository<EnderecoFornecedor, Long> {

	public List<EnderecoFornecedor> findByFornecedor(Fornecedor fornecedor);
}
