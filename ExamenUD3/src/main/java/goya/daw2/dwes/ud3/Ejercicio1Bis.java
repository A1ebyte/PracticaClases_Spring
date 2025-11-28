package goya.daw2.dwes.ud3;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class Ejercicio1Bis {
	static final String[] USUARIOS = { "Bob", "Calamardo", "Arenita" };

	@GetMapping("/Ejercicio1Bis")
	public String listaChatsBis(Model modelo) {
		modelo.addAttribute("usuarios", USUARIOS);
		modelo.addAttribute("cookie","Bis");
		return "chats";
	}

	@GetMapping("/chatBis")
	public String chatBis(@RequestParam(name = "usuario", required = false) String usuario,
			@RequestParam(name = "mensaje", required = false) String mensaje,
			@RequestParam(name="borrar",required = false) String borrar,
			@CookieValue(name="usuarioCookie", required = false) String usuarioCookie,
			@CookieValue(name="mensajesCookieBob", required = false) String mensajesCookieBob,
			@CookieValue(name="mensajesCookieCalamardo", required = false) String mensajesCookieCalamardo,
			@CookieValue(name="mensajesCookieArenita", required = false) String mensajesCookieArenita,
			Model modelo, HttpServletResponse response) {
		if(usuario!=null && !usuario.equals(usuarioCookie))
			response.addCookie(new Cookie("usuarioCookie", usuario));
		else 
			usuario=usuarioCookie;
		List<String> mensajes=new ArrayList<String>();
		switch (usuario) {
		case "Bob": {	
			if(mensajesCookieBob!=null)
			{
				String[] datos=URLDecoder.decode(mensajesCookieBob).split("/");
				for(String ele:datos) 
				{
					mensajes.add(ele);
				}
			}
			LocalDateTime hora = LocalDateTime.now();
			String horaFormato=hora.format(DateTimeFormatter.ofPattern("dd-MM-uu HH:mm"));
			if(mensaje!=null && !mensaje.isBlank()) 
			{
				mensajes.add(horaFormato+" - "+mensaje);			
			}
			System.out.println(mensajes.toString());
			modelo.addAttribute("hora",horaFormato);
			modelo.addAttribute("usuario", usuario);
			modelo.addAttribute("cookie","Bis");
			if(borrar!=null && borrar.equals("Borrar"))
				response.addCookie(new Cookie("mensajesCookieBob", ""));
			else {
				response.addCookie(new Cookie("mensajesCookieBob", URLEncoder.encode(String.join("/", mensajes),StandardCharsets.UTF_8)));
				modelo.addAttribute("listaMsj", mensajes.reversed());
			}
			return "chat";
		}
		case "Arenita": {	
			if(mensajesCookieArenita!=null)
			{
				String[] datos=URLDecoder.decode(mensajesCookieArenita).split("/");
				for(String ele:datos) 
				{
					mensajes.add(ele);
				}
			}
			LocalDateTime hora = LocalDateTime.now();
			String horaFormato=hora.format(DateTimeFormatter.ofPattern("dd-MM-uu HH:mm"));
			if(mensaje!=null && !mensaje.isBlank()) 
			{
				mensajes.add(horaFormato+" - "+mensaje);			
			}
			System.out.println(mensajes.toString());
			modelo.addAttribute("hora",horaFormato);
			modelo.addAttribute("usuario", usuario);
			modelo.addAttribute("cookie","Bis");
			if(borrar!=null && borrar.equals("Borrar"))
				response.addCookie(new Cookie("mensajesCookieArenita", ""));
			else {
				response.addCookie(new Cookie("mensajesCookieArenita", URLEncoder.encode(String.join("/", mensajes),StandardCharsets.UTF_8)));
				modelo.addAttribute("listaMsj", mensajes.reversed());
			}
			return "chat";
		}
		case "Calamardo": {	
			if(mensajesCookieCalamardo!=null)
			{
				String[] datos=URLDecoder.decode(mensajesCookieCalamardo).split("/");
				for(String ele:datos) 
				{
					mensajes.add(ele);
				}
			}
			LocalDateTime hora = LocalDateTime.now();
			String horaFormato=hora.format(DateTimeFormatter.ofPattern("dd-MM-uu HH:mm"));
			if(mensaje!=null && !mensaje.isBlank()) 
			{
				mensajes.add(horaFormato+" - "+mensaje);			
			}
			System.out.println(mensajes.toString());
			modelo.addAttribute("hora",horaFormato);
			modelo.addAttribute("usuario", usuario);
			modelo.addAttribute("cookie","Bis");
			if(borrar!=null && borrar.equals("Borrar"))
				response.addCookie(new Cookie("mensajesCookieCalamardo", ""));
			else {
				response.addCookie(new Cookie("mensajesCookieCalamardo", URLEncoder.encode(String.join("/", mensajes),StandardCharsets.UTF_8)));
				modelo.addAttribute("listaMsj", mensajes.reversed());
			}
			return "chat";
		}
		}
	return "chats";
	}
}
