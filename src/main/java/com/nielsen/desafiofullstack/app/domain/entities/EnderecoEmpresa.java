package com.nielsen.desafiofullstack.app.domain.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ENDERECO_EMPRESA")
public class EnderecoEmpresa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_ENDERECO_EMPRESA",updatable=false,nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(name = "CEP")
	private String cep;
	
	@Column(name = "UF")
	private String uf;
	
	@Column(name = "CIDADE")
	private String cidade;
	
	@Column(name = "BAIRRO")
	private String bairro;
	
	@Column(name = "LOGRADOURO")
	private String logradouro;
	
	@Column(name = "NUMERO")
	private String numero;
	
	@Column(name = "COMPLEMENTO")
	private String complemento;

	@ManyToOne
	@JoinColumn(name = "ID_EMPRESA")
	private Empresa empresa;
	
	public EnderecoEmpresa() {
	}

	public EnderecoEmpresa(Long id, String cep, String uf, String cidade, String bairro, String logradouro,
			String numero, String complemento, Empresa empresa) {
		super();
		this.id = id;
		this.cep = cep;
		this.uf = uf;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.empresa = empresa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cep, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnderecoEmpresa other = (EnderecoEmpresa) obj;
		return Objects.equals(cep, other.cep) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "EnderecoEmpresa [id=" + id + ", cep=" + cep + ", uf=" + uf + ", cidade=" + cidade + ", bairro=" + bairro
				+ ", logradouro=" + logradouro + ", numero=" + numero + ", complemento=" + complemento + ", empresa=[id=" + empresa.getId().longValue() + 
				", cnpj=" + empresa.getCnpj() + ", nomeFantasia=" + empresa.getNomeFantasia() + " ] ]";
	}
	
}
