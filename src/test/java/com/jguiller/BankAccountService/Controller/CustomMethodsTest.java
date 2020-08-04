package com.jguiller.BankAccountService.Controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.jguiller.BankAccountService.Model.BankAccount;
import com.jguiller.BankAccountService.Service.BankAccountService;

import reactor.core.publisher.Flux;

@WebFluxTest
public class CustomMethodsTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private BankAccountService bankAccountService;
	
	//OBTENER UNA CUENTA BANCARIA POR IDCLIENTE
	
	@Test
	public void getBankAccountByIdClient() {
		Integer id = 1;
		
		Mockito
		.when(this.bankAccountService.getBankAccountByIdCliente(id))
		.thenReturn(Flux.just(new BankAccount(2, 1, 3, 200.0, "24/07/2020")));
		
		this.webTestClient
		.get()
		.uri("/bankAccounts/client/{idCliente}", id)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.jsonPath("$..idCuenta").isEqualTo(2)
		.jsonPath("$..idCliente").isEqualTo(1)
		.jsonPath("$..idProducto").isEqualTo(3)
		.jsonPath("$..montoCuenta").isEqualTo(200.0)
		.jsonPath("$..fechaCreacionCuenta").isEqualTo("24/07/2020");
	}
	
}
