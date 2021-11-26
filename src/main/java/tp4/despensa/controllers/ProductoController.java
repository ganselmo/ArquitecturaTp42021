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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tp4.despensa.entities.Producto;
import tp4.despensa.services.ProductoService;

//Controlador de producto que se ocupa de capturar los request 
//que entran en la aplicación y derivan la consulta al servicio correspondiente.
@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
	private static Logger LOG = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductoService productoService;
	
	//obtiene un producto por medio de su id
	@GetMapping("/{id}")
	public ResponseEntity<Producto> getProducto(@PathVariable("id")int id){
		LOG.info("Buscando producto {}", id);
		Optional<Producto> producto = this.productoService.getProducto(id);
		
		if (!producto.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Producto>(producto.get(), HttpStatus.OK);
		}
	}
	
	//agrega un producto a la base de datos
	@PostMapping("")
	public ResponseEntity<?> addProducto(@RequestBody Producto p){
		boolean ok = this.productoService.addProducto(p);
		if(!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}else {
			return new ResponseEntity<Producto>(p, HttpStatus.OK);
		}
	}
	
	//elimina determinado producto por medio de su id
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProducto(@PathVariable("id")int id){
		boolean ok = this.productoService.deleteProducto(id);
		if(!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}else {
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
	}
	
	//actualiza un producto por medio de su id
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProducto(@RequestBody Producto c, @PathVariable("id")int id ){
		boolean ok = this.productoService.updateProducto(c, id);
		if(!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}else {
			return new ResponseEntity<Producto>(c, HttpStatus.OK);
		}
	}
	
	//obtiene el listado de productos
	@GetMapping("")
	public List<Producto> getAll(){
		return this.productoService.getProductos();
	}
	
	//obtiene un reporte del producto mas vendido
	@GetMapping("/mas-vendido")
	public Producto getProductoMasVendido(){
		return this.productoService.getProductoMasVendido();
	}
}
