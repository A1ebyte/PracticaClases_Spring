package goya.daw2.pruebas_plantillas;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class QuizBBDD {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Date fecha;
	
	@Enumerated(EnumType.STRING)
	private Nivel tipo;
	
	private Integer puntuacion;
	
	private String nombre;
	
	public QuizBBDD(Date fecha, Nivel tipo, Integer puntuacion, String nombre) {
		this.fecha = fecha;
		this.tipo = tipo;
		this.puntuacion = puntuacion;
		this.nombre = nombre;
	}
	
	public QuizBBDD() {

	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Nivel getTipo() {
		return tipo;
	}

	public void setTipo(Nivel tipo) {
		this.tipo = tipo;
	}

	public Integer getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}