package tp4.despensa.dto;

//Reporte como un objeto de transferencia
// con solo los datos requeridos por el cliente
public class ReporteClienteVentaDTO {
	
	private int idCliente;
	private String nombre;
	private String apellido;
	private double total;
	
	public ReporteClienteVentaDTO() {
		super();
	}

	public ReporteClienteVentaDTO(int idCliente, String nombre, String apellido, double total) {
		super();
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.total = total;
	}
	
	public int getId() {
		return idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	
	@Override
	public String toString() {
		return "ReporteClienteVentaDTO [idCliente=" + idCliente + ", nombre=" + nombre + ", apellido=" + apellido + ", total=" + total
				+ "]";
	}

}
