package com.example.demo;


import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class Sesiones 
{
	@GetMapping("/")
	public String getMethodName(Datos datos) 
	{
		return "formulario1";
	}
	
	@PostMapping("/")
	public String postMethodName(@Valid Datos datos, BindingResult result, @RequestParam (name="opcion") int opcion) 
	{	
		if (result.hasErrors()) {
			return "formulario"+datos.getEstado();
		}
		System.out.println(datos);
		datos.setEstado(datos.getEstado()+opcion);
		if(datos.getEstado()>3) 
		{
			ArrayList<String> info=new ArrayList<String>();
			info.add("Nombre: "+datos.getNombre());
			info.add("Sexo: "+datos.getSex());
			info.add("Hobbies: "+datos.getHobbies().toString().replace("[", "").replace("]", ""));
			datos.setDatosFinales(info);
			return "resultados";
		}
		return "formulario"+datos.getEstado();
		/*datos.setEstado(datos.getEstado()+1);
		return datos.getEstado()<4?"formulario"+datos.getEstado():"resultados";*/
	}
}
