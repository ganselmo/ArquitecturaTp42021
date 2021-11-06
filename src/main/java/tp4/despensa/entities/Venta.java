package tp4.despensa.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Venta {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	
	@ManyToOne
	@Cascade(CascadeType.PERSIST)
	private  Cliente cliente;
	
	@ManyToMany
	@JsonProperty(access=Access.WRITE_ONLY)
	@Cascade(CascadeType.PERSIST)
	private List<Producto> productos;
	
	@Column(nullable = false)
	Timestamp fecha;

	
	
	public Venta() {
		super();
	}



	public Venta(Cliente cliente, List<Producto> productos) {
		super();
		this.cliente = cliente;
		this.productos = new ArrayList<Producto>(productos);
		this.fecha =  new Timestamp(System.currentTimeMillis());
	}



	public int getId() {
		return id;
	}



	public Cliente getCliente() {
		return cliente;
	}



	public List<Producto> getProductos() {
		return this.productos;
	}



	public Timestamp getFecha() {
		return fecha;
	}
	
	
	
	
}
