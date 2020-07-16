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

import com.jguiller.BankAccountService.Model.BankAccount;
import com.jguiller.BankAccountService.Service.BankAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bankAccounts")
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
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
	public Mono<ResponseEntity<BankAccount>> getBankAccount(@PathVariable(value = "idCuenta") int id){
		return bankAccountService.getBankAccountById(id);
	}
	
	// ACTUALIZAR UN CUENTA BANCARIA
	@PutMapping("/updateBankAccount/{idCuenta}")
	public Mono<ResponseEntity<BankAccount>> updateBankAccount(@RequestBody BankAccount bankAccount, @PathVariable(value = "idCuenta") int id) {
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
	
}
