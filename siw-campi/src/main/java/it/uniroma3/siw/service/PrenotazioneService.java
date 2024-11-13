package it.uniroma3.siw.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Campo;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PrenotazioneRepository;
import jakarta.transaction.Transactional;



@Service
public class PrenotazioneService {

	@Autowired 
	PrenotazioneRepository prenotazioneRepository;

	@Autowired 
	CredentialsService credentialsService;


	@Transactional
	public Prenotazione save(Prenotazione prenotazione) {
		return this.prenotazioneRepository.save(prenotazione);
	}

	public List<Prenotazione> findAll() {
		return this.prenotazioneRepository.findAll();
	}

	public Prenotazione findById(long id) {
		return this.prenotazioneRepository.findById(id);
	}

	public Optional<Prenotazione> findByData(LocalDate data) {
		return this.prenotazioneRepository.findByData(data);
	}

	public Optional<Prenotazione> findByOrario(LocalTime orario) {
		return this.prenotazioneRepository.findByOrario(orario);
	}

	public Optional<Prenotazione> findByCampo(Campo campo){
		return this.prenotazioneRepository.findByCampo(campo);
	}

	public List<Prenotazione> findByUser(User user){
		List<Prenotazione> res = new ArrayList<Prenotazione>();

		Iterable<Prenotazione> iterable = this.prenotazioneRepository.findAll();

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		user = credentials.getUser();

		for(Prenotazione pren : iterable) {
			if(pren.getUser().getId() == user.getId()) {
				res.add(pren);
			}	
		}
		return res;
	}


	public Long count() {
		return this.prenotazioneRepository.count();
	}

	public boolean existsByData(LocalDate data) {
		return this.prenotazioneRepository.existsByData(data);
	}

	public boolean existsByOrario(LocalTime orario) {
		return this.prenotazioneRepository.existsByOrario(orario);
	}

	public boolean existsByOrarioAndData(LocalTime orario, LocalDate data) {
		return this.prenotazioneRepository.existsByOrarioAndData(orario, data);
	}

	public boolean existsByCampo(Campo campo) {
		return this.prenotazioneRepository.existsByCampo(campo);
	}

	public boolean existsByOrarioAndDataAndCampo(LocalTime orario, LocalDate data, Campo campo) {
		return (this.prenotazioneRepository.existsByCampo(campo) && this.existsByOrarioAndData(orario, data)); 
	}


}
