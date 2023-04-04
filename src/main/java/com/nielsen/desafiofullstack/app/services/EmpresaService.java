package com.nielsen.desafiofullstack.app.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nielsen.desafiofullstack.app.domain.dto.EmpresaDTO;
import com.nielsen.desafiofullstack.app.domain.dto.EnderecoEmpresaDTO;
import com.nielsen.desafiofullstack.app.domain.entities.Empresa;
import com.nielsen.desafiofullstack.app.domain.entities.EnderecoEmpresa;
import com.nielsen.desafiofullstack.app.repositories.EmpresaRepository;
import com.nielsen.desafiofullstack.app.repositories.EnderecoEmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private EnderecoEmpresaRepository enderecoEmpresaRepository;
	
    @Autowired
    private ModelMapper modelMapper;
	
	
	public String listAll() throws JsonProcessingException {
	
		List<Empresa> listaEmpresas = empresaRepository.findAll();

		return this.getEmpresas(listaEmpresas);
	}
	
	public String listCompanyById(Long id) throws JsonProcessingException {
		
		List<Empresa> listaEmpresas = new ArrayList<>();
		
		Optional<Empresa> opEmpresa = empresaRepository.findById(id);
		
		Empresa empresa = opEmpresa.orElse(null);
		
		if (empresa != null) {
			
			listaEmpresas.add(empresa);
			
			return this.getEmpresas(listaEmpresas);
		} else {
			return "Company not found!";
		}
	}
	
	public String deleteCompany(Long id) {
		
		Optional<Empresa> opEmpresa = empresaRepository.findById(id);
		
		Empresa empresa = opEmpresa.orElse(null);
		
		if (empresa != null) {
			
			List<EnderecoEmpresa> listaEnderecos = enderecoEmpresaRepository.findByEmpresa(empresa);
			
			for (EnderecoEmpresa endereco  : listaEnderecos) {
				 enderecoEmpresaRepository.delete(endereco);
			}
			
			empresaRepository.delete(empresa);
			
			return "Company and addresses deleted successfully!";
		} else {
			return "Company not found!";
		}
	}

	public String updateCompany(Long id, String jsonObj) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		
		EmpresaDTO empresaDto = objectMapper.readValue(jsonObj.getBytes(), EmpresaDTO.class);
		
		Optional<Empresa> opEmpresa = empresaRepository.findById(id);
		
		Empresa empresa = opEmpresa.orElse(null);
		
		if (empresa != null) {

			List<EnderecoEmpresaDTO> listaEnderecosDto = empresaDto.getEnderecos();
			for (EnderecoEmpresaDTO enderecoEmpresaDTO : listaEnderecosDto) {
				
				EnderecoEmpresa endereco = enderecoEmpresaRepository
						.findById(enderecoEmpresaDTO.getId())
						.orElseThrow(() -> new Exception("Address not exist with id: " + enderecoEmpresaDTO.getId().longValue()));
				
				endereco.setBairro(enderecoEmpresaDTO.getBairro());
				endereco.setCep(enderecoEmpresaDTO.getCep());
				endereco.setCidade(enderecoEmpresaDTO.getCidade());
				endereco.setComplemento(enderecoEmpresaDTO.getComplemento());
				endereco.setLogradouro(enderecoEmpresaDTO.getLogradouro());
				endereco.setNumero(enderecoEmpresaDTO.getNumero());
				endereco.setUf(enderecoEmpresaDTO.getUf());
				
				enderecoEmpresaRepository.save(endereco);
			}
			
			empresa.setCnpj(empresaDto.getCnpj());
			empresa.setNomeFantasia(empresaDto.getNomeFantasia());
			
			empresaRepository.save(empresa);
			
			return "Company and addresses updated successfully!";
		} else {
			return "Company not found!";
		}
		
	}
	
	public long saveCompany(String jsonObj) throws StreamReadException, DatabindException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		EmpresaDTO empresaDto = objectMapper.readValue(jsonObj.getBytes(), EmpresaDTO.class);
		
		List<EnderecoEmpresaDTO> enderecosDTO = empresaDto.getEnderecos();
		
