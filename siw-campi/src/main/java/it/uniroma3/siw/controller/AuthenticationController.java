package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PrenotazioneService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;


@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

    @Autowired
	private UserService userService;
    
    @Autowired
    UserValidator userValidator;
    
    @Autowired
    CredentialsValidator credentialsValidator;
    
    @Autowired 
    PrenotazioneService prenotazioneService;
    
    
    private final static String EMAIL_ADMIN = "luc.oliveri@stud.uniroma3.it";
	 
    
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser.html";
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin.html";
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "formLogin.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/index.html";
			}
		}
        return "formLogin.html";
	}
		
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	
    	User utente = credentials.getUser();
    	
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/index.html";
        }
   
    	model.addAttribute("utente", utente);
        return "homePage.html";
    }

	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);
		
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            String email = user.getEmail();
            System.out.print(email);
            if(email.equals(EMAIL_ADMIN)) {
            	credentials.setRole(Credentials.ADMIN_ROLE);
            }else {
            	credentials.setRole(Credentials.DEFAULT_ROLE);
            }
        	userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "registrationSuccessful.html";
        }
        // se ci sono errori torno sulla register e mi devo sempre aspettare degli oggetti
        model.addAttribute("user", user);
        model.addAttribute("credentials", credentials);
        return "formRegisterUser.html";
    }
	
	
}
