package domainapp.modules.simple.dom.reserva;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dom.afiliados.Afiliado;
import domainapp.modules.simple.dom.afiliados.AfiliadoRepository;
import domainapp.modules.simple.dom.reportes.ReporteRepository;
import domainapp.modules.simple.dom.reportes.VoucherAfiliadoReporte;
import domainapp.modules.simple.dom.reportes.VoucherEmpresaReporte;
import domainapp.modules.simple.dom.reportes.VoucherNoAfiliadoReporte;
import domainapp.modules.simple.dom.reservaafiliado.ReservaAfiliado;
import domainapp.modules.simple.dom.reservaempresa.ReservaEmpresa;
import domainapp.modules.simple.dom.reservanoafiliado.ReservaNoAfiliado;
import domainapp.modules.simple.dom.voucher.EstadoVoucher;
import domainapp.modules.simple.dom.voucher.Voucher;
import domainapp.modules.simple.dom.voucher.VoucherRepository;
import net.sf.jasperreports.engine.JRException;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Reserva")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "reservaId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "buscarPorCodigo", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.modules.simple.dom.reserva.Reserva " + "WHERE reservaCodigo == :reservaCodigo"),
		@javax.jdo.annotations.Query(name = "listarHabilitados", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.modules.simple.dom.reserva.Reserva "
				+ "WHERE reservaHabilitado == :reservaHabilitado ") })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public abstract class Reserva {

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Codigo")
	private int reservaCodigo;
	
	public int getReservaCodigo() {
		return reservaCodigo;
	}
	
	public void setReservaCodigo(int reservaCodigo) {
		this.reservaCodigo = reservaCodigo;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha")
	private Date reservaFecha;
	
	public Date getReservaFecha() {
		return reservaFecha;
	}
	
	public void setReservaFecha(Date reservaFecha) {
		this.reservaFecha = reservaFecha;
	}
	
	@Persistent (mappedBy="voucherReserva")
	@Property(editing = Editing.DISABLED) //OJO ACAAAA
	@CollectionLayout(named = "Vouchers")
	private List<Voucher> reservaListaVoucher;
	
	public List<Voucher> getReservaListaVoucher(){
		return reservaListaVoucher;
	}
	
	public void setReservaListaVoucher(List<Voucher> reservaListaVoucher) {
		this.reservaListaVoucher = reservaListaVoucher;
	}
	
	@PropertyLayout(named = "Precio Total",hidden=Where.ALL_TABLES)
	public Double getReservaPrecioTotal() {
		Double precio = 0.0;
		for(int indice = 0;indice<getReservaListaVoucher().size();indice++) {
			if((getReservaListaVoucher().get(indice).getVoucherEstado()==EstadoVoucher.reservado)||
					getReservaListaVoucher().get(indice).getVoucherEstado()==EstadoVoucher.prereserva) {
				precio = precio + getReservaListaVoucher().get(indice).getVoucherPrecioTotal();
			};
		}
		return precio;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Memo",hidden=Where.ALL_TABLES)
	private String reservaMemo;
	
	public String getReservaMemo() {
		return reservaMemo;
	}
	
	public void setReservaMemo(String reservaMemo) {
		this.reservaMemo = reservaMemo;
	}
	
	@Action(publishing=Publishing.ENABLED)
	@ActionLayout(named="Confirmar Presupuesto")
	public Reserva confirmarReserva() {
		for(int indice = 0;indice<getReservaListaVoucher().size();indice++) {
			if(getReservaListaVoucher().get(indice).getVoucherProducto().getProductoAlojamientoPropio()==true) {
				if(voucherRepository.corroborarDisponibilidadActualizar(getReservaListaVoucher().get(indice).getVoucherProducto(), 
						getReservaListaVoucher().get(indice).getVoucherFechaIngreso(), 
						getReservaListaVoucher().get(indice).getVoucherFechaEgreso(), getReservaListaVoucher().get(indice))==true) {
					getReservaListaVoucher().get(indice).getVoucherEstado().confirmar(getReservaListaVoucher().get(indice));
				}
				else {
					messageService.warnUser("NO HAY DISPONIBILIDAD");
				}
			}
			else {
				getReservaListaVoucher().get(indice).getVoucherEstado().confirmar(getReservaListaVoucher().get(indice));
			}
			if(getReservaListaVoucher().get(indice).getVoucherEstado()==EstadoVoucher.prereserva) {
				messageService.warnUser("PRERESERVA PENDIENTE DE AUTORIZACION");
			}
		}
		
		return this;
	}

	@Action(publishing=Publishing.ENABLED)
	@ActionLayout(named="Anular Reserva")
	public Reserva anularReserva() {
		for(int indice=0;indice<getReservaListaVoucher().size();indice++) {
			getReservaListaVoucher().get(indice).getVoucherEstado().anular(getReservaListaVoucher().get(indice));
		}
		return this;
	}

    @Action(semantics = SemanticsOf.SAFE, publishing=Publishing.ENABLED)
	public Blob imprimirVoucher(
            @ParameterLayout(named="Voucher: ") final Voucher voucher) throws JRException, IOException {
    	
    	List<Object> objectsReport = new ArrayList<Object>();
    	
    	String jrxml = "";
    	
    	if(voucher.getVoucherReserva().getClass()==ReservaAfiliado.class) {
        	ReservaAfiliado r = (ReservaAfiliado) voucher.getVoucherReserva();
        	Afiliado a = afiliadoRepository.buscarPorId(r.getClienteId());
    		VoucherAfiliadoReporte voucherAfiliadoReporte = new VoucherAfiliadoReporte();
    			
    		voucherAfiliadoReporte.setReservaCodigo(voucher.getVoucherReserva().getReservaCodigo());
    		voucherAfiliadoReporte.setReservaFecha(voucher.getVoucherReserva().getReservaFecha());
    		voucherAfiliadoReporte.setReservaCliente(a);
    		voucherAfiliadoReporte.setPersonaJuridicaDni(a.getPersonaJuridicaDni());
    		voucherAfiliadoReporte.setPersonaTelefono(a.getPersonaTelefono());
    		voucherAfiliadoReporte.setPersonaMail(a.getPersonaMail());
    		voucherAfiliadoReporte.setVoucherProducto(voucher.getVoucherProducto());
    		voucherAfiliadoReporte.setVoucherFechaIngreso(voucher.getVoucherFechaIngreso());
    		voucherAfiliadoReporte.setVoucherFechaEgreso(voucher.getVoucherFechaEgreso());
    		voucherAfiliadoReporte.setVoucherCantidadNoches(voucher.getVoucherCantidadNoches());
    		voucherAfiliadoReporte.setVoucherCantidadPasajeros(voucher.getVoucherCantidadPasajeros());
    		voucherAfiliadoReporte.setVoucherPrecioTotal(voucher.getVoucherPrecioTotal());
    		if(voucher.getVoucherObservaciones()==null) {
    			voucherAfiliadoReporte.setVoucherObservaciones(" ");
    		} else {
    			voucherAfiliadoReporte.setVoucherObservaciones(voucher.getVoucherObservaciones());
    		}
    		if(voucher.getVoucherProducto().getProductoPolitica()==null) {
    			voucherAfiliadoReporte.setPoliticasTexto(" ");
    		}else {
    			voucherAfiliadoReporte.setPoliticasTexto(voucher.getVoucherProducto().getProductoPolitica().getPoliticaTexto());
    		}
    		objectsReport.add(voucherAfiliadoReporte);
    		jrxml = "VoucherAfiliado.jrxml";
    	}
    	
    	if(voucher.getVoucherReserva().getClass()==ReservaNoAfiliado.class) {
        	ReservaNoAfiliado r = (ReservaNoAfiliado) voucher.getVoucherReserva();

        	VoucherNoAfiliadoReporte voucherNoAfiliadoReporte = new VoucherNoAfiliadoReporte();
    			
    		voucherNoAfiliadoReporte.setReservaCodigo(voucher.getVoucherReserva().getReservaCodigo());
    		voucherNoAfiliadoReporte.setReservaFecha(voucher.getVoucherReserva().getReservaFecha());
    		voucherNoAfiliadoReporte.setReservaCliente(r.getReservaCliente());
    		voucherNoAfiliadoReporte.setPersonaJuridicaDni(r.getReservaCliente().getPersonaJuridicaDni());
    		voucherNoAfiliadoReporte.setPersonaTelefono(r.getReservaCliente().getPersonaTelefono());
    		voucherNoAfiliadoReporte.setPersonaMail(r.getReservaCliente().getPersonaMail());
    		voucherNoAfiliadoReporte.setVoucherProducto(voucher.getVoucherProducto());
    		voucherNoAfiliadoReporte.setVoucherFechaIngreso(voucher.getVoucherFechaIngreso());
    		voucherNoAfiliadoReporte.setVoucherFechaEgreso(voucher.getVoucherFechaEgreso());
    		voucherNoAfiliadoReporte.setVoucherCantidadNoches(voucher.getVoucherCantidadNoches());
    		voucherNoAfiliadoReporte.setVoucherCantidadPasajeros(voucher.getVoucherCantidadPasajeros());
    		voucherNoAfiliadoReporte.setVoucherPrecioTotal(voucher.getVoucherPrecioTotal());
    		if(voucher.getVoucherObservaciones()==null) {
    			voucherNoAfiliadoReporte.setVoucherObservaciones(" ");
    		} else {
    			voucherNoAfiliadoReporte.setVoucherObservaciones(voucher.getVoucherObservaciones());
    		}
    		if(voucher.getVoucherProducto().getProductoPolitica()==null) {
    			voucherNoAfiliadoReporte.setPoliticasTexto(" ");
    		}else {
    			voucherNoAfiliadoReporte.setPoliticasTexto(voucher.getVoucherProducto().getProductoPolitica().getPoliticaTexto());
    		}
    		objectsReport.add(voucherNoAfiliadoReporte);
    		jrxml = "VoucherNoAfiliado.jrxml";
    	}

    	if(voucher.getVoucherReserva().getClass()==ReservaEmpresa.class) {
        	ReservaEmpresa r = (ReservaEmpresa) voucher.getVoucherReserva();
        	
    		VoucherEmpresaReporte voucherEmpresaReporte = new VoucherEmpresaReporte();
    			
    		voucherEmpresaReporte.setReservaCodigo(voucher.getVoucherReserva().getReservaCodigo());
    		voucherEmpresaReporte.setReservaFecha(voucher.getVoucherReserva().getReservaFecha());
    		voucherEmpresaReporte.setReservaCliente(r.getReservaCliente());
    		voucherEmpresaReporte.setPersonaCuitCuil(r.getReservaCliente().getPersonaCuitCuil());
    		voucherEmpresaReporte.setPersonaTelefono(r.getReservaCliente().getPersonaTelefono());
    		voucherEmpresaReporte.setPersonaMail(r.getReservaCliente().getPersonaMail());
    		voucherEmpresaReporte.setVoucherProducto(voucher.getVoucherProducto());
    		voucherEmpresaReporte.setVoucherFechaIngreso(voucher.getVoucherFechaIngreso());
    		voucherEmpresaReporte.setVoucherFechaEgreso(voucher.getVoucherFechaEgreso());
    		voucherEmpresaReporte.setVoucherCantidadNoches(voucher.getVoucherCantidadNoches());
    		voucherEmpresaReporte.setVoucherCantidadPasajeros(voucher.getVoucherCantidadPasajeros());
    		voucherEmpresaReporte.setVoucherPrecioTotal(voucher.getVoucherPrecioTotal());
    		if(voucher.getVoucherObservaciones()==null) {
    			voucherEmpresaReporte.setVoucherObservaciones(" ");
    		} else {
    			voucherEmpresaReporte.setVoucherObservaciones(voucher.getVoucherObservaciones());
    		}
    		if(voucher.getVoucherProducto().getProductoPolitica()==null) {
    			voucherEmpresaReporte.setPoliticasTexto(" ");
    		}else {
    			voucherEmpresaReporte.setPoliticasTexto(voucher.getVoucherProducto().getProductoPolitica().getPoliticaTexto());
    		}
    		objectsReport.add(voucherEmpresaReporte);
    		jrxml = "VoucherEmpresa.jrxml";
    	}
		
		//String nombreArchivo = "VoucherAfiliado_"+this.getVoucherReserva().getReservaCodigo()+"_"+new SimpleDateFormat("dd/MM/yyyy").format(this.getVoucherFechaEntrada());
		String nombreArchivo = "Voucher_"+voucher.getVoucherReserva().getReservaCodigo()+"_"+new SimpleDateFormat("dd/MM/yyyy").format(voucher.getVoucherFechaIngreso());
		ReporteRepository r = new ReporteRepository();
		return r.imprimirReporteIndividual(objectsReport, jrxml, nombreArchivo);
    }
    
	public String validateImprimirVoucher(Voucher voucher) {
		if (voucher.getVoucherEstado()!=EstadoVoucher.reservado)
			return "NO SE PUEDE IMPRIMIR EL VOUCHER PORQUE SU ESTADO NO ES RESERVADO";
		return "";
	}
	
	public List<Voucher> choices0ImprimirVoucher(){
		List<Voucher> lista = new ArrayList<>();
		for(int indice = 0; indice<this.getReservaListaVoucher().size(); indice++) {
			if(this.getReservaListaVoucher().get(indice).getVoucherEstado()==EstadoVoucher.reservado)
				lista.add(this.getReservaListaVoucher().get(indice));
		}
		return lista;
	}
	
	@Inject
	VoucherRepository voucherRepository;
	
	@Inject
	MessageService messageService;
	
	@Inject
	AfiliadoRepository afiliadoRepository;
	
}
