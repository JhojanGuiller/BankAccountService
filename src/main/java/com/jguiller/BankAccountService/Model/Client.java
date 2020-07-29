package com.jguiller.BankAccountService.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sun.istack.NotNull;

@Document(collection = "Clients")
public class Client {
	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idCliente;

	private String nombre;
	private String apellido;
	private String dni;
	private String tipoCliente;
	
	public Client() {	}
	
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	
	public Client(int idCliente, String nombre, String apellido, String dni, String tipoCliente) {
		super();
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.tipoCliente = tipoCliente;
	}
	
}
