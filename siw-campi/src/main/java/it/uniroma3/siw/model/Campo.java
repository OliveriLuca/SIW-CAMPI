package it.uniroma3.siw.model;


import java.util.Objects;

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
	private int costo;
	
	@Column(nullable = false)
	private String tipo;
	
	@Column(nullable = false)
	private String nome;
	
	
	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campo other = (Campo) obj;
		return Objects.equals(nome, other.nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
