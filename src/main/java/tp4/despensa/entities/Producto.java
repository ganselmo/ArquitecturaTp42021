package tp4.despensa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	
	@Column(nullable = false)
	String nombre;
	
	@Column(nullable = true)
	String descripcion;

	@Column(nullable = false)
	int cantidad;
	
	public Producto() {
		super();
	}

	public Producto(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = 0;
	}

	public Producto(String nombre) {
		super();
		this.nombre = nombre;
		this.cantidad = 0;
	}

	public Producto(String nombre, String descripcion, int cantidad) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}
	
	
	
	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public int getCantidad() {
		return cantidad;
	}


	
	
	
	
}
