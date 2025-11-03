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
			@CookieValue(name = "nombre", required = false) String nombreCookie,
			@CookieValue(name = "signo", required = false, defaultValue="0") String signoCookie,
			@CookieValue(name = "aficciones", required = false) String aficcionesCookie, HttpServletResponse response,
			Model modelo) {

		modelo.addAttribute("signos", SIGNOS);
		modelo.addAttribute("aficcionesList", AFICCIONES);

		if (numEtapa == null)
			return "etapa1";

		if (nombre == null && nombreCookie != null)
			nombre = nombreCookie;
		if (signo == null && signoCookie != null)
			signo = Integer.parseInt(signoCookie);
		if (aficciones == null && aficcionesCookie != null)
			aficciones = Arrays.asList(aficcionesCookie.split("-"));

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
			return "etapa" + numEtapa;
		
		// Guardar cookies
		if (nombre != null && nombre!=nombreCookie)
			response.addCookie(new Cookie("nombre", nombre));
		if (signo != null && signo!=Integer.parseInt(signoCookie))
			response.addCookie(new Cookie("signo", signo+""));
		if (aficciones != null && aficciones!=Arrays.asList(aficcionesCookie.split("-"))) {
			String joined = String.join("-", aficciones);
			response.addCookie(new Cookie("aficciones", joined));
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
	String getEtapa0(Model modelo, 
			@CookieValue(name = "nombre", required = false) String nombreCookie) 
	{
		modelo.addAttribute("nombre", nombreCookie);
		return "etapa1";
	}
}