//		List<EnderecoEmpresa> enderecos = new ArrayList<>();
		
		System.out.println("######[ EmpresaDTO ]######");
		System.out.println("CNPJ: " + empresaDto.getCnpj());
		System.out.println("NOME: " + empresaDto.getNomeFantasia());

		Empresa empresa = new Empresa();
		
		empresa.setCnpj(empresaDto.getCnpj());
		empresa.setNomeFantasia(empresaDto.getNomeFantasia());

		Empresa empresaNova = empresaRepository.save(empresa);
		
		for (EnderecoEmpresaDTO enderecoEmpresaDTO : enderecosDTO) {
			System.out.println("--------------------------");
			System.out.println("CEP        : " + enderecoEmpresaDTO.getCep());
			System.out.println("UF         : " + enderecoEmpresaDTO.getUf());
			System.out.println("CIDADE     : " + enderecoEmpresaDTO.getCidade());
			System.out.println("BAIRRO     : " + enderecoEmpresaDTO.getBairro());
			System.out.println("LOGRADOURO : " + enderecoEmpresaDTO.getLogradouro());
			System.out.println("NUMERO     : " + enderecoEmpresaDTO.getNumero());
			System.out.println("COMPLEMENTO: " + enderecoEmpresaDTO.getComplemento());
			
			EnderecoEmpresa endereco = this.fillEnderecoEmpresa(empresaNova, enderecoEmpresaDTO); //new EnderecoEmpresa();
			
//			endereco.setBairro(enderecoEmpresaDTO.getBairro());
//			endereco.setCep(enderecoEmpresaDTO.getCep());
//			endereco.setCidade(enderecoEmpresaDTO.getCidade());
//			endereco.setComplemento(enderecoEmpresaDTO.getComplemento());
//			endereco.setLogradouro(enderecoEmpresaDTO.getLogradouro());
//			endereco.setNumero(enderecoEmpresaDTO.getNumero());
//			endereco.setUf(enderecoEmpresaDTO.getUf());
//		
//			endereco.setEmpresa(empresaNova);
//			
//			enderecos.add(endereco);
			
			enderecoEmpresaRepository.save(endereco); 
		}

		return empresaNova.getId().longValue();
	}

	public String createCompanyAddress(Long idEmpresa, String jsonObj) throws StreamReadException, DatabindException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		EnderecoEmpresaDTO enderecoEmpresaDto = objectMapper.readValue(jsonObj.getBytes(), EnderecoEmpresaDTO.class);
		
		Optional<Empresa> opEmpresa = empresaRepository.findById(idEmpresa);
		
		Empresa empresa = opEmpresa.orElse(null);
		
		if (empresa != null) {

			EnderecoEmpresa endereco = this.fillEnderecoEmpresa(empresa, enderecoEmpresaDto); 
			
			enderecoEmpresaRepository.save(endereco); 
	
			return "Company address created successfully!";
		} else {
			return "Company not found!";
		}
	}
	
	public String deleteCompanyAddress(Long idEmpresa, Long id) throws Exception {
		
		Optional<Empresa> opEmpresa = empresaRepository.findById(idEmpresa);
		
		Empresa empresa = opEmpresa.orElse(null);
		
		if (empresa != null) {
			
			EnderecoEmpresa endereco = enderecoEmpresaRepository
					.findById(id)
					.orElseThrow(() -> new Exception("Address not exist with id: " + id.longValue()));
			
			 enderecoEmpresaRepository.delete(endereco);
			
			return "Address deleted successfully!";
		} else {
			return "Company not found!";
		}
	}

	private String getEmpresas(List<Empresa> listaEmpresas) throws JsonProcessingException {
		List<EmpresaDTO> listaEmpresasDTO = new ArrayList<>();
		
		for (Empresa empresa : listaEmpresas) {
			
			EmpresaDTO empresaDto = this.convertEmpresaToDto(empresa);
			
			listaEmpresasDTO.add(empresaDto);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		return objectMapper.writeValueAsString(listaEmpresasDTO);
	}
	
	private EmpresaDTO convertEmpresaToDto(Empresa empresa) {
		
	    EmpresaDTO empresaDto = modelMapper.map(empresa, EmpresaDTO.class);
	    
	    List<EnderecoEmpresa> listaEnderecos = enderecoEmpresaRepository.findByEmpresa(empresa);

	    List<EnderecoEmpresaDTO> listaEnderecosDto = new ArrayList<>(); 
	    
	    for (EnderecoEmpresa enderecoEmpresa : listaEnderecos) {
			EnderecoEmpresaDTO enderecoDto = modelMapper.map(enderecoEmpresa, EnderecoEmpresaDTO.class);
			listaEnderecosDto.add(enderecoDto);
		}
	    
	    empresaDto.setEnderecos(listaEnderecosDto);
	    
	    return empresaDto;
	}
	
	private EnderecoEmpresa fillEnderecoEmpresa(Empresa empresa, EnderecoEmpresaDTO enderecoEmpresaDTO) {
		EnderecoEmpresa endereco = new EnderecoEmpresa();
		endereco.setBairro(enderecoEmpresaDTO.getBairro());
		endereco.setCep(enderecoEmpresaDTO.getCep());
		endereco.setCidade(enderecoEmpresaDTO.getCidade());
		endereco.setComplemento(enderecoEmpresaDTO.getComplemento());
		endereco.setLogradouro(enderecoEmpresaDTO.getLogradouro());
		endereco.setNumero(enderecoEmpresaDTO.getNumero());
		endereco.setUf(enderecoEmpresaDTO.getUf());
	
		endereco.setEmpresa(empresa);
		
		return endereco;
	}
	
	
}
