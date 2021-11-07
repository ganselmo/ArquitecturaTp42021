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

import tp4.despensa.entities.Cliente;
import tp4.despensa.services.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	private static Logger LOG = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getCliente(@PathVariable("id")int id){
		LOG.info("Buscando cliente {}", id);
		Optional<Cliente> cliente = this.clienteService.getCliente(id);
		
		if (cliente.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> addCliente(@RequestBody Cliente c){
		boolean ok = this.clienteService.addCliente(c);
		if(!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}else {
			return new ResponseEntity<Cliente>(c, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("")
	public ResponseEntity<?> deleteCliente(@RequestBody Cliente c){
		boolean ok = this.clienteService.deleteCliente(c);
		if(!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}else {
			return new ResponseEntity<Cliente>(c, HttpStatus.OK);
		}
	}
	
	@GetMapping("/all")
	public List<Cliente> getAll(){
		return this.clienteService.getClientes();
	}
	
	
}
