package it.uniroma3.siw.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.User;


public interface UserRepository extends CrudRepository<User, Long> {

	public User findByEmail(String email);

	public boolean existsByEmail(String email);

	public List<User> findAll();
	
}
