package com.nielsen.desafiofullstack.app.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class FornecedorDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String cnpjCpf;
	private Integer tipoPessoa;
	private String numeroRg;
	
    @JsonInclude(Include.NON_NULL)
	private LocalDateTime dataNascimento;
    
	private String nome;
	private String email;
	private List<EnderecoFornecedorDTO> enderecos = new ArrayList<>(); 
		
	public FornecedorDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public Integer getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(Integer tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getNumeroRg() {
		return numeroRg;
	}

	public void setNumeroRg(String numeroRg) {
		this.numeroRg = numeroRg;
	}

	public LocalDateTime getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDateTime dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<EnderecoFornecedorDTO> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoFornecedorDTO> enderecos) {
		this.enderecos = enderecos;
	}

}
