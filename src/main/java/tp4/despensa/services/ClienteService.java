package tp4.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//	PARA AGREGAR EN VentaService
//import tp4.despensa.dto.ReporteClienteVentaDTO;
//import tp4.despensa.dto.ReporteVentasPorDiaDTO;


import tp4.despensa.entities.Cliente;
import tp4.despensa.repositories.ClienteRepository;

//PARA AGREGAR EN VentaService
//import tp4.despensa.repositories.VentaRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientes;
	
//	PARA AGREGAR EN VentaService
//	@Autowired
//	private VentaRepository ventas;
	
	
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
	public Boolean deleteCliente(int id) {
		this.clientes.deleteById(id);
		if(this.getCliente(id) == null) {
			return true;
		}else {
			return false;
		}
	}

	public List<Cliente> getClientes() {
		return this.clientes.findAll();
	}
	
//	PARA AGREGAR EN VentaService	
//	public List<ReporteVentasPorDiaDTO> getVentasPorDia() {
//		return this.ventas.getReporteVentasPorDia();
//	}
//	
//	public List<ReporteClienteVentaDTO> getVentasClientes() {
//		return this.ventas.getReporteClientesVentas();
//	}
}
