package tp4.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp4.despensa.entities.Cliente;
import tp4.despensa.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientes;
	
	public Optional<Cliente> getCliente(int id) {
		return this.clientes.findById(id);
	}
	
	@Transactional
	public Boolean addCliente(Cliente c) {
		this.clientes.save(c);
		if(this.getCliente(c.getId()) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Transactional
	public Boolean deleteCliente(Cliente c) {
		this.clientes.delete(c);
		if(this.getCliente(c.getId()) == null) {
			return true;
		}else {
			return false;
		}
	}

	public List<Cliente> getClientes() {
		return this.clientes.findAll();
	}
	
}
