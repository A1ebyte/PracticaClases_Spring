package com.example.demo;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorWeb 
{
	
	@GetMapping("/saludo")
	public String greetingForm(Model model) 
	{
		model.addAttribute("saludo", new Saludo());
		return "saludo";
	}

	@PostMapping("/saludo")
	public String greetingSubmit1(@ModelAttribute Saludo saludo, Model model) 
	{
		model.addAttribute("saludo", saludo);
		model.addAttribute("encodeContenido",(URLEncoder.encode(saludo.getContenido(), StandardCharsets.UTF_8)));
		model.addAttribute("encodeID",(URLEncoder.encode(saludo.getId()+"", StandardCharsets.UTF_8)));
		return "resultado";
	}
	
	@GetMapping("/respuesta")
	public String greetingSubmit2(@ModelAttribute Saludo saludo, Model model) 
	{
		model.addAttribute("saludo", saludo);
		model.addAttribute("encodeContenido",(URLEncoder.encode(saludo.getContenido(), StandardCharsets.UTF_8)));
		model.addAttribute("encodeID",(URLEncoder.encode(saludo.getId()+"", StandardCharsets.UTF_8)));
		return "resultado";
	}
}