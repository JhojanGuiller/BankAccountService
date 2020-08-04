package com.jguiller.BankAccountService.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "BankAccounts")
@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccount {
	
	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idCuenta;

	@NotNull
	private int idCliente;

	@NotNull
	private int idProducto;
	
	private Double montoCuenta;
	
	private String fechaCreacionCuenta;
	
}
