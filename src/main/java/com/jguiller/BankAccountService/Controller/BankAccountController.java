package com.jguiller.BankAccountService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	public BankAccount getBankAccount(@PathVariable int idCuenta){
		return bankAccountRepository.findByIdCuenta(idCuenta);
	}
	
//	Actualizar el monto de la cuenta bancaria
	@PutMapping("/updateBankAccountAmount/{idCuenta}/{tipoOperacion}")
	public String updateBankAccount(@PathVariable int idCuenta, @PathVariable String tipoOperacion, @RequestParam Double monto) {
		BankAccount bankAccount = bankAccountRepository.findByIdCuenta(idCuenta);
		
		if(tipoOperacion.equals("Deposito")) {		
			
			Double opeDeposito = bankAccount.getMontoCuenta() + monto;
			bankAccount.setMontoCuenta(opeDeposito);
			
		}else if (tipoOperacion.equals("Retiro")) {
			
			Double opeRetiro = bankAccount.getMontoCuenta() - monto;
			bankAccount.setMontoCuenta(opeRetiro);
			
		}else {
			return "Ingrese una operacion correcta";
		}		
		bankAccountRepository.save(bankAccount);
		return "Updated Bank Account Successfully: " + bankAccount.getIdCuenta();
	}
	
	@GetMapping("/deleteBankAccount/{idCuenta}")
	public String deleteBankAccount(@PathVariable int idCuenta) {
		bankAccountRepository.deleteByIdCuenta(idCuenta);
		return "Deleted Bank Account Successfully: " + idCuenta;
	}
	
}
