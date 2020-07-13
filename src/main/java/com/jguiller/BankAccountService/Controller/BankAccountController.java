package com.jguiller.BankAccountService.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jguiller.BankAccountService.Model.BankAccount;
import com.jguiller.BankAccountService.Repository.BankAccountRepository;

@RestController
public class BankAccountController {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@PostMapping("/addBankAccount")
	public String saveBankAccount(@RequestBody BankAccount bankAccount) {
		bankAccountRepository.save(bankAccount);
		return "Bank Account add successfully: " + bankAccount.getIdCuenta();
	}
	
	@GetMapping("/findAllBankAccounts")
	public List<BankAccount> getBankAccounts(){
		return bankAccountRepository.findAll();
	}
	
	@GetMapping("/findBankAccount/{idCuenta}")
	public Optional<BankAccount> getBankAccount(@PathVariable int idCuenta){
		return bankAccountRepository.findByIdCuenta(idCuenta);
	}
	
	@GetMapping("/deleteBankAccount/{idCuenta}")
	public String deleteBankAccount(@PathVariable int idCuenta) {
		bankAccountRepository.deleteByIdCuenta(idCuenta);
		return "Deleted Bank Account Successfully: " + idCuenta;
	}
	
}
