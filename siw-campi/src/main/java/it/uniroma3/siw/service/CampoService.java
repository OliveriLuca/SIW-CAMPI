package it.uniroma3.siw.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Campo;
import it.uniroma3.siw.repository.CampoRepository;



@Service
public class CampoService {

	@Autowired 
	protected CampoRepository campoRepository; 


	public Campo findById(long id) {
		return this.campoRepository.findById(id);
	}

	public Campo findByNumeroGiocatori(int n) {
		return this.campoRepository.findByNumeroGiocatori(n);
	}

	public Campo findByCosto(int c) {
		return this.campoRepository.findByCosto(c);
	}

	public Campo findByTipo(String s) {
		return this.campoRepository.findByTipo(s);
	}

	public Campo save(Campo campo) {
		return this.campoRepository.save(campo);
	}
	
	public List<Campo> findAll(){
		return this.campoRepository.findAll(); 
	}
	
	

}
