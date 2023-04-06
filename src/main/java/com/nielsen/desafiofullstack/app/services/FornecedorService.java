package com.nielsen.desafiofullstack.app.services;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
import com.nielsen.desafiofullstack.app.domain.dto.EnderecoFornecedorDTO;
import com.nielsen.desafiofullstack.app.domain.dto.FornecedorDTO;
import com.nielsen.desafiofullstack.app.domain.entities.EnderecoFornecedor;
import com.nielsen.desafiofullstack.app.domain.entities.Fornecedor;
import com.nielsen.desafiofullstack.app.domain.enums.TipoPessoa;
import com.nielsen.desafiofullstack.app.repositories.EnderecoFornecedorRepository;
import com.nielsen.desafiofullstack.app.repositories.FornecedorRepository;

@Service
public class FornecedorService {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private EnderecoFornecedorRepository enderecoFornecedorRepository;
	
    @Autowired
    private ModelMapper modelMapper;
    
	public String listAll() throws JsonProcessingException {
		
		List<Fornecedor> listaFornecedores = fornecedorRepository.findAll();

		return this.getFornecedores(listaFornecedores);		
	}
	
	public String listSupplierById(Long id) throws JsonProcessingException {
		
		List<Fornecedor> listaFornecedores = new ArrayList<>();
		
		Optional<Fornecedor> opFornecedor = fornecedorRepository.findById(id);
		
		Fornecedor fornecedor = opFornecedor.orElse(null);
		
		if (fornecedor != null) {
			
			listaFornecedores.add(fornecedor);
			
			return this.getFornecedores(listaFornecedores);
		} else {
			return "Supplier not found!";
		}
	}

	public String deleteSupplier(Long id) {
		
		Optional<Fornecedor> opFornecedor = fornecedorRepository.findById(id);
		
		Fornecedor fornecedor = opFornecedor.orElse(null);
		
		if (fornecedor != null) {
			
			List<EnderecoFornecedor> listaEnderecos = enderecoFornecedorRepository.findByFornecedor(fornecedor);
			
			for (EnderecoFornecedor endereco  : listaEnderecos) {
				 enderecoFornecedorRepository.delete(endereco);
			}
			
			fornecedorRepository.delete(fornecedor);
			
			return "Supplier and addresses deleted successfully!";
		} else {
			return "Supplier not found!";
		}
	}

	public String updateSupplier(Long id, String jsonObj) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		
		FornecedorDTO fornecedorDto = objectMapper.readValue(jsonObj.getBytes(), FornecedorDTO.class);
		
		Optional<Fornecedor> opFornecedor = fornecedorRepository.findById(id);
		
		Fornecedor fornecedor = opFornecedor.orElse(null);
		
