package goya.daw2.pruebas_plantillas;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

enum Nivel {
	  Principiante,
	  Junior,
	  Mid,
	  Senior
	}

@Controller
public class FormsController {

	private Repositorio repo;
	public FormsController(Repositorio repos) 
	{
		this.repo=repos;
	}

	@PostMapping("/")
	String procesaEtapaX(@RequestParam(name = "respuesta", required = false) Integer respuesta,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "opcion") Integer opcion,
			HttpSession session,
			Model modelo) {

		if (nombre != null && !nombre.equals(session.getAttribute("nombre")))
			session.setAttribute("nombre", nombre);
		else 
		{
			 String nm =(String)session.getAttribute("nombre");
	         if (nm != null)
	        	 nombre = nm;
		}

		String errores = "";
		
		int numEtapa=((Integer)session.getAttribute("etapa"));
		System.out.println(numEtapa);

		// Validaciones
		if (numEtapa == 1) {
			if (nombre == null || nombre.isBlank()) {
				errores = "Debes poner un nombre no vacío";
			} else if (nombre.length() < 3 || nombre.length() > 10) {
				errores = "La longitud del nombre debe estar entre 3 y 10 caracteres";
			}
		}
		if (numEtapa > 1 && numEtapa<6) {
			if (respuesta == null && opcion==1)
				errores = "Debes seleccionar una opcion";
			if (respuesta !=null && opcion==1)
				session.setAttribute("puntuacion", (Integer)session.getAttribute("puntuacion")+respuesta);
		}

		if (!errores.isBlank()) 
		{
			modelo.addAttribute("nombre", nombre);
			return "etapa" + numEtapa;
		}
		
		numEtapa += opcion;

		if (numEtapa == 6) {
			Integer puntos=(Integer)session.getAttribute("puntuacion");
			modelo.addAttribute("correctas",puntos);
			modelo.addAttribute("nombre",nombre);
			modelo.addAttribute("tipo",getNivel(puntos));
			QuizBBDD dato=new QuizBBDD(new Date(),getNivel(puntos),puntos,nombre);
			repo.save(dato);
			session.invalidate();
		    return "etapa6";
		}
		session.setAttribute("etapa", numEtapa);
		return "etapa" + numEtapa;
	}

	@GetMapping("/")
	String getEtapa0(Model modelo, HttpSession session) 
	{
		//session.invalidate(); //para cancelar toda la sesion
		modelo.addAttribute("nombre", (String)session.getAttribute("nombre"));
		session.setAttribute("etapa", 1);
		session.setAttribute("puntuacion", 0);
		System.out.println(session.getAttribute("etapa"));
		return "etapa1";
	}
	
	Nivel getNivel(Integer puntos) {
		if(puntos<=1)
			return Nivel.Principiante;
		if(puntos==2)
			return Nivel.Junior;
		if(puntos==3)
			return Nivel.Mid;
		if(puntos>=4)
			return Nivel.Senior;
		return null;
	}
}
