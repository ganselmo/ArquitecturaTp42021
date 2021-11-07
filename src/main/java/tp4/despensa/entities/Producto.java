package tp4.despensa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy=false)
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

	@Column(nullable = false)
	double precio;
	
	
	public Producto() {
		super();
	}

	public Producto(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = 0;
		this.precio = 0;
	}

	public Producto(String nombre,double precio) {
		super();
		this.nombre = nombre;
		this.cantidad = 0;
		this.precio = precio;
	}

	public Producto(String nombre, String descripcion, int cantidad,double precio) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.precio = precio;
	}
	
	public Producto(String nombre, int cantidad,double precio) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
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

	
	
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [nombre=" + nombre + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", precio="
				+ precio + "]";
	}
	
	

	

	
	
	
	
}
