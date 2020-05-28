package domainapp.modules.simple.dom.voucher;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.user.UserService;

import domainapp.modules.simple.dom.preciohistorico.TipoPrecio;
import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.producto.ProductoRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Voucher.class, objectType="simple.VoucherMenu")
@DomainServiceLayout(named = "Reserva", menuOrder = "40")
public class VoucherMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Crear Voucher")
	@MemberOrder(sequence = "1")
	public Voucher crear(@ParameterLayout(named="Producto")final Producto voucherProducto,
			@ParameterLayout(named="Fecha Ingreso")final Date voucherFechaIngreso,
			@ParameterLayout(named="Fecha Egreso")final Date voucherFechaEgreso,
			@ParameterLayout(named="Cantidad de Pasajeros")final int voucherCantidadPasajeros,
			@ParameterLayout(named="Observaciones")final String voucherObservaciones) {
		return voucherRepository.crear(voucherProducto, voucherFechaIngreso, voucherFechaEgreso, voucherCantidadPasajeros, 
				voucherObservaciones, obtenerUsuario(), TipoPrecio.Activo);
	}
	
	public List<Producto> choices0Crear(){
		return productoRepository.listarHabilitados();
	}
	
	public String validateCrear(final Producto voucherProducto, final Date voucherFechaIngreso, final Date voucherFechaEgreso,
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
    
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Listar Vouchers")
	@MemberOrder(sequence = "2")
    public List<Voucher> listar(){
    	return voucherRepository.listar();
    }
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Listar Vouchers por Estado")
	@MemberOrder(sequence = "3")
	public List<Voucher> listarVoucherPorEstado(EstadoVoucher voucherEstado){
		return voucherRepository.buscarVoucherPorEstado(voucherEstado);
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Listar Vouchers por Productos y estados")
	@MemberOrder(sequence = "4")
	public List<Voucher> buscarVoucherPorProductoYEstado(
			@ParameterLayout(named="Estado") final EstadoVoucher voucherEstado, 
			@ParameterLayout(named="Producto") final Producto voucherProducto,
			@ParameterLayout(named="Fecha Desde") final Date voucherFechaDesde, 
			@ParameterLayout(named="Fecha Hasta") final Date voucherFechaHasta){
		return voucherRepository.buscarVoucherPorProductoYEstado(voucherEstado, voucherProducto, voucherFechaDesde, voucherFechaHasta);
	}
	
	public List<Producto> choices1BuscarVoucherPorProductoYEstado(){
		return productoRepository.listarHabilitados();
	}
	
	@Inject
	VoucherRepository voucherRepository;
	
	@Inject
	UserService userService;
	
	@Inject
	ProductoRepository productoRepository;
	
}
