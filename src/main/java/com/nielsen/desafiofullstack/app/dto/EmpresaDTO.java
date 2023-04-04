package com.nielsen.desafiofullstack.app.dto;

import java.util.HashSet;
import java.util.Set;

public class EmpresaDTO {

	private Long id;
	private String cnpj;
	private String nomeFantasia;
	private Set<EnderecoEmpresaDTO> enderecos = new HashSet<>(); 
		
	public EmpresaDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public Set<EnderecoEmpresaDTO> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<EnderecoEmpresaDTO> enderecos) {
		this.enderecos = enderecos;
	}
	
	
}
