package com.nielsen.desafiofullstack.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nielsen.desafiofullstack.app.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
