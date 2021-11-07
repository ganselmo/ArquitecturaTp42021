package tp4.despensa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tp4.despensa.entities.Producto;
public interface ProductoRepository extends JpaRepository<Producto,Integer> {
	
	
	@Query("select p FROM Venta v JOIN v.productos p group by p.id order by count(*) desc")
	public List<Producto> getProductoMasVendido();
	
}

