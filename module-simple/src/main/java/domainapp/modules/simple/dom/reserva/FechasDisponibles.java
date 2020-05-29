package domainapp.modules.simple.dom.reserva;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;

import domainapp.modules.simple.dom.producto.Producto;

@DomainObject(nature = Nature.VIEW_MODEL, objectType="FechasDisponibles")
public class FechasDisponibles implements Comparable<FechasDisponibles>{
	
	public TranslatableString tittle() {
		return TranslatableString.tr(getProducto()+". Desde: "+new SimpleDateFormat("dd-MM-yyyy").format(getFechaDesde())+". Hasta "+new SimpleDateFormat("dd-MM-yyyy").format(getFechaHasta()));
	}
	
	public FechasDisponibles() {
		// TODO Auto-generated constructor stub
	}
	
	public FechasDisponibles(Producto producto, Date fechaDesde, Date fechaHasta) {
		setProducto(producto);
		setFechaDesde(fechaDesde);
		setFechaHasta(fechaHasta);
	}
	
	@PropertyLayout(named = "Producto")
	@MemberOrder(sequence="1")
	private Producto producto;
	
	public Producto getProducto() {
		return producto;
	}
	
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	@PropertyLayout(named = "Fecha Desde")
	@MemberOrder(sequence="2")
	private Date fechaDesde;
	
	public Date getFechaDesde() {
		return fechaDesde;
	}
	
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	
	@PropertyLayout(named = "Fecha Hasta")
	@MemberOrder(sequence="3")
	private Date fechaHasta;
	
	public Date getFechaHasta() {
		return fechaHasta;
	}
	
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
	@PropertyLayout(named = "Memo")
	@MemberOrder(sequence="4")
	private String memo;
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public String toString() {
		return getProducto()+". Desde: "+new SimpleDateFormat("dd-MM-yyyy").format(getFechaHasta())+". Hasta "+new SimpleDateFormat("dd-MM-yyyy").format(getFechaHasta());
	}

	@Override
	public int compareTo(FechasDisponibles fechasDisponibles) {
		// TODO Auto-generated method stub
		return this.fechaDesde.compareTo(fechasDisponibles.fechaDesde);
	}

}
