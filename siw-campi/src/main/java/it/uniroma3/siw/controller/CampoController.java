package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.CampoValidator;
import it.uniroma3.siw.model.Campo;
import it.uniroma3.siw.service.CampoService;
import jakarta.validation.Valid;


@Controller
public class CampoController {


	@Autowired 
	CampoService campoService;
	
	@Autowired
	CampoValidator campoValidator;


	@GetMapping("/admin/formNewCampo")
	public String formNewCampo(Model model) {
		model.addAttribute("campo", new Campo());
		return "/admin/formNewCampo.html";
	} 

	@PostMapping("/admin/campo")
	public String newCampo(@Valid @ModelAttribute("campo") Campo campo,  Model model,  BindingResult campoBindingResult) {
		
		this.campoValidator.validate(campo, campoBindingResult);

		if(! campoBindingResult.hasErrors()) {
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
	
	@GetMapping("/filtri")
	public String barraRicerca( Model model){
		model.addAttribute("tipo", new String());
		return "formTipoCampo.html";
	}
	
	@GetMapping("/cercaTipoCampo")
	public String cercaTipoCampo(@RequestParam("tipo")String tipo, Model model) {
		model.addAttribute("tipoCampi", this.campoService.findByTipo(tipo));
		return "tipoCampo.html";
	}




}
