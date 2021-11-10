package tp4.despensa.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tp4.despensa.entities.Producto;
import tp4.despensa.repositories.ProductoRepository;


@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productos;
	
	private static Logger LOG = LoggerFactory.getLogger(ProductoService.class);

	
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
	
	@Transactional
	public Boolean updateProducto(Producto p, int id) {

		Optional<Producto> aux = this.getProducto(id);
		
		if(!aux.isPresent()) {
			LOG.info("El producto con id {} no existe", id);
			return false;
		}
		
		Producto p1 = aux.get();
		

		Boolean cambioNom = !p1.getNombre().equals(p.getNombre());
		Boolean cambioDesc = !p1.getDescripcion().equals(p.getDescripcion());
		Boolean cambioPrec = p1.getPrecio() != p.getPrecio();
		
		if(cambioNom) {
			LOG.info("Cambiando nombre");
			p1.setNombre(p.getNombre());
		}
		if(cambioDesc) {
			LOG.info("Cambiando descripcion");
			p1.setDescripcion(p.getDescripcion());
		}
		if(cambioPrec) {
			LOG.info("Cambiando precio");
			p1.setPrecio(p.getPrecio());
		}
		
		if(cambioNom || cambioDesc || cambioPrec) {
			this.productos.save(p1);
			LOG.info("Hubo Cambios");
		}
		return true;
	}

	public List<Producto> getProductos() {
		return this.productos.findAll();
	}
	
	public Producto getProductoMasVendido() {
		List<Producto> productos = this.productos.getProductoMasVendido();
		
		return productos.get(0);
	}
}
