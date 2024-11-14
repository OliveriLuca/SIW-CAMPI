package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Campo;
import it.uniroma3.siw.repository.CampoRepository;


@Component
public class CampoValidator implements Validator {

	@Autowired
	private CampoRepository campoRepository;

	
	public void validate(Object o, Errors errors) {
		Campo campo = (Campo)o;
		if (campo.getNome()!=null 
				&& campoRepository.existsByNome(campo.getNome())){
			errors.reject("campo.duplicate");
		}
	}
	

	public boolean supports(Class<?> aClass) {
		return Campo.class.equals(aClass);
	}
	
	
}
