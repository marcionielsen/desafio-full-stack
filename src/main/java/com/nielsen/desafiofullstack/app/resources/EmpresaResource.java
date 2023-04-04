package com.nielsen.desafiofullstack.app.resources;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.nielsen.desafiofullstack.app.services.EmpresaService;

@RestController
@RequestMapping(value="/empresas")
public class EmpresaResource {

	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping(path = "/")
	public String listAll() throws JsonProcessingException {		
		return empresaService.listAll(); 
	}
	
	@GetMapping(path = "/{id}")
	public String listCompanyById(@PathVariable Long id) throws JsonProcessingException {
		return empresaService.listCompanyById(id); 
	}
	
	@DeleteMapping(path = "/{id}")
	public String deleteCompanyById(@PathVariable Long id) {
		return empresaService.deleteCompany(id); 
	}

	@PutMapping(path = "/{id}")
	public String updateCompanyById(@PathVariable Long id, @RequestBody String body) throws Exception {
		System.out.println("#### Objeto recebido: " + body);
		return empresaService.updateCompany(id, body);  
	}

	@PostMapping(path = "/")
	public String createCompany(@RequestBody String body) throws StreamReadException, DatabindException, IOException {
		 
		System.out.println("#### Objeto recebido: " + body);
		
		long empresaCadastrada = empresaService.saveCompany(body);
		
		return "Company created successfully! - " + empresaCadastrada;
	}

	@PostMapping(path = "/{idEmpresa}/endereco")
	public String createCompanyAddress(@PathVariable Long idEmpresa, @RequestBody String body) throws StreamReadException, DatabindException, IOException {
		 
		System.out.println("#### Objeto recebido: " + body);
		 
		return empresaService.createCompanyAddress(idEmpresa, body); 
	}

	@DeleteMapping(path = "/{idEmpresa}/endereco/{id}")
	public String deleteCompanyAddressById(@PathVariable Long idEmpresa, @PathVariable Long id) throws Exception {
		return empresaService.deleteCompanyAddress(idEmpresa, id); 
	}

}
