package goya.daw2.pruebas_plantillas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class FormsController {

	static final String[] SIGNOS = { "", "Aries", "Tauro", "Géminis", "Cáncer", "Leo", "Virgo", "Libra", "Escorpio",
			"Sagitario", "Capricornio", "Acuario", "Piscis" };

	static final String[] AFICCIONES = { "Deportes", "Juerga", "Lectura", "Relaciones sociales" };

	@PostMapping("/")
	String procesaEtapaX(@RequestParam(name = "numEtapa") Integer numEtapa,
			@RequestParam(name = "aficciones", required = false) List<String> aficciones,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "signo", required = false) Integer signo, 
			@RequestParam(name = "opcion") Integer opcion,
			HttpSession session,
			Model modelo) {

		modelo.addAttribute("signos", SIGNOS);
		modelo.addAttribute("aficcionesList", AFICCIONES);

		if (numEtapa == null)
			return "etapa1";

		if (nombre != null && !nombre.equals(session.getAttribute("nombre")))
			session.setAttribute("nombre", nombre);
		else 
		{
			 String nm =(String)session.getAttribute("nombre");
	         if (nm != null)
	        	 nombre = nm;
		}
		
		if (signo != null && signo!=session.getAttribute("signo"))
			session.setAttribute("signo", signo);
		else 
		{
			 Integer nm =(Integer) session.getAttribute("signo");
	         if (nm != null)
	        	 signo = nm;
		}
		
		if (aficciones != null && aficciones!=session.getAttribute("aficciones"))
			session.setAttribute("aficciones", aficciones);
		else 
		{
			 List<String> nm =(List<String>) session.getAttribute("aficciones");
	         if (nm != null)
	        	 aficciones = nm;
		}

		String errores = "";

		// Validaciones
		if (numEtapa == 1) {
			if (nombre == null || nombre.isBlank()) {
				errores = "Debes poner un nombre no vacío";
			} else if (nombre.length() < 3 || nombre.length() > 10) {
				errores = "La longitud del nombre debe estar entre 3 y 10 caracteres";
			}
		} else if (numEtapa == 2) {
			if (signo == null || signo==0) {
				errores = "Debes seleccionar un signo";
			}
		} else if (numEtapa == 3) {
			if (aficciones == null || aficciones.size() == 0) {
				errores = "Debes elegir al menos una afición, no seas soso/a 😄";
			}
		}

		if (!errores.isBlank()) 
		{
			modelo.addAttribute("nombre", nombre);
			modelo.addAttribute("signo", signo);
			modelo.addAttribute("aficciones", aficciones);
			return "etapa" + numEtapa;
		}
		
		numEtapa += opcion;
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("signo", signo);
		modelo.addAttribute("aficciones", aficciones);

		if (numEtapa == 4) {
			List<String> respuestas = new ArrayList<>();
			respuestas.add(nombre);
			respuestas.add(SIGNOS[signo]);
			respuestas.add(String.join(", ", aficciones));
			modelo.addAttribute("respuestas", respuestas);
		}

		return "etapa" + numEtapa;
	}

	@GetMapping("/")
	String getEtapa0(Model modelo, HttpSession session) 
	{
		//session.invalidate(); para cancelar toda la sesion
		modelo.addAttribute("nombre", (String)session.getAttribute("nombre"));
		return "etapa1";
	}
}
