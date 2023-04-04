package com.nielsen.desafiofullstack.app.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nielsen.desafiofullstack.app.dto.EmpresaDTO;
import com.nielsen.desafiofullstack.app.dto.EnderecoEmpresaDTO;
import com.nielsen.desafiofullstack.app.entities.Empresa;
import com.nielsen.desafiofullstack.app.entities.EnderecoEmpresa;
import com.nielsen.desafiofullstack.app.repositories.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	
	public long saveCompany(String jsonObj) throws StreamReadException, DatabindException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		EmpresaDTO empresaDto = objectMapper.readValue(jsonObj.getBytes(), EmpresaDTO.class);
		
		List<EnderecoEmpresaDTO> enderecosDTO = new ArrayList<>(empresaDto.getEnderecos());
		Set<EnderecoEmpresa> enderecos = new HashSet<>();
		
		System.out.println("######[ EmpresaDTO ]######");
		System.out.println("CNPJ: " + empresaDto.getCnpj());
		System.out.println("NOME: " + empresaDto.getNomeFantasia());
		
		for (EnderecoEmpresaDTO enderecoEmpresaDTO : enderecosDTO) {
			System.out.println("--------------------------");
			System.out.println("CEP        : " + enderecoEmpresaDTO.getCep());
			System.out.println("UF         : " + enderecoEmpresaDTO.getUf());
			System.out.println("CIDADE     : " + enderecoEmpresaDTO.getCidade());
			System.out.println("BAIRRO     : " + enderecoEmpresaDTO.getBairro());
			System.out.println("LOGRADOURO : " + enderecoEmpresaDTO.getLogradouro());
			System.out.println("NUMERO     : " + enderecoEmpresaDTO.getNumero());
			System.out.println("COMPLEMENTO: " + enderecoEmpresaDTO.getComplemento());
			
			EnderecoEmpresa endereco = new EnderecoEmpresa();
			endereco.setBairro(enderecoEmpresaDTO.getBairro());
			endereco.setCep(enderecoEmpresaDTO.getCep());
			endereco.setCidade(enderecoEmpresaDTO.getCidade());
			endereco.setComplemento(enderecoEmpresaDTO.getComplemento());
			endereco.setLogradouro(enderecoEmpresaDTO.getLogradouro());
			endereco.setNumero(enderecoEmpresaDTO.getNumero());
			endereco.setUf(enderecoEmpresaDTO.getUf());
			
			enderecos.add(endereco);
		}
		
		Empresa empresa = new Empresa();
		
		empresa.setCnpj(empresaDto.getCnpj());
		empresa.setNomeFantasia(empresaDto.getNomeFantasia());
		empresa.setEnderecos(enderecos);
		
		Empresa empresaNova = empresaRepository.save(empresa);
		
		return empresaNova.getId().longValue();
	}
	
}
