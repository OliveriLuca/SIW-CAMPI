package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;



/* Classe/entità che ha due chiavi primarie: 
 * - un Id_utente (univoco), ossia l'identificativo dell'utente che effettua la prenotazione e 
 * - un Id_campo(univoco), ossia l'identificativo del campo prenotato. 
 * 
 * Ha inoltre come campi il giorno e il relativo orario di prenotazione. 
 * 
 * Ogni prenotazione è quindi relativa a un deteminato utente e al campo scelto. 
 * Mentre ovviamente un campo può avere una o più prenotazioni, 
 * come un utente che può effettuare una o più prenotazioni. 
 * */



@Entity
public class Prenotazione {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campo_generator")
	@SequenceGenerator(name = "campo_generator", sequenceName = "campo_seq", allocationSize = 1)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_campo", nullable = false)
	private Campo campo; 

	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

	/*per rappresentare una data: giorno, mese, anno*/
	@Column(nullable = false)
	LocalDate data; 

	/*ha presente ora, minuti e secondi*/
	@Column(nullable = false)
	LocalTime orario;


	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getOrario() {
		return orario;
	}

	public void setOrario(LocalTime orario) {
		this.orario = orario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}




}
