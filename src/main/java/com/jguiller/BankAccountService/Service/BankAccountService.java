package com.jguiller.BankAccountService.Service;

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

	// OBTENER UNA CUENTA BANCARIA POR ID	
	public Mono<ResponseEntity<BankAccount>> getBankAccountById(Integer id) {
		return bankAccountRepository.findById(id)
				.map(bankAcc -> new ResponseEntity<BankAccount>(bankAcc, HttpStatus.OK))
				.defaultIfEmpty(notFound);
	}

	// ACTUALIZAR UN CUENTA BANCARIA
	public Mono<ResponseEntity<BankAccount>> updateBankAccount(BankAccount bankAccount, Integer id) {
		return bankAccountRepository.findById(id).flatMap(bankAcc -> {
			bankAcc.setIdCliente(bankAccount.getIdCliente());
			bankAcc.setIdProducto(bankAccount.getIdProducto());
			bankAcc.setMontoCuenta(bankAccount.getMontoCuenta());
			bankAcc.setFechaCreacionCuenta(bankAccount.getFechaCreacionCuenta());
			return bankAccountRepository.save(bankAcc);
		}).map(bankAcc1 -> new ResponseEntity<BankAccount>(bankAcc1, HttpStatus.OK)).defaultIfEmpty(notFound);
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
