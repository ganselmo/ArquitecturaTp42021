package tp4.despensa.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tp4.despensa.entities.Cliente;
import tp4.despensa.entities.Producto;

//repositorio de cliente destinado a interactuar con la table clientes en la base de datos
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
	
	
	@Query("select p FROM Venta v JOIN v.productos p WHERE v.cliente =:cliente and v.fecha <:hoy and v.fecha >:ayer")
	public List<Producto> getVentasDelDia(@Param("cliente") Cliente cliente, @Param("hoy") Date hoy, @Param("ayer") Date ayer);
	
	
	
}
