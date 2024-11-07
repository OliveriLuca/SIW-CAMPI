package it.uniroma3.siw.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
public class Credentials {

	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credentials_generator")
	@SequenceGenerator(name = "credentials_generator", sequenceName = "credentials_seq", allocationSize = 1)
    private Long id;
	
	@Column(unique = true)
	@NotBlank(message = "Username non può essere vuoto")
	@Size(min = 4, max = 40, message = "Username deve avere tra 4 e 40 caratteri")
	private String username;
	
	@NotBlank(message = "Password non può essere vuota")
    @Size(min = 4, message = "Password deve avere almeno 4 caratteri")
	private String password;
	
	private String role;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
	
	public String getUsername() {
		return username;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

}