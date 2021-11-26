package tp4.despensa.entities;

import java.util.Objects;

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
	private String nombre;
	
	@Column(nullable = true)
	private String descripcion;

	@Column(nullable = false)
	private int cantidad;

	@Column(nullable = false)
	private double precio;
	
	
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
	

	public void addStock(int cant) {
		this.cantidad +=cant ;
	}
	
	public void removeStock(int cant) {
		this.cantidad -=cant ;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	


	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", cantidad=" + cantidad
				+ ", precio=" + precio + "]";
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
		Producto other = (Producto) obj;
		return id == other.id;
	}

	public void setCantidad(int cant) {
		this.cantidad = cant;
	}
	
	

	

	
	
	
	
}
