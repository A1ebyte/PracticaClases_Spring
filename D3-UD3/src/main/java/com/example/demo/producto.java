package com.example.demo;

import java.io.Serializable;

public class producto implements Serializable {
	private static final long serialVersionUID = 1L;
	Integer cantidadTotal;
	Double precio;

	public Integer getCantidadTotal() {
		return cantidadTotal;
	}

	public void setCantidadTotal(Integer cantidad) {
		this.cantidadTotal = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public producto(Integer cantidad, Double precio) {
		this.precio = precio;
		this.cantidadTotal = cantidad;
	}

	@Override
	public String toString() {
		return "Producto{cantidad=" + cantidadTotal + ", precio=" + precio + "}";
	}
}
