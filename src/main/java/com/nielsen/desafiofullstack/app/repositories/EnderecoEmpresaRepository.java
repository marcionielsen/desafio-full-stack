package com.nielsen.desafiofullstack.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nielsen.desafiofullstack.app.domain.entities.Empresa;
import com.nielsen.desafiofullstack.app.domain.entities.EnderecoEmpresa;

public interface EnderecoEmpresaRepository extends JpaRepository<EnderecoEmpresa, Long> {

	public List<EnderecoEmpresa> findByEmpresa(Empresa empresa);
}
