package it.uniroma3.siw.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;



/*  La classe campo possiede :
 *   - un Id univoco per ogni campo (la chiave primaria),
 *   - il tipo di campo a cui si fa riferimento,
 *   - il costo a persona da pagare se si vuole giocare,
 *   - il numero minimo di giocatori consentiti per giocare una partita per quel determinato campo. 
 *    
 *  Ogni campo può avere una o più prenotazioni effettuate da un utente 
 *  durante un giorno, ma una sola per una determinata fascia oraria.   
 *  
 *  (Quindi una lista di prenotazioni).
 */



@Entity
public class Campo {
   
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campo_generator")
	@SequenceGenerator(name = "campo_generator", sequenceName = "campo_seq", allocationSize = 1)
	private Long id;
	
	@Column(nullable = false)
	private int numeroGiocatori; 
	
	@Column(nullable = false)
	private int costo;
	
	@Column(nullable = false)
	private String tipo;
	
	@Column(nullable = false)
	private String nome;
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId_campo() {
		return this.id;
	}
	
	public void setId_campo(Long id) {
		this.id = id;
	}

	public int getNumero_giocatori() {
		return numeroGiocatori;
	}

	public void setNumero_giocatori(int numero_giocatori) {
		this.numeroGiocatori = numero_giocatori;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
	
	
}
