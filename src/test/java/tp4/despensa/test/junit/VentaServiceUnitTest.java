package tp4.despensa.test.junit;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import tp4.despensa.entities.Cliente;
import tp4.despensa.entities.Producto;
import tp4.despensa.entities.Venta;
import tp4.despensa.repositories.VentaRepository;
import tp4.despensa.services.ClienteService;
import tp4.despensa.services.ProductoService;
import tp4.despensa.services.StockService;
import tp4.despensa.services.VentaService;

@ExtendWith(MockitoExtension.class)
public class VentaServiceUnitTest {

	@Mock
	private VentaRepository vr;
	@Autowired
	private ProductoService ps;
	@Autowired
	private ClienteService cs;
	@Autowired
	private StockService ss;
	@Mock
	private VentaService vs;
	
	private ArrayList<Venta> listV;
	
	
	@BeforeEach
	void setUp() {
		
		ArrayList<Producto> listP = new ArrayList<>();
		
		Producto p1 = new Producto();
		p1.setId(11);
		p1.setNombre("pruebaProducto");
		p1.setDescripcion("esto es un producto");
		p1.setPrecio(100);
		p1.setCantidad(1);
		
		Producto p2 = new Producto();
		p2.setId(22);
		p2.setNombre("pruebaProducto22");
		p2.setDescripcion("esto es un producto22");
		p2.setPrecio(200);
		p2.setCantidad(2);
		
		listP.add(p1);
		listP.add(p2);
		
		Cliente c = new Cliente();
		c.setNombre("nombrePrueba");	
		c.setApellido("apellidoPrueba");
		c.setDni("999999");
		c.setId(99);
		
		Venta v = new Venta(c, listP);
		this.listV = new ArrayList<>();
		listV.add(v);
	}
	
	// No sabria por que esta fallando
	@Test
	public void añadirVenta() {
		Mockito.when(vr.findAll()).thenReturn(this.listV);
		Venta v = vr.findAll().get(0);
		System.out.println(v);
		
		System.out.println(this.vs.addVenta(v));
		assertTrue(this.vs.addVenta(v));
		
	}
}
