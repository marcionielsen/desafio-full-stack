package com.nielsen.desafiofullstack.app.resources;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.nielsen.desafiofullstack.app.domain.dto.FornecedorDTO;
import com.nielsen.desafiofullstack.app.services.FornecedorService;

import lombok.Data;

@RestController
@RequestMapping(value = "/fornecedores")
public class FornecedorResource {

	@Autowired
	private FornecedorService fornecedorService;

	@PostMapping(path = "/teste")
	public Object test(@RequestBody Payload payload) {
		Map<String, Object> ret = new HashMap<>();
		ret.put("payload", payload); // request body
		ret.put("now", new Date());
		return ret;
	}

	@GetMapping(path = "/")
	public String listAll() throws JsonProcessingException {
		return fornecedorService.listAll();
	}

	@GetMapping(path = "/{id}")
	public String listSupplierById(@PathVariable Long id) throws JsonProcessingException {
		return fornecedorService.listSupplierById(id);
	}

	@DeleteMapping(path = "/{id}")
	public String deleteSupplierById(@PathVariable Long id) {
		return fornecedorService.deleteSupplier(id);
	}

	@PutMapping(path = "/{id}")
	public String updateSupplierById(@PathVariable Long id, @RequestBody String body) throws Exception {
		System.out.println("#### Objeto recebido: " + body);
		
		try {
			return fornecedorService.updateSupplier(id, body);
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@PostMapping(path = "/")
	public String createSupplier(@RequestBody FornecedorDTO body) throws Exception {

		System.out.println("#### Objeto recebido: " + body);

		try {
			long fornecedorCadastrado = fornecedorService.saveSupplier(body);

			return "Supplier created successfully! - " + fornecedorCadastrado;
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@PostMapping(path = "/{idFornecedor}/endereco")
	public String createSupplierAddress(@PathVariable Long idFornecedor, @RequestBody String body)
			throws StreamReadException, DatabindException, IOException {

		System.out.println("#### Objeto recebido: " + body);

		return fornecedorService.createSupplierAddress(idFornecedor, body);
	}

	@DeleteMapping(path = "/{idFornecedor}/endereco/{id}")
	public String deleteSupplierAddressById(@PathVariable Long idFornecedor, @PathVariable Long id) throws Exception {
		return fornecedorService.deleteSupplierAddress(idFornecedor, id);
	}

}

@Data
class Payload implements Serializable {
	
	private static final long serialVersionUID = 1L;

    @JsonInclude(Include.NON_NULL)
    private LocalDate date;
    
    @JsonInclude(Include.NON_NULL)
    private LocalDateTime  dateTime;
    
}
