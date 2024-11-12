package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Campo;
import it.uniroma3.siw.service.CampoService;
import jakarta.validation.Valid;


@Controller
public class CampoController {


	@Autowired 
	CampoService campoService;


	@GetMapping("/admin/formNewCampo")
	public String formNewCampo(Model model) {
		model.addAttribute("campo", new Campo());
		return "/admin/formNewCampo.html";
	} 

	@PostMapping("/admin/campo")
	public String newCampo(@Valid @ModelAttribute("campo") Campo campo,  Model model) {
		if(! this.campoService.existsByNome(campo.getNome())) {
			this.campoService.save(campo); 
			model.addAttribute("campo", campo);
			return "/admin/campoInserito.html";
		}

		return "/admin/formNewCampo.html";

	}

	@GetMapping("/campo/{id}")
	public String getCampo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("campo", this.campoService.findById(id));
		return "campo.html"; 
	}

	@GetMapping("/admin/campi")
	public String getCampi(Model model) {
		model.addAttribute("campi", this.campoService.findAll());
		return "/admin/campi.html";
	}

	@GetMapping("/formSearchCampi")
	public String formSearchCampi(Model model) {
		model.addAttribute("campi", this.campoService.findAll());
		return "formSearchCampi.html";
	}
	
	



}
