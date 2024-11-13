package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PrenotazioneService;


@Controller
public class PrenotazioneController {


	@Autowired 
	PrenotazioneService prenotazioneService;

	@Autowired 
	CredentialsService credentialsService;


	@GetMapping("/leMiePrenotazioni")
	public String getPrenotazioniUtente(Model model) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();

		model.addAttribute("prenotazioni", this.prenotazioneService.findByUser(user));

		return "leMiePrenotazioni.html";
	}


	@GetMapping("/admin/prenotazioni")
	public String getPrenotazioni(Model model) {
		model.addAttribute("prenotazioni", this.prenotazioneService.findAll());
		return "/admin/prenotazioni.html";
	}


	@GetMapping("/admin/dettagliPrenotazione/{id}")
	public String getDettagliPrenotazione(@PathVariable("id") Long id, Model model) {

		Prenotazione prenotazioneDaVisualizzare = this.prenotazioneService.findById(id);

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		String userRole = credentials.getRole();

		if(userRole.equals("ADMIN")) {
			model.addAttribute("prenotazione", prenotazioneDaVisualizzare);
			return "/admin/dettagliPrenotazione.html";
		}

		return "accessoNegato.html";
	}




}
