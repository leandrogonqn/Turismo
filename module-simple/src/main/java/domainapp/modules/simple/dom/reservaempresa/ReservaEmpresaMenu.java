package domainapp.modules.simple.dom.reservaempresa;

import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.user.UserService;
import domainapp.modules.simple.dom.empresa.Empresa;
import domainapp.modules.simple.dom.empresa.EmpresaRepository;
import domainapp.modules.simple.dom.preciohistorico.TipoPrecio;
import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.producto.ProductoRepository;
import domainapp.modules.simple.dom.voucher.VoucherRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = ReservaEmpresa.class, objectType="simple.ReservaEmpresaMenu")
@DomainServiceLayout(named = "Reserva", menuOrder = "40.3")
public class ReservaEmpresaMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Crear Reserva Empresa")
	@MemberOrder(sequence = "1.2")
	public ReservaEmpresa crear(@ParameterLayout(named="Codigo")final int reservaCodigo, 
			@ParameterLayout(named="Fecha reserva")final Date reservaFecha,
			@ParameterLayout(named="Cliente")final Empresa reservaCliente, 
			@ParameterLayout(named="Producto")final Producto voucherProducto,
			@ParameterLayout(named="Fecha Ingreso")final Date voucherFechaIngreso, 
			@ParameterLayout(named="Fecha Egreso")final Date voucherFechaEgreso,
			@ParameterLayout(named="Cantidad de pasajeros")final int voucherCantidadPasajeros,
			@Nullable @ParameterLayout(named="Observaciones del voucher",multiLine=6) @Parameter(optionality=Optionality.OPTIONAL)final String voucherObservaciones,
			@Nullable @ParameterLayout(named="Memo de la reserva",multiLine=6) @Parameter(optionality=Optionality.OPTIONAL)final String reservaMemo) {
		String usuario = obtenerUsuario();
		return reservaEmpresaRepository.crear(reservaCodigo, reservaFecha, reservaCliente, voucherProducto, 
				voucherFechaIngreso, voucherFechaEgreso, voucherCantidadPasajeros, TipoPrecio.No_Afiliado, voucherObservaciones, 
				reservaMemo, usuario);
	}
	
    public String obtenerUsuario() {          
    	String usuario = userService.getUser().getName();
    	String rol = userService.getUser().getRoles().get(0).getName();
        return "Usuario: "+usuario+" -- Rol: "+rol;
    }
    
    public List<Empresa> choices2Crear() {
    	return empresaRepository.listarHabilitados();
    }
    
    public List<Producto> choices3Crear() {
    	return productoRepository.listarHabilitados();
    }
    
    public String validateCrear(final int reservaCodigo, final Date reservaFecha, final Empresa reservaCliente, 
			final Producto voucherProducto, final Date voucherFechaIngreso, final Date voucherFechaEgreso, 
			final int voucherCantidadPasajeros, final String voucherObservaciones, final String reservaMemo) {
    	if(voucherFechaEgreso.before(voucherFechaIngreso))
    		return "La fecha de salida no puede ser anterior a la de entrada";
    	if(voucherRepository.corroborarDisponibilidadCrear(voucherProducto, voucherFechaIngreso, voucherFechaEgreso)==false)
    		return "El producto ya se encuentra reservado en las fechas seleccionadas";
    	return "";
    }
    
	public Date default1Crear() {
		Date hoy = new Date();
		return hoy;
	}
	
    @Inject
    ReservaEmpresaRepository reservaEmpresaRepository;
    
    @Inject
    UserService userService;
    
    @Inject
    EmpresaRepository empresaRepository;
    
    @Inject
    ProductoRepository productoRepository;
    
    @Inject
    VoucherRepository voucherRepository;
    
}
