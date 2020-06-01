package domainapp.modules.simple.dom.reportes;

import java.util.Date;

import domainapp.modules.simple.dom.clientenoafiliado.ClienteNoAfiliado;
import domainapp.modules.simple.dom.producto.Producto;

public class VoucherNoAfiliadoReporte {
	
	private int reservaCodigo;

	public int getReservaCodigo() {
		return reservaCodigo;
	}

	public void setReservaCodigo(int reservaCodigo) {
		this.reservaCodigo = reservaCodigo;
	}

	private Date reservaFecha;

	public Date getReservaFecha() {
		return reservaFecha;
	}

	public void setReservaFecha(Date reservaFecha) {
		this.reservaFecha = reservaFecha;
	}
	
	private ClienteNoAfiliado reservaCliente;

	public ClienteNoAfiliado getReservaCliente() {
		return reservaCliente;
	}

	public void setReservaCliente(ClienteNoAfiliado reservaCliente) {
		this.reservaCliente = reservaCliente;
	}
	
	private int personaJuridicaDni;

	public int getPersonaJuridicaDni() {
		return personaJuridicaDni;
	}

	public void setPersonaJuridicaDni(int personaJuridicaDni) {
		this.personaJuridicaDni = personaJuridicaDni;
	}
	
	private String personaTelefono;

	public String getPersonaTelefono() {
		return personaTelefono;
	}
	public void setPersonaTelefono(String personaTelefono) {
		this.personaTelefono = personaTelefono;
	}	
		
	private String personaMail;
	
	public String getPersonaMail() {
		return personaMail;
	}
	public void setPersonaMail(String personaMail) {
		this.personaMail = personaMail;
	}	
	
	private Producto voucherProducto;

	public Producto getVoucherProducto() {
		return voucherProducto;
	}

	public void setVoucherProducto(Producto voucherProducto) {
		this.voucherProducto = voucherProducto;
	}	
	
	private Date voucherFechaIngreso;

	public Date getVoucherFechaIngreso() {
		return voucherFechaIngreso;
	}

	public void setVoucherFechaIngreso(Date voucherFechaIngreso) {
		this.voucherFechaIngreso = voucherFechaIngreso;
	}	

	private Date voucherFechaEgreso;

	public Date getVoucherFechaEgreso() {
		return voucherFechaEgreso;
	}

	public void setVoucherFechaEgreso(Date voucherFechaEgreso) {
		this.voucherFechaEgreso = voucherFechaEgreso;
	}	
	
	private int voucherCantidadNoches;

	public int getVoucherCantidadNoches() {
		return voucherCantidadNoches;
	}

	public void setVoucherCantidadNoches(int voucherCantidadNoches) {
		this.voucherCantidadNoches = voucherCantidadNoches;
	}	
	
	private int voucherCantidadPasajeros;

	public int getVoucherCantidadPasajeros() {
		return voucherCantidadPasajeros;
	}

	public void setVoucherCantidadPasajeros(int voucherCantidadPasajeros) {
		this.voucherCantidadPasajeros = voucherCantidadPasajeros;
	}	
	
	private Double voucherPrecioTotal;

	public Double getVoucherPrecioTotal() {
		return voucherPrecioTotal;
	}

	public void setVoucherPrecioTotal(Double voucherPrecioTotal) {
		this.voucherPrecioTotal = voucherPrecioTotal;
	}	
	
	private String voucherObservaciones;
	
	public String getVoucherObservaciones() {
		return voucherObservaciones;
	}
	
	public void setVoucherObservaciones(String voucherObservaciones) {
		this.voucherObservaciones=voucherObservaciones;
	}
	
	private String politicasTexto;

	public String getPoliticasTexto() {
		return politicasTexto;
	}

	public void setPoliticasTexto(String politicasTexto) {
		this.politicasTexto = politicasTexto;
	}
	
}
