package goya.daw2.pruebas_plantillas;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import jakarta.servlet.http.HttpSession;

@Controller
public class FormsController {

	@GetMapping("/")
	String getInicio(Model modelo, HttpSession session) 
	{
		if(session.getAttribute("user")==null || session.getAttribute("password")==null) 
		{
			return "redirect:/login";			
		}
		return "redirect:/pagina1";
	}
	
	@GetMapping("/login")
	String getLogin(Model modelo, HttpSession session,
			@RequestParam (name="user", required = false) String user,
			@RequestParam (name="password", required = false) String password,
			@RequestParam (name="logout", required = false) boolean logout) 
	{	
		if(logout==true) 
		{
			session.invalidate();
			return "redirect:/";
		}
		if(user!=null && !user.equals(session.getAttribute("user")))
			session.setAttribute("user",user);
		else 
		{
			 String us =(String)session.getAttribute("user");
	         if (us != null)
	        	 user = us;
		}
		if(password!=null && !password.equals(session.getAttribute("password")))
			session.setAttribute("password",password);
		else 
		{
			 String psw =(String)session.getAttribute("password");
	         if (psw != null)
	        	 password = psw;
		}
		if(user==null || user.equals("") || password==null || password.equals("")) 
		{
			modelo.addAttribute("error");
			session.setAttribute("password","");
			session.setAttribute("user","");
			return "Login";			
		}
		return "redirect:/pagina1";
	}
	
	@GetMapping("/pagina1")
	String getPagina1(Model modelo, HttpSession session) 
	{
		if(session.getAttribute("user")==null || session.getAttribute("password")==null) 
		{
			return "redirect:/login";			
		}
		modelo.addAttribute("siguiente",true);
		modelo.addAttribute("user",(String)session.getAttribute("user"));
		return "Bienvenido";
	}
	
	@GetMapping("/pagina2")
	String getPagina2(Model modelo, HttpSession session)
	{
		if(session.getAttribute("user")==null || session.getAttribute("password")==null) 
		{
			return "redirect:/login";			
		}
		modelo.addAttribute("anterior",true);
		modelo.addAttribute("user",(String)session.getAttribute("user"));
		return "Bienvenido";
	}
}
