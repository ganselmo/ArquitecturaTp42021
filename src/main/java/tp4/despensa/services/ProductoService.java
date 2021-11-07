package tp4.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp4.despensa.entities.Producto;
import tp4.despensa.repositories.ProductoRepository;


@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productos;
	
	public Optional<Producto> getProducto(int id) {
		return this.productos.findById(id);
	}
	
	@Transactional
	public Boolean addProducto(Producto p) {
		this.productos.save(p);
		if(this.getProducto(p.getId()) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Transactional
	public Boolean deleteProducto(int id) {
		this.productos.deleteById(id);
		if(this.getProducto(id) == null) {
			return true;
		}else {
			return false;
		}
	}

	public List<Producto> getProductos() {
		return this.productos.findAll();
	}
	
	public Producto getProductoMasVendido() {
		List<Producto> productos = this.productos.getProductoMasVendido();
		
		return productos.get(0);
	}
}
