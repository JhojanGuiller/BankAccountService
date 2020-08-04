package com.jguiller.BankAccountService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.jguiller.BankAccountService.Model.BankAccount;
import com.jguiller.BankAccountService.Model.Client;
import com.jguiller.BankAccountService.Model.Product;
import com.jguiller.BankAccountService.Service.BankAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bankAccounts")
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	// OBTENER TODAS LA CUENTAS BANCARIAS
	@GetMapping
	public Flux<BankAccount> getBankAccounts(){
		return bankAccountService.getAllBankAccounts();
	}
	
	// CREAR UNA CUENTA BANCARIA
	@PostMapping("/addBankAccount")
	public Mono<BankAccount> saveBankAccount(@RequestBody BankAccount bankAccount) {
		return bankAccountService.addBankAccount(bankAccount);
	}
	
	// OBTENER UNA CUENTA BANCARIA POR ID	
	@GetMapping("/{idCuenta}")
	public Mono<BankAccount> getBankAccount(@PathVariable(value = "idCuenta") int id){
		return bankAccountService.getBankAccountById(id);
	}
	
	// ACTUALIZAR UN CUENTA BANCARIA
	@PutMapping("/updateBankAccount/{idCuenta}")
	public Mono<BankAccount> updateBankAccount(@RequestBody BankAccount bankAccount, @PathVariable(value = "idCuenta") int id) {
		return bankAccountService.updateBankAccount(bankAccount, id);
	}
	
	
	// ACTUALIZAR EL MONTO DE UNA CUENTA BANCARIA POR TIPO DE OPERACION
	@PutMapping("/updateBankAccountAmount/{idCuenta}/{tipoOperacion}")
	public Mono<ResponseEntity<BankAccount>> updateBankAccount(@PathVariable(value = "idCuenta") int id, @PathVariable String tipoOperacion, @RequestParam Double monto) {
		return bankAccountService.updateBankAccountAmount(id, tipoOperacion, monto);
	}
	
	// ELIMINAR UNA CUENTA BANCARIA POR ID
	@DeleteMapping("/deleteBankAccount/{idCuenta}")
	public Mono<Void> deleteBankAccount(@PathVariable(value = "idCuenta") int id) {
		return bankAccountService.deleteBankAccount(id);
	}
	
	
// ------------- START CUSTOM METHODS ------------
	
	//OBTENER LA CUENTA BANCARIA POR ID DE CLIENTE
	@GetMapping("/client/{idCliente}")
	public Flux<BankAccount> getBankAccByIdClient(@PathVariable(value = "idCliente") int id){
		return bankAccountService.getBankAccountByIdCliente(id);
	}

// ------------- END CUSTOM METHODS --------------
	
	@PostMapping("/addBankAccCustom")
	public Mono<BankAccount> addBankAcc(@RequestBody BankAccount bankAccount) {
		return bankAccountService.addBankAccountsCustom(bankAccount);
	}
	
// ------------- START CALL EXTERNAL MICROSERVICE ---------------
		
	//LLAMAR AL MICROSERVICE DE CLIENTE POR EL IDCLIENTE
	@GetMapping("/getClient/{idCliente}")
	public Mono<Client> getClient(@PathVariable(value = "idCliente") int id){
		return webClientBuilder.build()
				.get()
				.uri("http://localhost:8801/clients/" + id)
				.retrieve()
				.bodyToMono(Client.class);
	}
	
	//LLAMAR AL MICROSERVICE DE PRODUCTO POR EL IDPRODUCTO
	@GetMapping("/getProduct/{idProducto}")
	public Mono<Product> getProduct(@PathVariable(value = "idProducto") int id){
		return webClientBuilder.build()
				.get()
				.uri("http://localhost:8802/products/" + id)
				.retrieve()
				.bodyToMono(Product.class);
	}
	
// ------------- END CALL EXTERNAL MICROSERVICE -----------------
	
	
	
}
