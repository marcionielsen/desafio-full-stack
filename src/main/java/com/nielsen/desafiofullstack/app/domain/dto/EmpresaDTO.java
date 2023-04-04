package com.nielsen.desafiofullstack.app.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class EmpresaDTO {

	private Long id;
	private String cnpj;
	private String nomeFantasia;
	private List<EnderecoEmpresaDTO> enderecos = new ArrayList<>(); 
		
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

	public List<EnderecoEmpresaDTO> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoEmpresaDTO> enderecos) {
		this.enderecos = enderecos;
	}
	
	
}
