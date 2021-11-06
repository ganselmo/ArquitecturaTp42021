package tp4.despensa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tp4.despensa.entities.Venta;

public interface VentaRepository extends JpaRepository<Venta,Integer> {
	
}

