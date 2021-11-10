package tp4.despensa.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
public class Venta {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	
	@ManyToOne
	@Cascade(CascadeType.MERGE)
	private  Cliente cliente;
	
	@ManyToMany
	@Cascade(CascadeType.MERGE)
	private List<Producto> productos;
	
	@Column(nullable = false)
	Timestamp fecha;

	private double total;
	
	public Venta() {
		super();
	}



	public Venta(Cliente cliente, List<Producto> productos) {
		super();
		this.cliente = cliente;
		this.productos = new ArrayList<Producto>(productos);
		this.fecha =  new Timestamp(System.currentTimeMillis());
		this.total = this.calculateTotal();
	}



	public int getId() {
		return id;
	}

	public double calculateTotal()
	{
		double suma = 0;
		for (Producto producto : productos) {
			suma += producto.getPrecio();
		}
		
		return  Math.floor(suma * 100) / 100;
	}

	
	public double getTotal() {
		return total;
	}



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	public List<Producto> getProductos() {
		return this.productos;
	}



	public Timestamp getFecha() {
		return fecha;
	}

	
	public void addProducto(Producto p) {
		this.productos.add(p);
		this.total = this.calculateTotal();
	}
	
	public void removeProductos() {
		this.productos = new  ArrayList<Producto>();
		this.total = 0;
	}


	@Override
	public String toString() {
		return "Venta [cliente=" + cliente + ", productos=" + productos + ", fecha=" + fecha + ", total=" + total + "]";
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
		Venta other = (Venta) obj;
		return id == other.id;
	}

	
	
	
}
