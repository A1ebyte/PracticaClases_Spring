package com.example.demo;
import java.util.ArrayList;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Datos 
{
	enum sexo{Hombre,Mujer,No_Binario,Helicoptero_Apache}
	
	@NotNull
	@Size(min=1)
	private String nombre="";
	
	@NotNull
	private sexo sex=sexo.Helicoptero_Apache;
	
	private ArrayList<String> hobbies=new ArrayList<String>();
	
	private int estado=1;
	
	private ArrayList<String> datosFinales=new ArrayList<String>();
	
	public ArrayList<String> getDatosFinales() {
		return datosFinales;
	}
	
	public void setDatosFinales(ArrayList<String> datosFinales) {
		this.datosFinales = datosFinales;
	}
	
	public ArrayList<String> getHobbies() 
	{
		return hobbies;
	}

	public void setHobbies(ArrayList<String> hobbies) 
	{
		this.hobbies = hobbies;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public sexo getSex() {
		return sex;
	}

	public void setSex(sexo sex) {
		this.sex = sex;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Datos [nombre=" + nombre + ", sex=" + sex + ", hobbies=" + hobbies + ", estado=" + estado
				+ ", datosFinales=" + datosFinales + "]";
	}
}
