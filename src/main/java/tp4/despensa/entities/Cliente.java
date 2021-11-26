package tp4.despensa.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

//Entidad cliente
@Entity
@Proxy(lazy=false)
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	@Column(nullable = false) 
	private String nombre;
	@Column(nullable = false)
	private String apellido;
	@Column(nullable = false)
	private String dni;
	
	
	
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
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return id == other.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
