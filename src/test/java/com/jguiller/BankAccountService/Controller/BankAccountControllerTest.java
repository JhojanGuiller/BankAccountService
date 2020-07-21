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

import reactor.core.publisher.Mono;

@WebFluxTest
public class BankAccountControllerTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private BankAccountService bankAccountService;
	
	@Test
	public void getAllBankAccountsTest() {
		
		webTestClient
		.get()
		.uri("/bankAccounts")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(BankAccount.class);
		
	}
	
	@Test
	public void getBankAccountByIdTest() {
		
		Integer id = 1;
		
		Mockito
		.when(this.bankAccountService.getBankAccountById(id))
		.thenReturn(Mono.just(new BankAccount(1, 1, 1, 100.0, "20/07/2020")));
		
		this.webTestClient
		.get()
		.uri("/bankAccounts/{idCuenta}", id)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.jsonPath("$..idCuenta").isEqualTo(1)
		.jsonPath("$..idCliente").isEqualTo(1)
		.jsonPath("$..idProducto").isEqualTo(1)
		.jsonPath("$..montoCuenta").isEqualTo(100.0)
		.jsonPath("$..fechaCreacionCuenta").isEqualTo("20/07/2020");
		
	}
	
	@Test
	public void addBankAccountTest() {
		
		BankAccount bankAccount = new BankAccount(1, 1, 1, 100.0, "20/07/2020");
		
		webTestClient
		.post()
		.uri("/bankAccounts/addBankAccount")
		.body(Mono.just(bankAccount), BankAccount.class)
		.exchange()
		.expectStatus().isOk();
		
	}
	
	@Test
	public void updateBankAccountTest() {
		
		BankAccount bankAcc1 = new BankAccount();
		BankAccount bankAcc2 = new BankAccount();
		
		webTestClient
		.put()
		.uri("/bankAccounts/updateBankAccount/{idCuenta}", bankAcc1.getIdCuenta())
		.body(Mono.just(bankAcc2), BankAccount.class)
		.exchange()
		.expectStatus().isOk();
		
	}
	
	@Test
	public void deleteBankAccountTest() {
		
		Integer id = 1;
		
		webTestClient
		.delete()
		.uri("/bankAccounts/deleteBankAccount/{idCuenta}", id)
		.exchange()
		.expectStatus().isOk();
		
	}

}
