package tp4.despensa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	int id;
	@Column(nullable = false)
	String nombre;
	@Column(nullable = false)
	String apellido;
	@Column(nullable = false)
	String dni;
	
	
	
	public Cliente() {
		super();
	}
	
	public Cliente(String nombre, String apellido, String dni) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}
	
	

	public String getNombre() {
		return nombre;
	}



	public String getApellido() {
		return apellido;
	}



	public String getDni() {
		return dni;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public void setApellido(String apellido) {
		this.apellido = apellido;
	}



	public void setDni(String dni) {
		this.dni = dni;
	}



	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + "]";
	}
	
	
	
	
}
