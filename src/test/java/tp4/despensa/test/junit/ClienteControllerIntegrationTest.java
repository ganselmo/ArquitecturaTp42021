package tp4.despensa.test.junit;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tp4.despensa.controllers.ClienteController;
import tp4.despensa.entities.Cliente;
import tp4.despensa.repositories.ClienteRepository;

@SpringBootTest
public class ClienteControllerIntegrationTest {

	static Cliente defaultCliente;
	static List<Cliente> clientesATestear;

    @Autowired
    private ClienteRepository repository;
    
    @Autowired
    private ClienteController controller;
	
    @BeforeAll
    static void InitClass() {
        clientesATestear = new ArrayList<Cliente>();
        clientesATestear.add(new Cliente("Justo", "Ceril", "35774000"));
        clientesATestear.add(new Cliente("Juan", "Perez", "30345647"));
        clientesATestear.add(new Cliente("Roberto", "Castaño", "32456768"));
        clientesATestear.add(new Cliente("Rosario", "Perrota", "32445336"));
        clientesATestear.add(new Cliente("Geronimo", "Anselmo", "34556776"));
        clientesATestear.add(new Cliente("Agustina", "Notti", "38996785"));
        clientesATestear.add(new Cliente("Luciano", "Scaminaci", "35774000"));
        int cliente = (int) (Math.random()*clientesATestear.size());
        defaultCliente = ClienteControllerIntegrationTest.clientesATestear.get(cliente);
    }
    
    @BeforeEach
    void setUp() throws Exception {
        
    }
    
    // El test pasa si se agrega correctamente un Cliente
	@Test
	public void agregarCliente() {
		ResponseEntity<?> re = controller.addCliente(defaultCliente);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		assertEquals(((Cliente) re.getBody()).getNombre(), defaultCliente.getNombre());
		assertEquals(((Cliente) re.getBody()).getApellido(), defaultCliente.getApellido());
		assertEquals(((Cliente) re.getBody()).getDni(), defaultCliente.getDni());
	}
	
	// El test pasa si se agrega correctamente un Cliente y se modifica correctamente el mismo
	@Test
	public void modificarCliente() {
		controller.addCliente(defaultCliente);
		Cliente c = new Cliente("nomPrueba", "apePrueba", "111111111");
		ResponseEntity<?> re = controller.updateCliente(c, controller.getAll().get(0).getId());
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals(((Cliente) re.getBody()).getNombre(), "nomPrueba");
		assertEquals(((Cliente) re.getBody()).getApellido(), "apePrueba");
		assertEquals(((Cliente) re.getBody()).getDni(), "111111111");
	}
	
	// El test pasa si se agrega correctamente un Cliente y se elimina el mismo, controlando que cuando se elimina
	// el tamaño de la lista de clientes sea diferente
	@Test
	public void eliminarCliente() {
		controller.addCliente(defaultCliente);
		int id = defaultCliente.getId();
		List<Cliente> clientes = controller.getAll();
		ResponseEntity<?> re = controller.deleteCliente(id);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		assertTrue(clientes.size() != controller.getAll().size());
	}
	
	// El test pasa si no hay Ids repetidos en la lista, comprobando que la lista y el set sean del mismo tamaño
	// ya que al tirar una lista en un set si hay valores repetidos estos caen en el mismo key, y el size() se reduce.
	@Test
	public void IdClienteNoRepetido() {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		
		for (Cliente c : clientesATestear) {
			controller.addCliente(c);
			testList.add(c.getId());
		}
	    Set<Integer> set = new HashSet<Integer>(testList);

	    assertTrue(set.size() == testList.size());
	}
	
	// El test pasa si se agrega correctamente un Cliente y se encuentra el mismo por su Id
	@Test
	public void getClientePorId() {
		controller.addCliente(defaultCliente);
		ResponseEntity<?> re = controller.getCliente(defaultCliente.getId());
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		assertEquals(((Cliente) re.getBody()).getNombre(), defaultCliente.getNombre());
		assertEquals(((Cliente) re.getBody()).getApellido(), defaultCliente.getApellido());
		assertEquals(((Cliente) re.getBody()).getDni(), defaultCliente.getDni());
	}
	
	// El test pasa si se agregan correctamente una lista de clientes y se recupera la misma lista
	@Test
	public void getAllClientes() {
		for (Cliente c : clientesATestear) {
			controller.addCliente(c);
		}
		
		List<Cliente> clientes = controller.getAll();
		for (Cliente c : clientes) {
			assertTrue(clientes.contains(c));
		}
	}
}
