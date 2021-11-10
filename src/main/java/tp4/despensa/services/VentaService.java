package tp4.despensa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp4.despensa.dto.ReporteClienteVentaDTO;
import tp4.despensa.dto.ReporteVentasPorDiaDTO;
import tp4.despensa.entities.Cliente;
import tp4.despensa.entities.Producto;
import tp4.despensa.entities.Venta;

import tp4.despensa.repositories.VentaRepository;

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

	public Optional<Venta> getVenta(int id) {
		return this.vr.findById(id);
	}

	public List<Venta> getVentas() {
		return this.vr.findAll();
	}

	public Boolean addVenta(Venta v) {

		Optional<Cliente> c = cs.getCliente(v.getCliente().getId());
		if (!c.isPresent()) {
			LOG.info("Cliente con id {} no existe", v.getCliente().getId());
			return false;
		}
		List<Producto> productos = new ArrayList<>();
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
			productos.add(newp);
			this.ss.removeStock(newp, 1);

		}

		Venta venta = new Venta(c.get(), productos);

		vr.save(venta);

		if (this.getVenta(venta.getId()) != null) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateVenta(int id, Venta v) {
		Optional<Venta> ov = this.getVenta(id);

		if (!ov.isPresent()) {
			LOG.info("Venta con id {} no existe", id);
			return false;
		}
		Venta v1 = ov.get();

		boolean cambioC = v1.getCliente().getId() != v.getCliente().getId();
		boolean cambioP = !Objects.equals(v1.getProductos(), v.getProductos());
		if (cambioC) {
			LOG.info("Cambiando Cliente");
			Optional<Cliente> cliente = this.cs.getCliente(v.getCliente().getId());

			if (!cliente.isPresent()) {
				LOG.info("Cliente con id {} no existe", v.getCliente().getId());
				return false;
			}
			v1.setCliente(cliente.get());
		}

		if (cambioP) {
			LOG.info("Cambiando Productos");
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
				v1.addProducto(newp);
				this.ss.removeStock(newp, 1);
			}
		}

		if (cambioC || cambioP) {
			this.vr.save(v1);
			LOG.info("Hubo Cambios");
		}
		return true;
	}

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

	private void returnProductos(Venta v1) {

		for (Producto p : v1.getProductos()) {
			this.ss.addStock(p, 1);
		}
		v1.removeProductos();

	}

	public List<ReporteVentasPorDiaDTO> getVentasPorDia() {
		return this.vr.getReporteVentasPorDia();
	}

	public List<ReporteClienteVentaDTO> getVentasClientes() {
		return this.vr.getReporteClientesVentas();
	}
}
