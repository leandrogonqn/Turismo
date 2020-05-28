package domainapp.modules.simple.dom.voucher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;

import domainapp.modules.simple.dom.preciohistorico.TipoPrecio;
import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.producto.ProductoRepository;
import domainapp.modules.simple.dom.reserva.Reserva;
import domainapp.modules.simple.dom.reservaafiliado.ReservaAfiliado;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Voucher")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "voucherId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "buscarVoucherPorEstado", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.modules.simple.dom.voucher.Voucher " + "WHERE voucherEstado == :voucherEstado"),
		@javax.jdo.annotations.Query(name = "buscarVoucherPorProductoYEstado", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.modules.simple.dom.voucher.Voucher " + "WHERE voucherEstado == :voucherEstado & voucherProducto == :voucherProducto") })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Voucher implements Comparable<Voucher>{
	
	public TranslatableString tittle() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return TranslatableString.tr("Producto: "+getVoucherProducto()+" Fecha Ingreso: "+sdf.format(getVoucherFechaIngreso())+" Fecha Egreso "+sdf.format(getVoucherFechaEgreso()));
	}
	
	public Voucher() {
		// TODO Auto-generated constructor stub
	}
	
	public Voucher (final Producto voucherProducto, final Date voucherFechaIngreso, final Date voucherFechaEgreso, final int voucherCantidadNoches,
			final int voucherCantidadPasajeros, final Double voucherPrecioTotal, final String voucherObservaciones, final String voucherUsuario) {
		setVoucherProducto(voucherProducto);
		setVoucherFechaIngreso(voucherFechaIngreso);
		setVoucherFechaEgreso(voucherFechaEgreso);
		setVoucherCantidadNoches(voucherCantidadNoches);
		setVoucherCantidadPasajeros(voucherCantidadPasajeros);
		setVoucherPrecioTotal(voucherPrecioTotal);
		setVoucherObservaciones(voucherObservaciones);
		setVoucherEstado(EstadoVoucher.presupuestado);
		setVoucherUsuario(voucherUsuario);
	}
	
	@Column(allowsNull="false")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Producto")
	private Producto voucherProducto;
	
	public Producto getVoucherProducto() {
		return voucherProducto;
	}
	
	public void setVoucherProducto(Producto voucherProducto) {
		this.voucherProducto=voucherProducto;
	}
	
	@Column(allowsNull="false")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Fecha Ingreso")
	private Date voucherFechaIngreso;
	
	public Date getVoucherFechaIngreso() {
		return voucherFechaIngreso;
	}
	
	public void setVoucherFechaIngreso(Date voucherFechaIngreso) {
		this.voucherFechaIngreso = voucherFechaIngreso;
	}
	
	@Column(allowsNull="false")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Fecha Egreso")
	private Date voucherFechaEgreso;
	
	public Date getVoucherFechaEgreso() {
		return voucherFechaEgreso;
	}
	
	public void setVoucherFechaEgreso(Date voucherFechaEgreso) {
		this.voucherFechaEgreso=voucherFechaEgreso;
	}
	
	@Column(allowsNull="false")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Cantidad de Noches")
	private int voucherCantidadNoches;
	
	public int getVoucherCantidadNoches() {
		return voucherCantidadNoches;
	}
	
	public void setVoucherCantidadNoches(int voucherCantidadNoches) {
		this.voucherCantidadNoches = voucherCantidadNoches;
	}
	
	@Column(allowsNull="false")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Cantidad de Pasajeros")
	private int voucherCantidadPasajeros;
	
	public int getVoucherCantidadPasajeros() {
		return voucherCantidadPasajeros;
	}
	
	public void setVoucherCantidadPasajeros(int voucherCantidadPasajeros) {
		this.voucherCantidadPasajeros = voucherCantidadPasajeros;
	}
	
	@Column(allowsNull="false")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Precio Total")
	private Double voucherPrecioTotal;
	
	public Double getVoucherPrecioTotal() {
		return voucherPrecioTotal;
	}
	
	public void setVoucherPrecioTotal(Double voucherPrecioTotal) {
		this.voucherPrecioTotal=voucherPrecioTotal;
	}
	
	@Column(allowsNull="true")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Observaciones")
	private String voucherObservaciones;
	
	public String getVoucherObservaciones() {
		return voucherObservaciones;
	}
	
	public void setVoucherObservaciones(String voucherObservaciones) {
		this.voucherObservaciones = voucherObservaciones;
	}
	
	@Column(allowsNull="false")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Estado")
	private EstadoVoucher voucherEstado;
	
	public EstadoVoucher getVoucherEstado() {
		return voucherEstado;
	}
	
	public void setVoucherEstado(EstadoVoucher voucherEstado) {
		this.voucherEstado = voucherEstado;
	}
	
	@Column(name="reservaId", allowsNull="true")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Reserva", hidden=Where.ALL_TABLES)
	private Reserva voucherReserva;
	
	public Reserva getVoucherReserva() {
		return voucherReserva;
	}
	
	public void setVoucherReserva(Reserva voucherReserva) {
		this.voucherReserva = voucherReserva;
	}
	
	@Column(allowsNull="false")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Usuario")
	private String voucherUsuario;
	
	public String getVoucherUsuario() {
		return voucherUsuario;
	}
	
	public void setVoucherUsuario(String voucherUsuario) {
		this.voucherUsuario=voucherUsuario;
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "voucherFechaIngreso")
	public Voucher actualizarFechas(@ParameterLayout(named="Fecha Ingreso") final Date voucherFechaIngreso,
			@ParameterLayout(named="Fecha Egreso") final Date voucherFechaEgreso) {
		TipoPrecio tipoPrecio = TipoPrecio.Activo;
		if(getVoucherReserva().getClass()==ReservaAfiliado.class) {
			ReservaAfiliado rf = (ReservaAfiliado) getVoucherReserva();
			if(rf.getReservaCliente().getAfiliadoActivo()==true) {
				tipoPrecio = TipoPrecio.Activo;
			}
			else {
				tipoPrecio = TipoPrecio.Retirado;
			}
		}
		else {
			tipoPrecio = TipoPrecio.No_Afiliado;
		}
		setVoucherFechaIngreso(voucherFechaIngreso);
		setVoucherFechaEgreso(voucherFechaEgreso);
		setVoucherPrecioTotal(voucherRepository.calcularPrecioTotal(getVoucherProducto(), voucherFechaIngreso, voucherFechaEgreso, tipoPrecio));
		setVoucherCantidadNoches(voucherRepository.calcularCantidadDeNoches(voucherFechaIngreso, voucherFechaEgreso));
		return this;
	}
	
	public Date default0ActualizarFechas() {
		return getVoucherFechaIngreso();
	}
	
	public Date default1ActualizarFechas() {
		return getVoucherFechaEgreso();
	}
	
	public String validateActualizarFechas(final Date voucherFechaIngreso, final Date voucherFechaEgreso) {
		if(voucherFechaIngreso.after(voucherFechaEgreso))
			return "La fecha de salida no puede ser anterior a la de entrada";
		if(voucherRepository.corroborarDisponibilidadActualizar(getVoucherProducto(), voucherFechaIngreso, voucherFechaEgreso, this)==false) {
			return "El cambio no se puede realizar, debido a que ya existe una reserva hecha en las fechas seleccionadas";
		}
		return "";
	}

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "voucherCantidadPasajeros")
	public Voucher actualizarCantidadPasajeros(@ParameterLayout(named="Cantidad de pasajeros")final int voucherCantidadPasajeros) {
		setVoucherCantidadPasajeros(voucherCantidadPasajeros);
		return this;
	}
	
	public int default0ActualizarCantidadPasajeros() {
		return getVoucherCantidadPasajeros();
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "voucherPrecioTotal")
	public Voucher actualizarPrecioTotal(@ParameterLayout(named="Precio Total")final Double voucherPrecioTotal ) {
		setVoucherPrecioTotal(voucherPrecioTotal);
		return this;
	}
	
	public Double default0ActualizarPrecioTotal() {
		return getVoucherPrecioTotal();
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "voucherObservaciones")
	public Voucher actualizarObservaciones(@Nullable @ParameterLayout(named="Observaciones",multiLine=6) @Parameter(optionality=Optionality.OPTIONAL)final String voucherObservaciones) {
		setVoucherObservaciones(voucherObservaciones);
		return this;
	}
	
	public String default0ActualizarObservaciones() {
		return getVoucherObservaciones();
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "voucherEstado")
	public Voucher actualizarEstado(@ParameterLayout(named="Estado") final EstadoVoucher voucherEstado) {
		setVoucherEstado(voucherEstado);
		return this;
	}
	
	public EstadoVoucher default0ActualizarEstado() {
		return getVoucherEstado();
	}
	
	public String validateActualizarEstado(final EstadoVoucher voucherEstado) {
		if(voucherEstado==EstadoVoucher.prereserva||voucherEstado==EstadoVoucher.reservado) {
			if(voucherRepository.corroborarDisponibilidadActualizar(getVoucherProducto(), getVoucherFechaIngreso(), getVoucherFechaEgreso(), this)==false) {
				return "El cambio no se puede realizar, debido a que ya existe una reserva hecha en las fechas seleccionadas";
			}
		}
		return "";
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return "Producto: "+getVoucherProducto()+" Fecha Ingreso: "+sdf.format(getVoucherFechaIngreso())+" Fecha Egreso "+sdf.format(getVoucherFechaEgreso());
	}
	
	@Override
	public int compareTo(Voucher o) {
		return this.getVoucherFechaIngreso().compareTo(o.getVoucherFechaIngreso());
	}
	
	@Action(publishing=Publishing.ENABLED, invokeOn=InvokeOn.OBJECT_AND_COLLECTION)
	public Voucher autorizarVoucher() {
		this.getVoucherEstado().autorizar(this);
		return this;
	}
	
	@Action(publishing=Publishing.ENABLED, invokeOn=InvokeOn.OBJECT_AND_COLLECTION)
	public Voucher desautorizarVoucher() {
		this.getVoucherEstado().desautorizar(this);
		return this;
	}
	
	@Inject
	ProductoRepository productoRepository;
	
	@Inject
	VoucherRepository voucherRepository;

}
