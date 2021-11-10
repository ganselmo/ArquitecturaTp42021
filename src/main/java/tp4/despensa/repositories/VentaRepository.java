package tp4.despensa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tp4.despensa.dto.ReporteClienteVentaDTO;
import tp4.despensa.dto.ReporteVentasPorDiaDTO;
import tp4.despensa.entities.Venta;

public interface VentaRepository extends JpaRepository<Venta,Integer> {
	
	
	//Genere un reporte donde se indiquen los clientes y el monto total de sus compras
	@Query("select new tp4.despensa.dto.ReporteClienteVentaDTO(v.id, sum(v.total)) from Venta v group by v.id")
	public List<ReporteClienteVentaDTO> getReporteClientesVentas();
	
	//Genere un reporte con las ventas por día -->VER PARA MEJORAR LA CONSULTA
	@Query("SELECT new tp4.despensa.dto.ReporteVentasPorDiaDTO(year(v.fecha), month(v.fecha), day(v.fecha), COUNT(*)) FROM Venta v group by year(v.fecha), month(v.fecha), day(v.fecha)")
	public List<ReporteVentasPorDiaDTO> getReporteVentasPorDia();

}

