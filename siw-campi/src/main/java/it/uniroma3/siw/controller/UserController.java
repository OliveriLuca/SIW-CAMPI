package it.uniroma3.siw.controller;


import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.uniroma3.siw.model.Campo;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CampoRepository;
import it.uniroma3.siw.repository.PrenotazioneRepository;
import it.uniroma3.siw.service.CampoService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;



@Controller
public class UserController {

	@Autowired
	PrenotazioneRepository prenotazioneRepository;	

	@Autowired
	CredentialsService credentialsService;	

	@Autowired
	UserService userService;	

	@Autowired
	CampoRepository campoRepository;
	
	@Autowired
	CampoService campoService;


	@GetMapping("/admin/cancellaCampo/{id}")
	public String cancellaCampo(@PathVariable("id") Long id, Model model) {
		Campo campoDaCancellare = this.campoRepository.findById(id).get();

		/*sono presenti prenotazioni per questo campo?*/
		if (this.prenotazioneRepository.existsByCampo(campoDaCancellare)) {
			return "/admin/eliminazioneNegata.html";
		}

		/*si può cancellare*/
		model.addAttribute("campoDaCancellare", campoDaCancellare);
		return "/admin/cancellaCampoConferma.html"; 
	}



	@PostMapping("/admin/cancellaCampoConferma/{id}")
	public String cancellaCampoConferma(@PathVariable("id") Long id, Model model) {
		Campo campo = this.campoRepository.findById(id).get();
		model.addAttribute("campoDaCancellare", campo);
		this.campoRepository.delete(campo);
		return "/admin/campoCancellato.html";
	}


	@GetMapping("/admin/showFormModificaCampo/{id}")
	public String showModificaCampo(@PathVariable("id") Long id, Model model) {
		Campo campoDaModificare = this.campoRepository.findById(id).orElse(null);
		model.addAttribute("campoDaModificare", campoDaModificare);
		return "/admin/formModificaCampo.html"; 
	}


	@PostMapping("/admin/formModificaCampo/{id}")
	public String formModificaCampo(@PathVariable("id") Long id,@ModelAttribute Campo nuovoCampo,Model model) {
		Campo campo = this.campoRepository.findById(id).orElse(null);
		
		if (campo != null) {
			if (!nuovoCampo.getNome().equals(campo.getNome()) && !this.campoService.existsByNome(nuovoCampo.getNome())) {
				campo.setNome(nuovoCampo.getNome());
			}
			if (nuovoCampo.getCosto() != campo.getCosto()) {
				campo.setCosto(nuovoCampo.getCosto());
			}
			if (!nuovoCampo.getTipo().equals(campo.getTipo())) {
				campo.setTipo(nuovoCampo.getTipo());
			}
			this.campoRepository.save(campo);
		}
		return "redirect:/admin/campi"; // Reindirizza all’elenco dei campi aggiornato
	}


	@GetMapping("/showFormNewPrenotazione/{id}")
	public String showFormPrenotazione(@PathVariable("id") Long id, Model model) {
		Campo campoDaPrenotare = this.campoRepository.findById(id).orElse(null);
		model.addAttribute("campoDaPrenotare", campoDaPrenotare);
		model.addAttribute("prenotazione", new Prenotazione());
		return "formNewPrenotazione.html";  
	}


	@PostMapping("/formNewPrenotazione/{id}")
	public String formPrenotaCampo(@PathVariable("id") Long id, @ModelAttribute Prenotazione prenotazione, Model model,
			@RequestParam("orario")LocalTime orario, @RequestParam("data")LocalDate data) {

		Campo campo = this.campoRepository.findById(id).orElse(null);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();

		if(campo != null &&  !this.prenotazioneRepository.existsByOrarioAndDataAndCampo(orario, data, campo)){

			prenotazione.setCampo(campo);
			prenotazione.setUser(user);
			prenotazione.setOrario(orario);
			prenotazione.setData(data);		
			this.prenotazioneRepository.save(prenotazione);

			model.addAttribute(prenotazione);
			return "prenotazioneSuccesso.html";
		}

		model.addAttribute(campo);
		return "prenotazioneNegata.html";
	}


	@GetMapping("/cancellaPrenotazione/{id}")
	public String cancellaPrenotazione(@PathVariable("id") Long id, Model model) {
		Prenotazione prenotazioneDaCancellare = this.prenotazioneRepository.findById(id).orElse(null);

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();

		if (prenotazioneDaCancellare != null && prenotazioneDaCancellare.getUser().equals(user)) {
			this.prenotazioneRepository.delete(prenotazioneDaCancellare);
			return "prenotazioneCancellata.html";
		}
		return "accessoNegato.html"; 
	}


	@GetMapping("/admin/registrazioni")
	public String getRegistrazioni(Model model) {
		model.addAttribute("registrazioni", this.userService.getAllUsers());
		model.addAttribute("registrati", this.userService.count());
		return "/admin/registrazioni.html";
	}





}