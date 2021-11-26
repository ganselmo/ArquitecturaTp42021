package tp4.despensa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp4.despensa.entities.Producto;
import tp4.despensa.repositories.ProductoRepository;

//Servicio de stock
//intermediario entre el controlador y el repositorio,
//lleva a cabo la lógica de negocio y procesamiento de los datos.

@Service
public class StockService {
	
	@Autowired
	private ProductoRepository pr;
	
	//verifica si hay stock
	public boolean containsStock(Producto p) {
		
		return p.getCantidad()>0;
		
	}

	//agrega determinada cantidad al stock del producto
	public void addStock(Producto p, int cant) {
		
		p.addStock(cant);

		
	}
	
	//remueve determinada cantidad del stock del producto
	public void removeStock(Producto p, int cant) {
		
		p.removeStock(cant);
		
	}

}
