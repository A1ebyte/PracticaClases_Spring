package com.example.demo;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Tienda {

	//@Autowired
	private Repositorio repo;
	//otra manera de hacer lo de arriba
	public Tienda(Repositorio repos) 
	{
		this.repo=repos;
	}

	@GetMapping("/")
	public String inicioGet(Model model, HttpSession session,
			@RequestParam(name = "correcto", required = false) String correcto) {
		Object name =session.getAttribute("usuario");
		if (name!=null) 
		{
			return "redirect:/Tienda";
		}
		model.addAttribute("nombre", (String)name);
		model.addAttribute("correcto", correcto);
		return "Inicio";
	}

	@PostMapping("/")
	public String inicioPost(Model model, HttpSession session,
			@RequestParam(name = "nombre", required = false) String nombre) {
		if (nombre == null || nombre.isBlank())
			return "Inicio";
		session.setAttribute("usuario", nombre);
		return "redirect:/Tienda";
	}

	@GetMapping("/Tienda")
	public String tiendaGet(Model model, HttpSession session) {
		if (session.getAttribute("usuario") == null || ((String) session.getAttribute("usuario")).isBlank()) {
			return "redirect:/";
		}

		float precioProvicional = 0;
		HashMap<String, Integer> cantidad = (HashMap<String, Integer>) session.getAttribute("cantidades");

		if (cantidad == null) {
			cantidad = new HashMap<>();
			for (ObjetoBBDD item : repo.findAll())
				cantidad.put(item.getNombre(), 0);
		} else {
			for (ObjetoBBDD obj : repo.findAll()) {
				Integer valor = cantidad.getOrDefault(obj.getNombre(), 0);
				valor = valor <= 0 ? 0 : valor >= obj.getStock()? obj.getStock():valor;
				precioProvicional += (valor * obj.getPrecio());
				cantidad.put(obj.getNombre(), valor);
			}
		}
		session.setAttribute("cantidades", cantidad);
		model.addAttribute("cantidades", cantidad);
		model.addAttribute("precioProv", precioProvicional);
		model.addAttribute("usuario", session.getAttribute("usuario"));
		model.addAttribute("carrito", repo.findAll());
		return "Tienda";
	}

	@PostMapping("/Tienda")
	public String tiendaPost(Model model, HttpSession session,
			@RequestParam HashMap<String, String> cantidades,
			@RequestParam(name = "accion", required = false) String accion) {
		HashMap<String, Integer> cantidad = (HashMap<String, Integer>) session.getAttribute("cantidades");
		float precioProvicional = 0;
		// System.out.println(cantidad);
		if (accion.equals("actualizar")) {
			for (ObjetoBBDD obj : repo.findAll()) {
				Integer valor = Integer.parseInt(cantidades.getOrDefault("cantidades_"+obj.getNombre(),"0"));
				valor = valor <= 0 ? 0 : valor >= obj.getStock()? obj.getStock():valor;
				cantidad.put(obj.getNombre(), valor);
				precioProvicional += (valor * obj.getPrecio());
			}
		}
		if (accion.equals("finalizar")) {
			boolean exito = true;
			for (ObjetoBBDD obj : repo.findAll()) {
				Integer valor = Integer.parseInt(cantidades.getOrDefault("cantidades_"+obj.getNombre(),"0"));
				if (valor > obj.getStock()) {
					valor = obj.getStock();
					model.addAttribute("errorMayor",
							"La cantidad que se pide es mayor a la cantidad de stock disponible, producto: " + obj.getNombre());
					exito = false;
				}
				if (valor < 0) {
					model.addAttribute("errorMenor",
							"La cantidad que se pide es menor a 0, no aceptamos devoluciones, producto: " + obj.getNombre());
					valor = 0;
					exito = false;
				}
				cantidad.put(obj.getNombre(), valor);
				precioProvicional += (valor * obj.getPrecio());
			}
			if (exito) {
				for (ObjetoBBDD obj : repo.findAll()) {
					obj.setStock(obj.getStock()-cantidad.getOrDefault(obj.getNombre(), 0));
					repo.save(obj);
				}
				session.removeAttribute("cantidades");
				session.removeAttribute("usuario");
				return "redirect:/?correcto=Compra hecha correctamente, has gastado: " + precioProvicional;
			}
		}
		session.setAttribute("cantidades", cantidad);
		model.addAttribute("precioProv", precioProvicional);
		model.addAttribute("cantidades", cantidad);
		model.addAttribute("usuario", session.getAttribute("usuario"));
		model.addAttribute("carrito", repo.findAll());
		return "Tienda";
	}

	/*@GetMapping("/Admin")
	public String adminGet(Model model) {
		model.addAttribute("carrito", repo.getAll());
		return "Admin";
	}

	// usando hashmap
	@PostMapping("/Admin")
	public String adminPost(Model model, @RequestParam HashMap<String, String> nums,
			@RequestParam(name = "accion", required = false) String accion) {
		if (accion != null && accion.equals("Tienda")) {
			return "redirect:/";
		}
		/*
		 * for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
		 * System.out.println(entry.getKey() + " → " + entry.getValue()); }
		 *//*
		for (String nombre : repo.getAll().keySet()) {
			String key = "cantidad_" + nombre;
			Integer valor = nums.get(key) == null || nums.get(key).trim().equals("") ? 0
					: Integer.parseInt(nums.get(key));
			System.out.println(valor);
			if (valor + repo.getOne(nombre).getCantidadTotal() <= 0)
				valor = repo.getOne(nombre).getCantidadTotal() * -1;
			valor += repo.getOne(nombre).getCantidadTotal();
			producto prod = new producto(valor, repo.getOne(nombre).getPrecio());
			repo.modify(nombre, prod);
		}
		model.addAttribute("carrito", repo.getAll());
		return "Admin";
	}*/
}
