package tp4.despensa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tp4.despensa.entities.Producto;
public interface ProductoRepository extends JpaRepository<Producto,Integer> {
	
}

