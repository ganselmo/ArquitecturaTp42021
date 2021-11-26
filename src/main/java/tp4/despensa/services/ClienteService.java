package tp4.despensa.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//	PARA AGREGAR EN VentaService
//import tp4.despensa.dto.ReporteClienteVentaDTO;
//import tp4.despensa.dto.ReporteVentasPorDiaDTO;

import tp4.despensa.entities.Cliente;
import tp4.despensa.entities.Producto;
import tp4.despensa.repositories.ClienteRepository;

//PARA AGREGAR EN VentaService
//import tp4.despensa.repositories.VentaRepository;

//Servicio de cliente
//intermediario entre el controlador y el repositorio,
//lleva a cabo la lógica de negocio y procesamiento de los datos.
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientes;

	private static Logger LOG = LoggerFactory.getLogger(ClienteService.class);

	public Optional<Cliente> getCliente(int id) {
		return this.clientes.findById(id);
	}

	//agrega un cliente en la base de datos
	@Transactional
	public Boolean addCliente(Cliente c) {
		this.clientes.save(c);
		if (this.getCliente(c.getId()) != null) {
			return true;
		} else {
			return false;
		}
	}

	//elimina un cliente de la base de datos
	@Transactional
	public Boolean deleteCliente(int id) {
		this.clientes.deleteById(id);
		if(!this.getCliente(id).isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	//actualiza un cliente de la base de datos
	@Transactional
	public Boolean updateCliente(Cliente c, int id) {

		Optional<Cliente> aux = this.getCliente(id);

		if (!aux.isPresent()) {
			LOG.info("El cliente con id {} no existe", id);
			return false;
		}

		Cliente c1 = aux.get();

		Boolean cambioAp = !c1.getApellido().equals(c.getApellido());
		Boolean cambioNom = !c1.getNombre().equals(c.getNombre());
		Boolean cambioDNI = c1.getDni() != c.getDni();

		if (cambioAp) {
			LOG.info("Cambiando apellido");
			c1.setApellido(c.getApellido());
		}
		if (cambioNom) {
			LOG.info("Cambiando nombre");
			c1.setNombre(c.getNombre());
		}
		if (cambioDNI) {
			LOG.info("Cambiando DNI");
			c1.setDni(c.getDni());
		}

		if (cambioAp || cambioNom || cambioDNI) {
			this.clientes.save(c1);
			LOG.info("Hubo Cambios");
		}
		return true;

	}

	//obtiene el listado de clientes de la base de datos
	public List<Cliente> getClientes() {
		return this.clientes.findAll();
	}
	
	//obtiene el listado de ventas por dia
	public List<Producto> getVentasDelDia(Cliente cliente){
		
		Date ayer = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(ayer); 
		c.add(Calendar.DATE, -1);
		ayer = c.getTime();
		Date hoy = new Date();

		return this.clientes.getVentasDelDia(cliente,hoy,ayer);
	}

}
