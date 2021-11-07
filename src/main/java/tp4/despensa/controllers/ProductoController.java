package tp4.despensa.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tp4.despensa.entities.Producto;
import tp4.despensa.services.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	private static Logger LOG = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> getProducto(@PathVariable("id")int id){
		LOG.info("Buscando producto {}", id);
		Optional<Producto> producto = this.productoService.getProducto(id);
		
		if (producto.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Producto>(producto.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> addProducto(@RequestBody Producto p){
		boolean ok = this.productoService.addProducto(p);
		if(!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}else {
			return new ResponseEntity<Producto>(p, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("")
	public ResponseEntity<?> deleteProducto(@PathVariable("id")int id){
		boolean ok = this.productoService.deleteProducto(id);
		if(!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}else {
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
	}
	
	@GetMapping("/all")
	public List<Producto> getAll(){
		return this.productoService.getProductos();
	}
	
	@GetMapping("/mas-vendido")
	public Producto getProductoMasVendido(){
		return this.productoService.getProductoMasVendido();
	}
}
