package tp4.despensa.dto;

public class ReporteClienteVentaDTO {
	
	private int id;
	private double total;
	
	public ReporteClienteVentaDTO() {
		super();
	}

	public ReporteClienteVentaDTO(int id, double total) {
		super();
		this.id = id;
		this.total = total;
	}

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	public double gettotal() {
		return total;
	}

	public void settotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ReporteClienteVentaDTO [id=" + id + ", total=" + total + "]";
	}
	
	
	
	

}
