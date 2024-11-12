package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Campo;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;


public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long>{

	public Optional<Prenotazione> findById(Long id);
	
	public List<Prenotazione> findAll();

	public Optional<Prenotazione> findByData(LocalDate data);

	public Optional<Prenotazione> findByOrario(LocalTime orario);

	public Optional<Prenotazione> findByCampo(Campo campo);

	public List<Prenotazione> findByUser(User user);

	public boolean existsByOrario(LocalTime orario);

	public boolean existsByData(LocalDate data);
	
	public boolean existsByOrarioAndData(LocalTime orario, LocalDate data);
	
	public boolean existsByCampo(Campo campo);
	
	public boolean existsByOrarioAndDataAndCampo(LocalTime orario, LocalDate data, Campo campo);

}