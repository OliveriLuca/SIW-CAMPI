package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Campo;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PrenotazioneRepository;
import jakarta.transaction.Transactional;



@Service
public class PrenotazioneService {

	@Autowired 
	protected PrenotazioneRepository prenotazioneRepository;

	
	@Transactional
	public Prenotazione save(Prenotazione prenotazione) {
		return this.prenotazioneRepository.save(prenotazione);
	}

	public Optional<Prenotazione> findById(long id) {
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
		for(Prenotazione pren : iterable)
			res.add(pren);
		return res;
	}
	
	public Long count() {
		return this.prenotazioneRepository.count();
	}
	
	
    
}
