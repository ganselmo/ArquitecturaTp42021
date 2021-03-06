package tp4.despensa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tp4.despensa.entities.Producto;

//repositorio de producto destinado a interactuar con la tabla productos en la base de datos

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {
	
	
	@Query("select p FROM Venta v JOIN v.productos p group by p.id order by count(*) desc")
	public List<Producto> getProductoMasVendido();
	
}

