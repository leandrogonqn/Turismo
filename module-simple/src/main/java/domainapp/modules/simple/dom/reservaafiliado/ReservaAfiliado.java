package domainapp.modules.simple.dom.reservaafiliado;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.user.UserService;

import domainapp.modules.simple.dom.afiliados.Afiliado;
import domainapp.modules.simple.dom.afiliados.AfiliadoRepository;
import domainapp.modules.simple.dom.preciohistorico.TipoPrecio;
import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.producto.ProductoRepository;
import domainapp.modules.simple.dom.reserva.Reserva;
import domainapp.modules.simple.dom.voucher.EstadoVoucher;
import domainapp.modules.simple.dom.voucher.Voucher;
import domainapp.modules.simple.dom.voucher.VoucherRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "ReservaAfiliado")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "reservaId")
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class ReservaAfiliado extends Reserva implements Comparable<Reserva>{
	
	public TranslatableString tittle() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return TranslatableString.tr("Codigo: "+getReservaCodigo()+". Cliente: "+getReservaCliente()+". Fecha Ingreso: "+ 
				sdf.format(getReservaListaVoucher().get(0).getVoucherFechaIngreso())+". Fecha Egreso: "+sdf.format(getReservaListaVoucher().get(0).getVoucherFechaEgreso()));		
	}

	public String iconName() {
		String activo;
		if(getReservaCliente().getAfiliadoActivo()==true) {
			activo = "A";
		}else {
			activo = "R";
		}
		return activo;
	}
	
	//Constructor
	public ReservaAfiliado() {
		// TODO Auto-generated constructor stub
	}
	
	public ReservaAfiliado(final int reservaCodigo, final int clienteId, final Date reservaFecha, final List<Voucher> reservaListaVoucher,
			final CanalDePago reservaCanalDePago, final String reservaMemo) {
		setReservaCodigo(reservaCodigo);
		setClienteId(clienteId);
		setReservaFecha(reservaFecha);
		setReservaListaVoucher(reservaListaVoucher);
		setReservaCanalDePago(reservaCanalDePago);
		setReservaMemo(reservaMemo);
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED, hidden=Where.EVERYWHERE)
	@PropertyLayout(named = "Cliente")
	private int clienteId;
	
	public int getClienteId() {
		return clienteId;
	}
	
	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}
	
	@ActionLayout(named="Cliente")
	public Afiliado getReservaCliente() {
		return afiliadoRepository.buscarPorId(this.clienteId);
	}
	
	@Column(allowsNull="true")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named="Canal de Pago", hidden=Where.ALL_TABLES)
	private CanalDePago reservaCanalDePago;
	
	public CanalDePago getReservaCanalDePago() {
		return reservaCanalDePago;
	}
	
	public void setReservaCanalDePago(CanalDePago reservaCanalDePago) {
		this.reservaCanalDePago = reservaCanalDePago;
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "reservaCodigo")
	public ReservaAfiliado actualizarCodigo(@ParameterLayout(named="Codigo")int reservaCodigo) {
		setReservaCodigo(reservaCodigo);
		return this;
	}
	
	public int default0ActualizarCodigo() {
		return getReservaCodigo();
	}

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "reservaFecha")
	public ReservaAfiliado actualizarFecha(@ParameterLayout(named="Fecha") Date reservaFecha) {
		setReservaFecha(reservaFecha);
		return this;
	}
	
	public Date default0ActualizarFecha() {
		return getReservaFecha();
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "reservaCanalDePago")
	public ReservaAfiliado actualizarCanalDePago(@ParameterLayout(named="Canal de pago") CanalDePago reservaCanalDePago) {
		setReservaCanalDePago(reservaCanalDePago);
		return this;
	}
	
	public CanalDePago default0ActualizarCanalDePago() {
		return getReservaCanalDePago();
	}
	
	public String validateActualizarCanalDePago(final CanalDePago reservaCanalDePago) {
		if((reservaCanalDePago==CanalDePago.Debito_Automatico)&(getReservaCliente().getAfiliadoCBU()==null)) {
			return "ERROR: CBU no cargado, elija otro canal de pago";
		}
		return "";
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "reservaMemo")
	public ReservaAfiliado actualizarMemo(@Nullable @ParameterLayout(named="Memo",multiLine=6) @Parameter(optionality=Optionality.OPTIONAL) final String reservaMemo) {
		setReservaMemo(reservaMemo);
		return this;
	}

	public String default0ActualizarMemo() {
		return getReservaMemo();
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return("Codigo: "+getReservaCodigo()+". Cliente: "+getReservaCliente()+". Fecha Ingreso: "+ 
		sdf.format(getReservaListaVoucher().get(0).getVoucherFechaIngreso())+". Fecha Egreso: "+sdf.format(getReservaListaVoucher().get(0).getVoucherFechaEgreso()));
	}

	@Override
	public int compareTo(final Reserva reserva) {
        if (getReservaCodigo()<reserva.getReservaCodigo()) {
            return -1;
        }
        if (getReservaCodigo()>reserva.getReservaCodigo()) {
            return 1;
        }	
        return 0;
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Crear Voucher",cssClass="crear")
	@MemberOrder(sequence = "1")
	public ReservaAfiliado crearVoucher(@ParameterLayout(named="Producto")final Producto voucherProducto,
			@ParameterLayout(named="Fecha Ingreso")final Date voucherFechaIngreso,
			@ParameterLayout(named="Fecha Egreso")final Date voucherFechaEgreso,
			@ParameterLayout(named="Cantidad de Pasajeros")final int voucherCantidadPasajeros,
			@Nullable @ParameterLayout(named="Observaciones", multiLine=6)@Parameter(optionality=Optionality.OPTIONAL)final String voucherObservaciones) {
		TipoPrecio t = TipoPrecio.Activo;
		if(getReservaCliente().getAfiliadoActivo()==true) {
			t = TipoPrecio.Activo;
		}
		else {
			t = TipoPrecio.Retirado;
		}
		String usuario = obtenerUsuario();
		Voucher v = voucherRepository.crear(voucherProducto, voucherFechaIngreso, voucherFechaEgreso, voucherCantidadPasajeros, 
				voucherObservaciones, usuario, t);
		this.getReservaListaVoucher().add(v);
		this.setReservaListaVoucher(this.getReservaListaVoucher());
		v.setVoucherReserva(this);
		List<Voucher> lista = new ArrayList<Voucher>();
		for(int indice = 0;indice<getReservaListaVoucher().size();indice++) {
			if(getReservaListaVoucher().get(indice).getVoucherEstado()==EstadoVoucher.reservado||
					getReservaListaVoucher().get(indice).getVoucherEstado()==EstadoVoucher.prereserva) {
				lista.add(getReservaListaVoucher().get(indice));
			}
		}
		if(!lista.isEmpty())
			confirmarReserva();
		return this;
	}
	
	public List<Producto> choices0CrearVoucher(){
		return productoRepository.listarHabilitados();
	}
	
	public String validateCrearVoucher(final Producto voucherProducto, final Date voucherFechaIngreso, final Date voucherFechaEgreso,
			final int voucherCantidadPasajeros, final String voucherObservaciones) {
		if(voucherFechaIngreso.after(voucherFechaEgreso))
			return "La fecha de ingreso debe ser antes de la fecha de egreso";
		if(voucherRepository.corroborarDisponibilidadCrear(voucherProducto, voucherFechaIngreso, voucherFechaEgreso)==false)
			return "El alojamiento ya esta reservado en las fechas seleccionadas";
		return "";
	}
	
    public String obtenerUsuario() {          
    	String usuario = userService.getUser().getName();
    	String rol = userService.getUser().getRoles().get(0).getName();
        return "Usuario: "+usuario+" -- Rol: "+rol;
    }
	
	
	@Inject
	AfiliadoRepository afiliadoRepository;
	
	@Inject
	VoucherRepository voucherRepository;
	
	@Inject
	ProductoRepository productoRepository;
	
	@Inject
	UserService userService;
	
}
