package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Pelicula;
import com.example.demo.repositories.PeliculaRepository;

@Service
public class PeliculaService {
	
	private PeliculaRepository peliculaRepo;

	public PeliculaService(PeliculaRepository peliculaRepo) {
		this.peliculaRepo = peliculaRepo;
	}
	
	public List<Pelicula> findAll(){
		return peliculaRepo.findAll();
	}
	
	public Optional<Pelicula> findById(Long id) {
		return peliculaRepo.findById(id);
	}
	
	public Pelicula save(Pelicula pelicula) {
	    return peliculaRepo.save(pelicula); 
	}

    public void delete(Pelicula pelicula) {
        peliculaRepo.delete(pelicula);
    }
}