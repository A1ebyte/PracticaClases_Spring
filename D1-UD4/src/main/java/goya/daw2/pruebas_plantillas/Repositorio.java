package goya.daw2.pruebas_plantillas;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repositorio extends CrudRepository<QuizBBDD,Long> {
	QuizBBDD findByNombre(String nombre);
}
