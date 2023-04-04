package com.nielsen.desafiofullstack.app.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value="/ceps")
public class CepResource {

	@GetMapping(path = "/{idCep}")
	public String listCompanyById(@PathVariable String idCep) throws JsonProcessingException {
		
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(interceptors);
		
		String uri = "http://cep.la/" + idCep;
		String jsonCeps = restTemplate.getForObject(uri, String.class);
		
		return jsonCeps;
	}
	
	public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

        private final String headerName;

        private final String headerValue;

        public HeaderRequestInterceptor(String headerName, String headerValue) {
            this.headerName = headerName;
            this.headerValue = headerValue;
        }

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {
			request.getHeaders().set(headerName, headerValue);
            return execution.execute(request, body);
		}
    }
}
