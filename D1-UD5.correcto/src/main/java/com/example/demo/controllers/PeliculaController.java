package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pelicula;
import com.example.demo.services.PeliculaService;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
	
    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }
	
    @GetMapping
    public List<Pelicula> listAll(){
        return peliculaService.findAll();	
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<?> listOne(@PathVariable(name = "id") Long id){
    	Optional<Pelicula> peliculaOpt = peliculaService.findById(id);
    	if(peliculaOpt.isPresent()) return ResponseEntity.ok(peliculaOpt.get());
    	return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public Pelicula createOne(@RequestBody Pelicula pelicula) {
        return peliculaService.save(pelicula); // Ahora sí funciona el return
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name="id")Long id) {
    	Optional<Pelicula> peliculaOpt = peliculaService.findById(id);
    	if(peliculaOpt.isPresent()) peliculaService.delete(peliculaOpt.get());
    	
    }
}