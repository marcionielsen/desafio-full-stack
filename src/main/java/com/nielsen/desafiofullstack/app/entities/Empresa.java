package com.nielsen.desafiofullstack.app.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "EMPRESA")
public class Empresa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_EMPRESA",updatable=false,nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CNPJ", nullable=false)
	private String cnpj;
	
	@Column(name = "NOME_FANTASIA", nullable=false)
	private String nomeFantasia;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPRESA")
	private Set<EnderecoEmpresa> enderecos = new HashSet<>();

	public Empresa() {
	}

	public Empresa(String cnpj, String nomeFantasia) {
		super();
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
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

	public Set<EnderecoEmpresa> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<EnderecoEmpresa> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", cnpj=" + cnpj + ", nomeFantasia=" + nomeFantasia + ", enderecos=" + enderecos
				+ "]";
	}
	
}
