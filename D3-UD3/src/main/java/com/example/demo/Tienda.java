package com.example.demo;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Tienda {
	public RepositorioStock repo = new RepositorioStock();

	@GetMapping("/")
	public String inicioGet(Model model, HttpSession session,
			@RequestParam(name = "correcto", required = false) String correcto) {
		String name = (String) session.getAttribute("usuario");
		model.addAttribute("nombre", name);
		model.addAttribute("correcto", correcto);
		return "Inicio";
	}

	@PostMapping("/")
	public String inicioPost(Model model, HttpSession session,
			@RequestParam(name = "nombre", required = false) String nombre) {
		if (nombre == null || nombre.trim().equals(""))
			return "Inicio";
		session.setAttribute("usuario", nombre);
		return "redirect:/Tienda";
	}

	@GetMapping("/Tienda")
	public String tiendaGet(Model model, HttpSession session) {
		if (session.getAttribute("usuario") == null || ((String) session.getAttribute("usuario")).trim().equals("")) {
			return "redirect:/";
		}

		float precioProvicional = 0;
		HashMap<String, Integer> cantidad = (HashMap<String, Integer>) session.getAttribute("cantidades");
		if (cantidad == null) {
			cantidad = new HashMap<>();
			for (String nombre : repo.getAll().keySet())
				cantidad.put(nombre, 0);
		} else {
			for (String nombre : repo.getAll().keySet()) {
				Integer valor = (Integer) ((HashMap<String, Integer>) session.getAttribute("cantidades")).get(nombre);
				if (valor > repo.getOne(nombre).getCantidadTotal())
					valor = repo.getOne(nombre).getCantidadTotal();
				if (valor < 0)
					valor = 0;
				precioProvicional += (valor * repo.getOne(nombre).getPrecio());
				cantidad.replace(nombre, valor);
			}
		}
		session.setAttribute("cantidades", cantidad);
		model.addAttribute("precioProv", precioProvicional);
		model.addAttribute("usuario", session.getAttribute("usuario"));
		model.addAttribute("carrito", repo.getAll());
		model.addAttribute("cantidades", cantidad);
		return "Tienda";
	}

	// usando array
	@PostMapping("/Tienda")
	public String tiendaPost(Model model, HttpSession session,
			@RequestParam(name = "cantidades", required = false) Integer[] cantidades,
			@RequestParam(name = "accion", required = false) String accion) {
		HashMap<String, Integer> cantidad = (HashMap<String, Integer>) session.getAttribute("cantidades");
		float precioProvicional = 0;
		// System.out.println(cantidad);
		if (accion.equals("actualizar")) {
			int indx = 0;
			for (String nombre : repo.getAll().keySet()) {
				Integer valor = cantidades[indx] == null ? 0 : cantidades[indx];
				if (valor > repo.getOne(nombre).getCantidadTotal())
					valor = repo.getOne(nombre).getCantidadTotal();
				if (valor < 0)
					valor = 0;
				cantidad.put(nombre, valor);
				precioProvicional += (valor * repo.getOne(nombre).getPrecio());
				indx++;
			}
		}
		if (accion.equals("finalizar")) {
			boolean exito = true;
			int indx = 0;
			for (String nombre : repo.getAll().keySet()) {
				Integer valor = cantidades[indx] == null ? 0 : cantidades[indx];
				if (valor > repo.getOne(nombre).getCantidadTotal()) {
					valor = repo.getOne(nombre).getCantidadTotal();
					model.addAttribute("errorMayor",
							"La cantidad que se pide es mayor a la cantidad de stock disponible, producto: " + nombre);
					exito = false;
				}
				if (valor < 0) {
					model.addAttribute("errorMenor",
							"La cantidad que se pide es menor a 0, no aceptamos devoluciones, producto: " + nombre);
					valor = 0;
					exito = false;
				}
				cantidad.put(nombre, valor);
				precioProvicional += (valor * repo.getOne(nombre).getPrecio());
				indx++;
			}
			if (exito) {
				for (String nombre : repo.getAll().keySet()) {
					producto prod = new producto(repo.getOne(nombre).getCantidadTotal() - cantidad.get(nombre),
							repo.getOne(nombre).getPrecio());
					repo.modify(nombre, prod);
					session.removeAttribute("cantidades");
				}
				return "redirect:/?correcto=Compra hecha correctamente, has gastado: "+precioProvicional;
			}
		}
		session.setAttribute("cantidades", cantidad);
		model.addAttribute("precioProv", precioProvicional);
		model.addAttribute("cantidades", cantidad);
		model.addAttribute("usuario", session.getAttribute("usuario"));
		model.addAttribute("carrito", repo.getAll());
		return "Tienda";
	}

	@GetMapping("/Admin")
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
	}
}
