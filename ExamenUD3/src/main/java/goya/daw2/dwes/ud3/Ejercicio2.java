package goya.daw2.dwes.ud3;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Ejercicio2   {
	static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	static Map<String,String> userPasswords = new HashMap<String,String>();

	// Constructor:
	public Ejercicio2() {
		// crea un mapa con claves user1, user2 ... y sus valores los hashes correlativos
		// del siguiente array	
		final String[] HASHES = {
				"$2a$10$nR7E7eQbxXYbi/g9gPPr3.zkbh3dgdyMFHrLpJ58G3VcoEnyi2wWS",
				"$2a$10$IWn/W1pLUgHDxeKNIAuJqO5oS4adcN0fatFPudLst5yktYtyxpBzG",
				"$2a$10$w1SvJkFeE00t44a9vC9g1.OTu8m0zO9UOBeC94hI1HlrtuP5J9GQK",
				"$2a$10$wUrSYhItqPup8j75V7b20O1EU/NcS3k9X4vwCWBCmb2ffvmjbcCW.",
				"$2a$10$uqmjFIuDVuogWm5XvUXdGu3vHzefOmrnBCV9NXK.wURe5w.5Iu4xi",
				"$2a$10$iyOvUqRWzjrqyWzVTJgWkO/YOSynBmGfgMtp1rgq1r.8KbAiKA/Si"                      
		};
		int contador = 1;
		for (String hash : HASHES) {
			userPasswords.put("user" + contador, hash);
			contador++;
		}
	}
	
	@GetMapping("/Ejercicio2")
	public String inicio(Model modelo, HttpSession session) 
	{
		Ejercicio2 ej2=new Ejercicio2();
		if(session.getAttribute("user")!=null)
			modelo.addAttribute("nombre",(String)session.getAttribute("user"));
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(name="nombre",required=false)String user,
			@RequestParam(name="contraseña",required=false)String contra,
			@RequestParam(name="logout", required = false)String logout,
			Model modelo, HttpSession session) 
	{
		String password=userPasswords.getOrDefault(user, "No");
		if(password.equals("No") || encoder.matches(contra,password)==false) {
			System.out.println(password.equals("No"));
			modelo.addAttribute("errores","Usuario y/o contraseña invalidos");
			return"login";
		}
		if(logout!=null&&logout.equals("Cerrar sesión")) {
			session.invalidate();
			return "redirect:/Ejercicio2";
		}
		modelo.addAttribute("nombre",user);
		session.setAttribute("user", user);
		return"login";
	}
	
	@GetMapping("/secreto")
	public String secreto(HttpSession session) 
	{
		if(session.getAttribute("user")==null)
			return "redirect:/Ejercicio2";
		return "secreto";
	}
	
	@GetMapping("/registro")
	public String registro() {
		return "registro";
	}
	
	
}
