package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pelicula;
@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long>{

}
