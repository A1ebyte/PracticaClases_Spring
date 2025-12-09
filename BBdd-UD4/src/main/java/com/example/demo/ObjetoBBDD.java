package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class ObjetoBBDD {
	@Id
	private String nombre;

	private Integer stock;

	private Double precio;
	
	public ObjetoBBDD( String nombre, Integer stock, Double precio) 
	{
		this.nombre = nombre;
		this.stock = stock;
		this.precio = precio;
	}

	public ObjetoBBDD() 
	{
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "ObjetoBBDD [nombre=" + nombre + ", stock=" + stock + ", precio=" + precio + "]";
	}

}
