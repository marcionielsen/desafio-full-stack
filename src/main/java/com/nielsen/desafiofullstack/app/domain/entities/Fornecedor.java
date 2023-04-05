package com.nielsen.desafiofullstack.app.domain.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "FORNECEDOR")
public class Fornecedor implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@Column(name = "ID_FORNECEDOR",updatable=false,nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(name = "CNPJ_CPF", nullable=false)
	private String cnpjCpf;
	
	@Column(name = "TIPO_PESSOA", nullable=false)
	private Integer tipoPessoa;
	
	@Column(name = "NUMERO_RG", nullable=false)
	private String numeroRg;
	
	@Column(name = "DATA_NASCIMENTO", nullable=false)
	private LocalDateTime dataNascimento;
	
	@Column(name = "NOME", nullable=false)
	private String nome;
	
	@Column(name = "EMAIL", nullable=false)
	private String email;
	
	@OneToMany(mappedBy = "fornecedor",fetch = FetchType.LAZY)
	private List<EnderecoFornecedor> enderecos = new ArrayList<>();

	public Fornecedor() {
	}

	public Fornecedor(String cnpjCpf, Integer tipoPessoa, String numeroRg, LocalDateTime dataNascimento,
			String nome, String email) {
		super();
		this.cnpjCpf = cnpjCpf;
		this.tipoPessoa = tipoPessoa;
		this.numeroRg = numeroRg;
		this.dataNascimento = dataNascimento;
		this.nome = nome;
		this.email = email;
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

	public List<EnderecoFornecedor> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoFornecedor> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fornecedor other = (Fornecedor) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Fornecedor [id=" + id + ", cnpjCpf=" + cnpjCpf + ", tipoPessoa=" + tipoPessoa + ", numeroRg=" + numeroRg
				+ ", dataNascimento=" + dataNascimento + ", nome=" + nome + ", email=" + email + ", enderecos="
				+ enderecos + "]";
	}

}
