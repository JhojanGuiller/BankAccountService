package com.jguiller.BankAccountService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import com.jguiller.BankAccountService.Model.BankAccount;
import com.jguiller.BankAccountService.Model.Client;
import com.jguiller.BankAccountService.Model.Product;
import com.jguiller.BankAccountService.Repository.BankAccountRepository;

import javassist.NotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	private ResponseEntity<BankAccount> notFound = ResponseEntity.notFound().build();
	
// ------------- START CALL EXTERNAL MICROSERVICE ---------------
	
	//LLAMAR AL MICROSERVICE DE CLIENTE POR EL IDCLIENTE
	public Mono<Client> getClient(@PathVariable(value = "idCliente") int id){
		return webClientBuilder.build()
								.get()
								.uri("http://localhost:8801/clients/" + id)
								.retrieve()
								.bodyToMono(Client.class);
	}
		
	//LLAMAR AL MICROSERVICE DE PRODUCTO POR EL IDPRODUCTO
	public Mono<Product> getProduct(@PathVariable(value = "idProducto") int id){
		return webClientBuilder.build()
								.get()
								.uri("http://localhost:8802/products/" + id)
								.retrieve()
								.bodyToMono(Product.class);
	}
		
// ------------- END CALL EXTERNAL MICROSERVICE -----------------		
	
	// OBTENER TODAS LA CUENTAS BANCARIAS
	public Flux<BankAccount> getAllBankAccounts() {
		return bankAccountRepository.findAll();
	}

	// CREAR UNA CUENTA BANCARIA
	public Mono<BankAccount> addBankAccount(BankAccount bankAccount) {
		return bankAccountRepository.save(bankAccount);
	}
	
	// CREAR UNA CUENTA BANCARIA DONDE TENGA UN MONTO MINIMO
	public Mono<BankAccount> addBankAccountMinAmount(BankAccount bankAccount){
		Product producto = (Product) getProduct(bankAccount.getIdProducto()).subscribe();
		
		if (producto.getProducto().equals("Ahorro personal VIP") || producto.getProducto().equals("Corriente personal VIP") || producto.getProducto().equals("Empresarial PYME") || producto.getProducto().equals("Empresarial Corporativo") || producto.getProducto().equals("A plazo fijo VIP")) {
			if (bankAccount.getMontoCuenta() >= 50.0) {
				return bankAccountRepository.save(bankAccount);
			}else {
				return Mono.error(new NotFoundException("El monto minimo de creacion para este tipo de perfil es S/. 50.0s")); //Configurar un msg de error adecuado ("El monto minimo de creacion para este tipo de perfil es S/. 50.0") 
			}
		}else {
			return bankAccountRepository.save(bankAccount);
		}
	}
	
// ----------- START CUSTOM METHODS -----------------
	
	//OBTENER UNA CUENTA BANCARIA POR IDCLIENTE
	public Flux<BankAccount> getBankAccountByIdCliente(Integer id) {
		return bankAccountRepository.findByIdCliente(id);
	}
	
	public Mono<List<BankAccount>> getBankAccountByIdClienteBlock(Integer id) {
		return getBankAccountByIdCliente(id).collectList();
	}
	
	// CREACION DE UNA CUENTA BANCARIA CON VERIFICACION
	public Mono<BankAccount> addBankAccountsCustom(BankAccount bankAccount){
		
		//Mono<List<BankAccount>> bankAcc = getBankAccountByIdClienteBlock(bankAccount.getIdCliente());
		
		Client client = getClient(bankAccount.getIdCliente()).block();
		if (client.getTipoCliente() == "Personal") {
			if (bankAccount.getIdProducto() == 1 || bankAccount.getIdProducto() == 2 || bankAccount.getIdProducto() == 3) {
//				if (bankAcc != bankAccount.getIdProducto()) {
//					return bankAccountRepository.save(bankAccount);
//				}else {
					return null; // Pendiente configurar un mensaje de error adecuado
				//}
			}else {
				return bankAccountRepository.save(bankAccount);
			}
		}else if (client.getTipoCliente() == "Empresarial") {
			if (bankAccount.getIdProducto() == 1 || bankAccount.getIdProducto() == 3) {
				return null; // Pendiente configurar un mensaje de error adecuado
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
