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

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.nielsen.desafiofullstack.app.services.EmpresaService;

@RestController
@RequestMapping(value="/empresas")
public class EmpresaResource {

	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping(path = "/")
	public String listAll() {
		return "Get List All Method OK!";
	}
	
	@GetMapping(path = "/{id}")
	public String listCompanyById(@PathVariable String id) {
		return "Company retrieved By Id: " + id;
	}
	
	@DeleteMapping(path = "/{id}")
	public String deleteCompanyById(@PathVariable String id) {
		return "Company deleted By Id: " + id;
	}

	@PutMapping(path = "/{id}")
	public String updateCompanyById(@PathVariable String id, @RequestBody String body) {
		System.out.println("#### Objeto recebido: " + body);
		return "Company updated By Id: " + id;
	}

	@PostMapping(path = "/")
	public String createCompany(@RequestBody String body) throws StreamReadException, DatabindException, IOException {
		 
		System.out.println("#### Objeto recebido: " + body);
		
		long empresaCadastrada = empresaService.saveCompany(body);
		
		return "Company created successfully! - " + empresaCadastrada;
	}

	@DeleteMapping(path = "/endereco/{id}")
	public String deleteCompanyAddressById(@PathVariable String id) {
		return "Company Address deleted By Id: " + id;
	}

	@PostMapping(path = "/endereco")
	public String createCompanyAddress(@RequestBody String body) {
		 
		System.out.println("#### Objeto recebido: " + body);
		 
		return "Company Address created successfully!";
	}
	
	
	
}
