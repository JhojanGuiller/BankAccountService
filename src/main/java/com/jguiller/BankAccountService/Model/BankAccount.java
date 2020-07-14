package com.jguiller.BankAccountService.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BankAccounts")
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
	
	public BankAccount() {}

	public int getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public Double getMontoCuenta() {
		return montoCuenta;
	}

	public void setMontoCuenta(Double montoCuenta) {
		this.montoCuenta = montoCuenta;
	}

	public String getFechaCreacionCuenta() {
		return fechaCreacionCuenta;
	}

	public void setFechaCreacionCuenta(String fechaCreacionCuenta) {
		this.fechaCreacionCuenta = fechaCreacionCuenta;
	}

	public BankAccount(@NotNull int idCuenta, @NotNull int idCliente, @NotNull int idProducto, Double montoCuenta,
			String fechaCreacionCuenta) {
		super();
		this.idCuenta = idCuenta;
		this.idCliente = idCliente;
		this.idProducto = idProducto;
		this.montoCuenta = montoCuenta;
		this.fechaCreacionCuenta = fechaCreacionCuenta;
	}
	
}
