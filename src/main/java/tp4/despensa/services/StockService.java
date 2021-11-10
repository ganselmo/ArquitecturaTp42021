package tp4.despensa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp4.despensa.entities.Producto;
import tp4.despensa.repositories.ProductoRepository;

@Service
public class StockService {
	
	@Autowired
	private ProductoRepository pr;
	
	public boolean containsStock(Producto p) {
		
		return p.getCantidad()>0;
		
	}

	public void addStock(Producto p, int cant) {
		
		p.addStock(cant);

		
	}
	
	public void removeStock(Producto p, int cant) {
		
		p.removeStock(cant);
		
	}

}
