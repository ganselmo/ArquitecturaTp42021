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

import tp4.despensa.entities.Cliente;
import tp4.despensa.services.ClienteService;

//Controlador de cliente que se ocupa de capturar los request 
//que entran en la aplicación y derivan la consulta al servicio correspondiente.
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
	private static Logger LOG = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private ClienteService clienteService;

	//devuelte un cliente con un id en particular
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getCliente(@PathVariable("id") int id) {
		LOG.info("Buscando cliente {}", id);
		Optional<Cliente> cliente = this.clienteService.getCliente(id);

		if (!cliente.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		}
	}

	//agrega un cliente en la base de datos
	@PostMapping("")
	public ResponseEntity<?> addCliente(@RequestBody Cliente c) {
		boolean ok = this.clienteService.addCliente(c);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<Cliente>(c, HttpStatus.OK);
		}
	}

	//elimina un cliente determinado por medio de un id
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable("id") int id) {
		boolean ok = this.clienteService.deleteCliente(id);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
	}

	//actualiza algun atributo de un cliente ya registrado en la base
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCliente(@RequestBody Cliente c, @PathVariable("id") int id) {
		boolean ok = this.clienteService.updateCliente(c, id);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<Cliente>(c, HttpStatus.OK);
		}
	}

	//obtiene el listado de clientes
	@GetMapping("")
	public List<Cliente> getAll() {
		return this.clienteService.getClientes();
	}
}
