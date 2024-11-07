package it.uniroma3.siw.repository;


import java.util.List;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Campo;


/*  le operazioni CRUD della classe campo vengono implementate qua  */


public interface CampoRepository extends CrudRepository<Campo, Long>{

	public Campo findById(long id); 
	
	public Campo findByNumeroGiocatori(int n);
	
	public Campo findByCosto(int c);
	
	public Campo findByTipo(String s);
	
	public List<Campo> findAll();
	
}
