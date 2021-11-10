package tp4.despensa.dto;

public class ReporteVentasPorDiaDTO {
	
	private int dia;
	private int mes;
	private int anio;
	private long cantidadVentas;
	
	public ReporteVentasPorDiaDTO() {
		super();
	}

	public ReporteVentasPorDiaDTO(int anio, int mes, int dia, long cantidadVentas) {
		super();
		this.anio = anio;
		this.mes = mes;
		this.dia = dia;
		this.cantidadVentas = cantidadVentas;
	}

	public String getDia() {
		return dia+"-"+mes+"-"+anio;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public long getCantidadVentas() {
		return cantidadVentas;
	}

	public void setCantidadVentas(long cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}

	@Override
	public String toString() {
		return "ReporteVentasPorDiaDTO [dia=" + dia + "-" + mes + "-" + anio + ", cantidadVentas="
				+ cantidadVentas + "]";
	}

}
