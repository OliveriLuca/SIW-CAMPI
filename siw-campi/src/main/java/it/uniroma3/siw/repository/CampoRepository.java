package it.uniroma3.siw.repository;


import java.util.List;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Campo;


/*  le operazioni CRUD della classe campo vengono implementate qua  */


public interface CampoRepository extends CrudRepository<Campo, Long>{

	public Campo findById(long id); 
	
	public List<Campo> findByCosto(int c);
	
	public List<Campo> findByTipo(String s);
	
	public List<Campo> findAll();
	
	public boolean existsByNome(String nome);
	
}
