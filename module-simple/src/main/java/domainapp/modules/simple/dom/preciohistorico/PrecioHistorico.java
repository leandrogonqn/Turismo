package domainapp.modules.simple.dom.preciohistorico;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import domainapp.modules.simple.dom.producto.Producto;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "PrecioHistorico")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "precioHistoricoId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "listarPreciosPorProducto", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.modules.simple.dom.preciohistorico.PrecioHistorico " + "WHERE precioHistoricoProducto == :precioHistoricoProducto &&  precioHistoricoHabilitado == :precioHistoricoHabilitado "),
		@javax.jdo.annotations.Query(name = "listarPreciosPorProductoPorTipoDeAfiliado", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.modules.simple.dom.preciohistorico.PrecioHistorico " + "WHERE precioHistoricoProducto == :precioHistoricoProducto && precioHistoricoTipoPrecio == :precioHistoricoTipoPrecio &&  precioHistoricoHabilitado == :precioHistoricoHabilitado ")})
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class PrecioHistorico implements Comparable<PrecioHistorico> {
	
	public PrecioHistorico() {
		// TODO Auto-generated constructor stub
	}
	
	public PrecioHistorico(final Producto precioHistoricoProducto, final Date precioHistoricoFechaDesde, final Date precioHistoricoFechaHasta,
			final TipoPrecio precioHistoricoTipoPrecio, final Double precioHistoricoPrecio) {
		setPrecioHistoricoProducto(precioHistoricoProducto);
		setPrecioHistoricoFechaDesde(precioHistoricoFechaDesde);
		setPrecioHistoricoFechaHasta(precioHistoricoFechaHasta);
		setPrecioHistoricoTipoPrecio(precioHistoricoTipoPrecio);
		setPrecioHistoricoPrecio(precioHistoricoPrecio);
		setPrecioHistoricoHabilitado(true);
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Producto")
	private Producto precioHistoricoProducto;
	
	public Producto getPrecioHistoricoProducto() {
		return precioHistoricoProducto;
	}
	
	public void setPrecioHistoricoProducto(Producto precioHistoricoProducto) {
		this.precioHistoricoProducto=precioHistoricoProducto;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha desde")
	private Date precioHistoricoFechaDesde;
	
	public Date getPrecioHistoricoFechaDesde() {
		return precioHistoricoFechaDesde;
	}
	
	public void setPrecioHistoricoFechaDesde(Date precioHistoricoFechaDesde) {
		this.precioHistoricoFechaDesde = precioHistoricoFechaDesde;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha hasta")
	private Date precioHistoricoFechaHasta;
	
	public Date getPrecioHistoricoFechaHasta() {
		return precioHistoricoFechaHasta;
	}
	
	public void setPrecioHistoricoFechaHasta(Date precioHistoricoFechaHasta) {
		this.precioHistoricoFechaHasta=precioHistoricoFechaHasta;
		
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Tipo precio")
	private TipoPrecio precioHistoricoTipoPrecio;
	
	public TipoPrecio getPrecioHistoricoTipoPrecio() {
		return precioHistoricoTipoPrecio;
	}
	
	public void setPrecioHistoricoTipoPrecio(TipoPrecio precioHistoricoTipoPrecio) {
		this.precioHistoricoTipoPrecio = precioHistoricoTipoPrecio;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Precio")
	private Double precioHistoricoPrecio;
	
	public Double getPrecioHistoricoPrecio() {
		return precioHistoricoPrecio;
	}
	
	public void setPrecioHistoricoPrecio(Double precioHistoricoPrecio) {
		this.precioHistoricoPrecio = precioHistoricoPrecio;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Habilitado", hidden=Where.ALL_TABLES)
	private boolean precioHistoricoHabilitado;
	
	public boolean getPrecioHistoricoHabilitado() {
		return precioHistoricoHabilitado;
	}
	
	public void setPrecioHistoricoHabilitado(boolean precioHistoricoHabilitado) {
		this.precioHistoricoHabilitado = precioHistoricoHabilitado;
	}
	
	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarPrecioHistorico() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setPrecioHistoricoHabilitado(false);
	}

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "precioHistoricoFechaDesde")	
	public PrecioHistorico actualizarPrecioHistoricoFecha(@ParameterLayout(named="Fecha Desde")final Date precioHistoricoFechaDesde,
			@ParameterLayout(named="Fecha Hasta") final Date precioHistoricoFechaHasta) {
		setPrecioHistoricoFechaDesde(precioHistoricoFechaDesde);
		setPrecioHistoricoFechaHasta(precioHistoricoFechaHasta);
		return this;
	}
	
	public Date default0ActualizarPrecioHistoricoFecha() {
		return getPrecioHistoricoFechaDesde();
	}
	
	public Date default1ActualizarPrecioHistoricoFecha() {
		return getPrecioHistoricoFechaHasta();
	}
	
	public String validateActualizarPrecioHistoricoFecha(final Date precioHistoricoFechaDesde, final Date precioHistoricoFechaHasta) {
		if(precioHistoricoFechaDesde.after(precioHistoricoFechaHasta))
			return "la fecha desde no puede ser posterior a fecha hasta";
		if(precioHistoricoRepository.verificarActualizarPrecio(getPrecioHistoricoProducto(), getPrecioHistoricoTipoPrecio(), 
				precioHistoricoFechaDesde, precioHistoricoFechaHasta, this)==false) {
			return "ERROR: Superposicion de fechas con precios ya cargados";
		}
		return "";
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "precioHistoricoTipoPrecio")	
	public PrecioHistorico actualizarPrecioHistoricoTipoPrecio(@ParameterLayout(named="Tipo Precio")final TipoPrecio precioHistoricoTipoPrecio) {
		setPrecioHistoricoTipoPrecio(precioHistoricoTipoPrecio);
		return this;
	}
	
	public TipoPrecio default0ActualizarPrecioHistoricoTipoPrecio() {
		return getPrecioHistoricoTipoPrecio();
	}
	
	public String validateActualizarPrecioHistoricoTipoPrecio(TipoPrecio precioHistoricoTipoPrecio) {
		if(precioHistoricoRepository.verificarActualizarPrecio(getPrecioHistoricoProducto(), precioHistoricoTipoPrecio, 
				getPrecioHistoricoFechaDesde(), getPrecioHistoricoFechaHasta(), this)==false) {
			return "ERROR: Superposicion de fechas con precios ya cargados";
		}
		return "";
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "precioHistoricoPrecio")
	public PrecioHistorico actualizarPrecioHistoricoPrecio(@ParameterLayout(named="Precio")final Double precioHistoricoPrecio) {
		setPrecioHistoricoPrecio(precioHistoricoPrecio);
		return this;
	}
	
	public Double default0ActualizarPrecioHistoricoPrecio() {
		return getPrecioHistoricoPrecio();
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "precioHistoricoPrecio")
	public PrecioHistorico actualizarPrecioHistoricoHabilitado(@ParameterLayout(named="Habilitado")final boolean precioHistoricoHabilitado) {
		setPrecioHistoricoHabilitado(precioHistoricoHabilitado);
		return this;
	}
	
	public boolean default0ActualizarPrecioHistoricoHabilitado() {
		return getPrecioHistoricoHabilitado();
	}
	
	public String validateActualizarPrecioHistoricoHabilitado(final boolean precioHistoricoHabilitado) {
		if(precioHistoricoHabilitado==true) {
			if(precioHistoricoRepository.verificarActualizarPrecio(getPrecioHistoricoProducto(), getPrecioHistoricoTipoPrecio(), 
					getPrecioHistoricoFechaDesde(), getPrecioHistoricoFechaHasta(), this)==false) {
				return "ERROR: Superposicion de fechas con precios ya cargados";
			}
		}
		return "";
	}
	
	// region > toString, compareTo
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return getPrecioHistoricoProducto().toString() + " Precio: " + getPrecioHistoricoPrecio().toString() + " Desde: " + 
		sdf.format(getPrecioHistoricoFechaDesde())+ " Hasta: "+sdf.format(getPrecioHistoricoFechaHasta());
	}

	@Override
	public int compareTo(final PrecioHistorico precioHistoricoServicio) {
		return this.precioHistoricoFechaDesde.compareTo(precioHistoricoServicio.precioHistoricoFechaDesde);
	}
	
	@Inject
	TitleService titleService;
	
	@Inject
	MessageService messageService;
	
	@Inject
	PrecioHistoricoRepository precioHistoricoRepository;

}
