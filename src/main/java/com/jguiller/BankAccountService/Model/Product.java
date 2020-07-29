package com.jguiller.BankAccountService.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
public class Product {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProducto;
	
	private String tipoProducto;
	private String producto;
	
	public Product() {	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public Product(@NotNull int idProducto, String tipoProducto, String producto) {
		super();
		this.idProducto = idProducto;
		this.tipoProducto = tipoProducto;
		this.producto = producto;
	}
	

}
