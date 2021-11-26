package tp4.despensa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp4.despensa.dto.ReporteClienteVentaDTO;
import tp4.despensa.dto.ReporteVentasPorDiaDTO;
import tp4.despensa.entities.Cliente;
import tp4.despensa.entities.Producto;
import tp4.despensa.entities.Venta;

import tp4.despensa.repositories.VentaRepository;

//Servicio de ventas
//intermediario entre el controlador y el repositorio,
//lleva a cabo la lógica de negocio y procesamiento de los datos.
@Service
public class VentaService {

	private static Logger LOG = LoggerFactory.getLogger(VentaService.class);

	@Autowired
	private VentaRepository vr;
	@Autowired
	private ProductoService ps;
	@Autowired
	private ClienteService cs;
	@Autowired
	private StockService ss;

	//obtiene una venta por medio de un id
	public Optional<Venta> getVenta(int id) {
		return this.vr.findById(id);
	}

	//obtiene el listado de ventas
	public List<Venta> getVentas() {
		return this.vr.findAll();
	}

	//agrega una venta a la base de datos
	public Boolean addVenta(Venta v) {

		if(!this.validarVentas(v,0))
		{
			return false;
		}
				
		List<Producto> psd =this.getVentasDelDia(v.getCliente());
		LOG.info("Hola");
		for (Producto p : psd){
			
			LOG.info(p.toString());
		}
		Optional<Cliente> c = cs.getCliente(v.getCliente().getId());
		if (!c.isPresent()) {
			LOG.info("Cliente con id {} no existe", v.getCliente().getId());
			return false;
		}
		List<Producto> productos = new ArrayList<>();
		int contador = 1;
		for (Producto p : v.getProductos()) {
			Optional<Producto> op = ps.getProducto(p.getId());
			if (!op.isPresent()) {
				LOG.info("Producto con id {} no existe", p.getId());
				return false;
			}
			Producto newp = op.get();
			if (!this.ss.containsStock(newp)) {
				LOG.info("Producto con id {} no tiene Stock", p.getId());
				return false;
			}
			if(!this.validarVentas(v,contador))
			{
				LOG.info("Solo puede comprar 3 productos");
				return false;
			}
			productos.add(newp);
			this.ss.removeStock(newp, 1);
			contador++;

		}

		Venta venta = new Venta(c.get(), productos);

		vr.save(venta);

		if (this.getVenta(venta.getId()) != null) {
			return true;
		} else {
			return false;
		}
	}

	//valida que no se haya excedido el limite de compras por cliente de 3 unidades por dia
	private boolean validarVentas(Venta v,int value) {
		
		if (this.getVentasDelDia(v.getCliente()).size() + value> 3) {
			LOG.info("el cliente no puede superar los 3 productos en ventas del dia");
			return false;
		}
		return true;
		
	}

	//actualiza una venta
	public Boolean updateVenta(int id, Venta v) {
		Optional<Venta> ov = this.getVenta(id);

		if (!ov.isPresent()) {
			LOG.info("Venta con id {} no existe", id);
			return false;
		}
		Venta v1 = ov.get();

		boolean cambioC = v1.getCliente().getId() != v.getCliente().getId();
		LOG.info(Boolean.toString(cambioC));
		
		LOG.info(v.getCliente().toString());
		LOG.info(v1.getCliente().toString());
		boolean cambioP = !Objects.equals(v1.getProductos(), v.getProductos());
		if (cambioC) {

			LOG.info("Cambiando Cliente");
			Optional<Cliente> cliente = this.cs.getCliente(v.getCliente().getId());

			if (!cliente.isPresent()) {
				LOG.info("Cliente con id {} no existe", v.getCliente().getId());
				return false;
			}
			if (this.getVentasDelDia(cliente.get()).size() > 3) {
				LOG.info("el cliente no puede superar los 3 productos en ventas del dia");
				return false;
			}
			v1.setCliente(cliente.get());
		}

		if (cambioP&&cambioC) {
			LOG.info("Cambiando Productos y Cliente");
			this.returnProductos(v1);

			int contador = 1;
			for (Producto p : v.getProductos()) {
				Optional<Producto> op = ps.getProducto(p.getId());
				if (!op.isPresent()) {
					LOG.info("Producto con id {} no existe", p.getId());
					return false;
				}
				Producto newp = op.get();
				if (!this.ss.containsStock(newp)) {
					LOG.info("Producto con id {} no tiene Stock", p.getId());
					return false;
				}
				if(!this.validarVentas(v1,contador))
				{
					LOG.info("Solo puede comprar 3 productos");
					return false;
				}
				v1.addProducto(newp);
				this.ss.removeStock(newp, 1);
				contador++;
			}
		}
		else {
			
			LOG.info("Cambiando Solo Productos");
			int contador = 1 - v1.getProductos().size();
			this.returnProductos(v1);
			
			for (Producto p : v.getProductos()) {
				Optional<Producto> op = ps.getProducto(p.getId());
				if (!op.isPresent()) {
					LOG.info("Producto con id {} no existe", p.getId());
					return false;
				}
				Producto newp = op.get();
				if (!this.ss.containsStock(newp)) {
					LOG.info("Producto con id {} no tiene Stock", p.getId());
					return false;
				}
				if(!this.validarVentas(v,contador))
				{
					LOG.info("Solo puede comprar 3 productos");
					return false;
				}
				v1.addProducto(newp);
				this.ss.removeStock(newp, 1);
				contador++;
			}
			
		}

		if (cambioC || cambioP) {
			this.vr.save(v1);
			LOG.info("Hubo Cambios");
		}
		return true;
	}

	//elimina una venta
	@Transactional
	public Boolean deleteVenta(int id) {

		Optional<Venta> vb = this.getVenta(id);
		if (vb.isPresent()) {
			LOG.info("Devolviendo productos");
			this.returnProductos(vb.get());
			LOG.info("Borrando ventas");
			this.vr.deleteById(id);
			if (!this.getVenta(id).isPresent()) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}

	//devuelte los productos de una venta en especifico
	private void returnProductos(Venta v1) {

		for (Producto p : v1.getProductos()) {
			this.ss.addStock(p, 1);
		}
		v1.removeProductos();

	}

	//reporte con el listado de ventas por dia
	public List<ReporteVentasPorDiaDTO> getVentasPorDia() {
		return this.vr.getReporteVentasPorDia();
	}

	//reporte con el listado de ventas por cliente
	public List<ReporteClienteVentaDTO> getVentasClientes() {
		return this.vr.getReporteClientesVentas();
	}
	
	//reporte con las ventas del dia
	public List<Producto> getVentasDelDia(Cliente cliente){
		return this.cs.getVentasDelDia(cliente);
	}
}
