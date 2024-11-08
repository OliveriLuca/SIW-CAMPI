package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Campo;
import it.uniroma3.siw.repository.CampoRepository;


@Controller
public class UserController {
	
   @Autowired
   CampoRepository campoRepository;
   	
   @GetMapping("/admin/cancellaCampo/{id}")
   public String cancellaCampo(@PathVariable("id") Long id, Model model) {
       Campo campoDaCancellare = this.campoRepository.findById(id).orElse(null);
       if (campoDaCancellare != null) {
           this.campoRepository.delete(campoDaCancellare);
       }
       return "redirect:/admin/campi"; // Reindirizza all’elenco dei campi aggiornato
   }

   @GetMapping("/admin/showFormModificaCampo/{id}")
   public String showModificaCampo(@PathVariable("id") Long id, Model model) {
       Campo campoDaModificare = this.campoRepository.findById(id).orElse(null);
       model.addAttribute("campoDaModificare", campoDaModificare);
       return "/admin/formModificaCampo.html"; 
   }

   @PostMapping("/admin/formModificaCampo/{id}")
   public String formModificaCampo(@PathVariable("id") Long id, @ModelAttribute Campo nuovoCampo, Model model) {
       Campo campo = this.campoRepository.findById(id).orElse(null);
       
       if (campo != null) {
           if (!nuovoCampo.getNome().equals(campo.getNome())) {
               campo.setNome(nuovoCampo.getNome());
           }

           if (nuovoCampo.getCosto() != campo.getCosto()) {
               campo.setCosto(nuovoCampo.getCosto());
           }

           if (!nuovoCampo.getTipo().equals(campo.getTipo())) {
               campo.setTipo(nuovoCampo.getTipo());
           }

           if (nuovoCampo.getNumero_giocatori() != campo.getNumero_giocatori()) {
               campo.setNumero_giocatori(nuovoCampo.getNumero_giocatori()); // Correzione per l’attributo numero_giocatori
           }

           this.campoRepository.save(campo);
       }

       return "redirect:/admin/campi"; // Reindirizza all’elenco dei campi aggiornato
   }

   
   
}