		if (fornecedor != null) {
			
			validarTipoPessoa(fornecedorDto);
			
			List<EnderecoFornecedorDTO> listaEnderecosDto = fornecedorDto.getEnderecos();
			for (EnderecoFornecedorDTO enderecoFornecedorDTO : listaEnderecosDto) {
				
				EnderecoFornecedor endereco = enderecoFornecedorRepository
						.findById(enderecoFornecedorDTO.getId())
						.orElseThrow(() -> new Exception("Address not exist with id: " + enderecoFornecedorDTO.getId().longValue()));
				
				endereco.setBairro(enderecoFornecedorDTO.getBairro());
				endereco.setCep(enderecoFornecedorDTO.getCep());
				endereco.setCidade(enderecoFornecedorDTO.getCidade());
				endereco.setComplemento(enderecoFornecedorDTO.getComplemento());
				endereco.setLogradouro(enderecoFornecedorDTO.getLogradouro());
				endereco.setNumero(enderecoFornecedorDTO.getNumero());
				endereco.setUf(enderecoFornecedorDTO.getUf());
				
				enderecoFornecedorRepository.save(endereco);
			}
			
			fornecedor.setCnpjCpf(fornecedorDto.getCnpjCpf());
			fornecedor.setDataNascimento(fornecedorDto.getDataNascimento());
			fornecedor.setEmail(fornecedorDto.getEmail());
			fornecedor.setNumeroRg(fornecedorDto.getNumeroRg());
			fornecedor.setNome(fornecedorDto.getNome());			
			fornecedor.setTipoPessoa(fornecedorDto.getTipoPessoa());
			
			fornecedorRepository.save(fornecedor);
			
			return "Supplier and addresses updated successfully!";
		} else {
			return "Supplier not found!";
		}
		
	}
	
	private void validarTipoPessoa(FornecedorDTO fornecedorDto) throws Exception {
		
		if (TipoPessoa.toEnum(fornecedorDto.getTipoPessoa()).equals(TipoPessoa.PESSOAFISICA) && fornecedorDto.getDataNascimento() == null && fornecedorDto.getNumeroRg().isBlank()) {
			throw new Exception("Para cadastrar fornecedor do tipo Pessoa Física é obrigatório informar RG e Data de Nascimento!");
		}
	}
	
	public long saveSupplier(FornecedorDTO body) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		
		FornecedorDTO fornecedorDto = body; // objectMapper.readValue(jsonObj.getBytes(), FornecedorDTO.class);
		
		List<EnderecoFornecedorDTO> enderecosDTO = fornecedorDto.getEnderecos();
		
		System.out.println("######[ FornecedorDTO ]######");
		System.out.println("CNPJ : " + fornecedorDto.getCnpjCpf());
		System.out.println("NOME : " + fornecedorDto.getNome());
		System.out.println("EMAIL: " + fornecedorDto.getEmail());
		
		if (fornecedorDto.getNumeroRg() != null && !fornecedorDto.getNumeroRg().isEmpty() ) {
			System.out.println("RG   : " + fornecedorDto.getNumeroRg());
		}
		
		if (fornecedorDto.getDataNascimento() != null) { 
			System.out.println("DATA : " + fornecedorDto.getDataNascimento().format(formatter));
		}
		
		System.out.println("TIPO : " + fornecedorDto.getTipoPessoa());

		validarTipoPessoa(fornecedorDto);
		
		Fornecedor fornecedor = this.fillFornecedor(fornecedorDto); 
		
		Fornecedor fornecedorNovo = fornecedorRepository.save(fornecedor);
		
		for (EnderecoFornecedorDTO enderecoFornecedorDTO : enderecosDTO) {
			System.out.println("--------------------------");
			System.out.println("CEP        : " + enderecoFornecedorDTO.getCep());
			System.out.println("UF         : " + enderecoFornecedorDTO.getUf());
			System.out.println("CIDADE     : " + enderecoFornecedorDTO.getCidade());
			System.out.println("BAIRRO     : " + enderecoFornecedorDTO.getBairro());
			System.out.println("LOGRADOURO : " + enderecoFornecedorDTO.getLogradouro());
			System.out.println("NUMERO     : " + enderecoFornecedorDTO.getNumero());
			System.out.println("COMPLEMENTO: " + enderecoFornecedorDTO.getComplemento());
			
			EnderecoFornecedor endereco = this.fillEnderecoFornecedor(fornecedorNovo, enderecoFornecedorDTO); 
			
			enderecoFornecedorRepository.save(endereco); 
		}

		return fornecedorNovo.getId().longValue();
	}
	
	public String createSupplierAddress(Long idFornecedor, String jsonObj) throws StreamReadException, DatabindException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		EnderecoFornecedorDTO enderecoFornecedorDto = objectMapper.readValue(jsonObj.getBytes(), EnderecoFornecedorDTO.class);
		
		Optional<Fornecedor> opFornecedor = fornecedorRepository.findById(idFornecedor);
		
		Fornecedor fornecedor = opFornecedor.orElse(null);
		
		if (fornecedor != null) {

			EnderecoFornecedor endereco = this.fillEnderecoFornecedor(fornecedor, enderecoFornecedorDto); 
			
			enderecoFornecedorRepository.save(endereco); 
	
			return "Supplier address created successfully!";
		} else {
			return "Supplier not found!";
		}
	}
	
	public String deleteSupplierAddress(Long idFornecedor, Long id) throws Exception {
		
		Optional<Fornecedor> opFornecedor = fornecedorRepository.findById(idFornecedor);
		
		Fornecedor fornecedor = opFornecedor.orElse(null);
		
		if (fornecedor != null) {
			
			EnderecoFornecedor endereco = enderecoFornecedorRepository
					.findById(id)
					.orElseThrow(() -> new Exception("Address not exist with id: " + id.longValue()));
			
			 enderecoFornecedorRepository.delete(endereco);
			
			return "Address deleted successfully!";
		} else {
			return "Supplier not found!";
		}
	}
	
	private String getFornecedores(List<Fornecedor> listaFornecedores) throws JsonProcessingException {
		List<FornecedorDTO> listaFornecedoresDTO = new ArrayList<>();
		
		for (Fornecedor fornecedor : listaFornecedores) {
			
			FornecedorDTO fornecedorDto = this.convertFornecedorToDto(fornecedor);
			
			listaFornecedoresDTO.add(fornecedorDto);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		return objectMapper.writeValueAsString(listaFornecedoresDTO);
	}
	
	private FornecedorDTO convertFornecedorToDto(Fornecedor fornecedor) {
		
	    FornecedorDTO fornecedorDto = modelMapper.map(fornecedor, FornecedorDTO.class);
	    
	    List<EnderecoFornecedor> listaEnderecos = enderecoFornecedorRepository.findByFornecedor(fornecedor);

	    List<EnderecoFornecedorDTO> listaEnderecosDto = new ArrayList<>(); 
	    
	    for (EnderecoFornecedor enderecoFornecedor : listaEnderecos) {
			EnderecoFornecedorDTO enderecoDto = modelMapper.map(enderecoFornecedor, EnderecoFornecedorDTO.class);
			listaEnderecosDto.add(enderecoDto);
		}
	    
	    fornecedorDto.setEnderecos(listaEnderecosDto);
	    
	    return fornecedorDto;
	}
	
	private Fornecedor fillFornecedor(FornecedorDTO fornecedorDTO) {
		Fornecedor fornecedor = modelMapper.map(fornecedorDTO, Fornecedor.class);
	    
		return fornecedor;
	}
	
	private EnderecoFornecedor fillEnderecoFornecedor(Fornecedor fornecedor, EnderecoFornecedorDTO enderecoFornecedorDTO) {
		EnderecoFornecedor endereco = new EnderecoFornecedor();
		endereco.setBairro(enderecoFornecedorDTO.getBairro());
		endereco.setCep(enderecoFornecedorDTO.getCep());
		endereco.setCidade(enderecoFornecedorDTO.getCidade());
		endereco.setComplemento(enderecoFornecedorDTO.getComplemento());
		endereco.setLogradouro(enderecoFornecedorDTO.getLogradouro());
		endereco.setNumero(enderecoFornecedorDTO.getNumero());
		endereco.setUf(enderecoFornecedorDTO.getUf());
	
		endereco.setFornecedor(fornecedor);
		
		return endereco;
	}
	
}
