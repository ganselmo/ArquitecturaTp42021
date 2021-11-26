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

import tp4.despensa.dto.ReporteClienteVentaDTO;
import tp4.despensa.dto.ReporteVentasPorDiaDTO;
import tp4.despensa.entities.Venta;
import tp4.despensa.services.VentaService;

//Controlador de venta que se ocupa de capturar los request 
//que entran en la aplicación y derivan la consulta al servicio correspondiente.
@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

	private static Logger LOG = LoggerFactory.getLogger(VentaController.class);

	@Autowired
	private VentaService ventaService;

	//obtiene el listado de ventas
	@GetMapping("")
	public List<Venta> getAll() {
		return this.ventaService.getVentas();
	}

	//obtiene una venta en particular por medio de un id
	@GetMapping("/{id}")
	public ResponseEntity<Venta> getVenta(@PathVariable("id") int id) {
		LOG.info("Buscando Venta {}", id);
		Optional<Venta> venta = this.ventaService.getVenta(id);
		if (!venta.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Venta>(venta.get(), HttpStatus.OK);
		}
	}

	//agrega una venta en la base de datos
	@PostMapping("")
	public ResponseEntity<?> addVenta(@RequestBody Venta v) {
		LOG.info("Guardando Venta {}", v);

		boolean ok = this.ventaService.addVenta(v);

		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<Venta>(HttpStatus.OK);
		}
	}

	//actualiza una venta por medio de su id
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVenta(@PathVariable("id") int id, @RequestBody Venta v) {
		LOG.info("Updateando Venta {}", v);

		boolean ok = this.ventaService.updateVenta(id, v);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<Venta>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVenta(@PathVariable("id") int id) {
		boolean ok = this.ventaService.deleteVenta(id);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
	}

	//obtiene un reporte con las ventas por dia
	@GetMapping("/ventas-por-dia")
	public List<ReporteVentasPorDiaDTO> getVentasPorDia() {
		return this.ventaService.getVentasPorDia();
	}

	//obtiene un reporte de las ventas por cliente
	@GetMapping("/cliente-ventas")
	public List<ReporteClienteVentaDTO> getVentasClientes() {
		return this.ventaService.getVentasClientes();
	}

}
