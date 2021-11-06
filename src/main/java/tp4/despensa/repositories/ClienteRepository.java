package tp4.despensa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tp4.despensa.entities.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
	
}
