package com.jguiller.BankAccountService.Service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jguiller.BankAccountService.Model.BankAccount;
import com.jguiller.BankAccountService.Repository.BankAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	private ResponseEntity<BankAccount> notFound = ResponseEntity.notFound().build();
	
	// OBTENER TODAS LA CUENTAS BANCARIAS
	public Flux<BankAccount> getAllBankAccounts() {
		return bankAccountRepository.findAll();
	}

	// CREAR UNA CUENTA BANCARIA
	public Mono<BankAccount> addBankAccount(BankAccount bankAccount) {
		return bankAccountRepository.save(bankAccount);
	}
	
	// ----------- START CUSTOM METHODS -----------------
	
	//OBTENER UNA CUENTA BANCARIA POR IDCLIENTE
	public BankAccount getBankAccountByIdCliente(Integer id) {
		return bankAccountRepository.findByIdCliente(id).block();
	}
	
	public Mono<BankAccount> addBankAccountCustom(BankAccount bankAccount) throws Exception {
		
		// Si tipoCliente = Personal, solo puede tener 1 cuentaAhorro, 1 cuentaCorriente o 1 cuentaPlazoFijo
		// Si idCliente = 1 => idProducto = 1, 2 o 3
		BankAccount bankAcc = getBankAccountByIdCliente(bankAccount.getIdCliente());
		if (bankAccount.getIdCliente() == 1 ) {
			if(bankAcc.getIdProducto() == 1 || bankAcc.getIdProducto() == 2 || bankAcc.getIdProducto() == 3) {
				throw new NoSuchElementException("Error 2020");
			}else {
				return bankAccountRepository.save(bankAccount);
			}
		}else {
			return bankAccountRepository.save(bankAccount);
		}
		
	}
	
	// ---------- END CUSTOM METHODS ------------

	// OBTENER UNA CUENTA BANCARIA POR ID	
	public Mono<BankAccount> getBankAccountById(Integer id) {
		return bankAccountRepository.findById(id);
	}

	// ACTUALIZAR UN CUENTA BANCARIA
	public Mono<BankAccount> updateBankAccount(BankAccount bankAccount, Integer id) {
		return bankAccountRepository.findById(id).flatMap(bankAcc -> {
			bankAcc.setIdCliente(bankAccount.getIdCliente());
			bankAcc.setIdProducto(bankAccount.getIdProducto());
			bankAcc.setMontoCuenta(bankAccount.getMontoCuenta());
			bankAcc.setFechaCreacionCuenta(bankAccount.getFechaCreacionCuenta());
			return bankAccountRepository.save(bankAcc);
		});
	}

	// ACTUALIZAR EL MONTO DE UNA CUENTA BANCARIA POR TIPO DE OPERACION
	public Mono<ResponseEntity<BankAccount>> updateBankAccountAmount(int id, String tipoOperacion, double monto) {
		return bankAccountRepository.findById(id).flatMap(bankAccAm -> {
			if (tipoOperacion.equals("Deposito")) {
				bankAccAm.setMontoCuenta(bankAccAm.getMontoCuenta() + monto);
				return bankAccountRepository.save(bankAccAm);
			}else if (tipoOperacion.equals("Retiro")) {
				bankAccAm.setMontoCuenta(bankAccAm.getMontoCuenta() - monto);
				return bankAccountRepository.save(bankAccAm);
			}else {
				return null;
			}
		}).map(bankAccAm1 -> new ResponseEntity<BankAccount>(bankAccAm1, HttpStatus.OK)).defaultIfEmpty(notFound);
	}
	
	// ELIMINAR UNA CUENTA BANCARIA POR ID
	public Mono<Void> deleteBankAccount(Integer id) {
		return bankAccountRepository.deleteById(id);
	}
}
