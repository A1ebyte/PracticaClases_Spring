package com.example.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class RepositorioStock {
	protected Map<String, producto> stock;
	protected final static String RUTA_FICHERO = "stock.data";

	public RepositorioStock() {
		// super();
		stock = load();
	}

	public Map<String, producto> load() {
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(RUTA_FICHERO))) {
			return (HashMap<String, producto>) entrada.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Problema leyendo archivo " + RUTA_FICHERO);
			return new HashMap<String, producto>();
		}
	}

	public boolean save() {
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(RUTA_FICHERO))) {
			salida.writeObject(stock);
		} catch (IOException e1) {
			System.err.println("ERROR GUARDANDO STOCK");
			return false;
		}
		return true;
	}

	public Map<String, producto> getAll() {
		return stock;
	}

	public producto getOne(String producto) {
		return stock.get(producto);
	}

	public void add(String producto, producto datos) {
		stock.put(producto, datos);
		save();
	}

	public void del(String producto) {
		stock.remove(producto);
		save();
	}

	public void modify(String producto, producto datos) {
		add(producto, datos);
	}
}
