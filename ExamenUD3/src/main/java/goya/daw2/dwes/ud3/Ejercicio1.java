package goya.daw2.dwes.ud3;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Ejercicio1 {
	static final String[] USUARIOS = { "Bob", "Calamardo", "Arenita" };

	@GetMapping("/Ejercicio1")
	public String listaChats(Model modelo,HttpSession session) {
		modelo.addAttribute("usuarios", USUARIOS);
		modelo.addAttribute("cookie","");
		return "chats";
	}

	@GetMapping("/chat")
	public String chat(@RequestParam(name = "usuario", required = false) String usuario,
			@RequestParam(name = "mensaje", required = false) String mensaje,
			@RequestParam(name="borrar",required = false) String borrar,
			Model modelo, HttpSession session) {
		if(usuario!=null && !usuario.equals((String)session.getAttribute("userActual")))
			session.setAttribute("userActual", usuario);
		else 
		{
			String user=(String) session.getAttribute("userActual");
			if(user!=null)
				usuario=user;
		}
		ArrayList<String> mensajes=session.getAttribute(usuario+"mensajes")==null?
				new ArrayList<>():(ArrayList<String>)session.getAttribute(usuario+"mensajes");
		LocalDateTime hora = LocalDateTime.now();
		String horaFormato=hora.format(DateTimeFormatter.ofPattern("dd-MM-uu HH:mm"));
		if(mensaje!=null && !mensaje.isBlank()) 
		{
			mensajes.add(horaFormato+" - "+mensaje);			
		}
		modelo.addAttribute("hora",horaFormato);
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("cookie","");
		if(borrar!=null && borrar.equals("Borrar"))
			session.removeAttribute(usuario+"mensajes");
		else {
			session.setAttribute(usuario+"mensajes", mensajes);
			modelo.addAttribute("listaMsj", mensajes.reversed());
		}
		return "chat";
	}

}
