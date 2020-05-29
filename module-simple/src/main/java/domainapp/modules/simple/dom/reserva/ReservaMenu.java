package domainapp.modules.simple.dom.reserva;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import domainapp.modules.simple.dom.localidad.Localidad;
import domainapp.modules.simple.dom.localidad.LocalidadRepository;
import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.producto.ProductoRepository;
import domainapp.modules.simple.dom.voucher.EstadoVoucher;
import domainapp.modules.simple.dom.voucher.Voucher;
import domainapp.modules.simple.dom.voucher.VoucherRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Reserva.class, objectType="simple.ReservaMenu")
@DomainServiceLayout(named = "Reserva", menuOrder = "60")
public class ReservaMenu {
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todas las Reservas")
	@MemberOrder(sequence = "60")
	public List<Reserva> listar() {
		return reservaRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar reserva por codigo")
	@MemberOrder(sequence = "65")
	public List<Reserva> buscarPorCodigo(@ParameterLayout(named = "Codigo") final int reservaCodigo) {
		return reservaRepository.buscarPorCodigo(reservaCodigo);
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Listar Vouchers por Localidad")
	@MemberOrder(sequence = "70")
	public List<Voucher> listarVoucherPorLocalidad(@ParameterLayout(named="Localidad")final Localidad voucherLocalidad,
			@ParameterLayout(named="Fecha Desde") final Date voucherFechaDesde, 
			@ParameterLayout(named="Fecha Hasta") final Date voucherFechaHasta){
		List<Producto> listaProducto = productoRepository.buscarProductoPorLocalidad(voucherLocalidad);
		List<Voucher> listaVoucher = new ArrayList<Voucher>();
		for(int indice=0;indice<listaProducto.size();indice++) {
			listaVoucher.addAll(voucherRepository.buscarVoucherPorProductoYEstado(EstadoVoucher.prereserva, listaProducto.get(indice), voucherFechaDesde, voucherFechaHasta));
			listaVoucher.addAll(voucherRepository.buscarVoucherPorProductoYEstado(EstadoVoucher.reservado, listaProducto.get(indice), voucherFechaDesde, voucherFechaHasta));
		}
		return listaVoucher;
	}
	
	public List<Localidad> choices0ListarVoucherPorLocalidad(){
		return localidadRepository.listar();
	}
	
	public String validateListarVoucherPorLocalidad(final Localidad voucherLocalidad, final Date fechaDesde,
			final Date fechaHasta) {
		if (fechaDesde.after(fechaHasta)/*||fechaDesde.equals(fechaHasta)*/)
			return "La fecha desde no puede ser anterior o igual a la fecha hasta";
		return "";
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Listar Vouchers por Productos")
	@MemberOrder(sequence = "80")
	public List<Voucher> listarVoucherPorProducto(
			@ParameterLayout(named="Producto") final Producto voucherProducto,
			@ParameterLayout(named="Fecha Desde") final Date voucherFechaDesde, 
			@ParameterLayout(named="Fecha Hasta") final Date voucherFechaHasta){
		List<Voucher> listaVoucher = voucherRepository.buscarVoucherPorProductoYEstado(EstadoVoucher.reservado, voucherProducto, voucherFechaDesde, voucherFechaHasta);
		listaVoucher.addAll(voucherRepository.buscarVoucherPorProductoYEstado(EstadoVoucher.prereserva, voucherProducto, voucherFechaDesde, voucherFechaHasta));
		return listaVoucher;
	}
	
	public List<Producto> choices0ListarVoucherPorProducto(){
		return productoRepository.listarHabilitados();
	}
	
	public String validateListarVoucherPorProducto(final Producto voucherProducto, final Date fechaDesde,
			final Date fechaHasta) {
		if (fechaDesde.after(fechaHasta)/*||fechaDesde.equals(fechaHasta)*/)
			return "La fecha desde no puede ser anterior o igual a la fecha hasta";
		return "";
	}
	
	@SuppressWarnings("deprecation")
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-calendar", named = "Listar Disponibilidad Por Productos")
	@MemberOrder(sequence = "50")
	public List<FechasDisponibles> listarDisponibilidadPorProducto(@ParameterLayout(named="Producto")final Producto producto,
			@ParameterLayout(named="Fecha Desde")final Date fechaDesde,
			@ParameterLayout(named="Fecha Hasta")final Date fechaHasta) {
		List<FechasDisponibles> listaFechasDisponibles = new ArrayList<>();
		if(producto.getProductoAlojamientoPropio()==true) {
			fechaDesde.setHours(12);
			fechaHasta.setHours(12);
			Date fechaAux = fechaDesde;
			while(fechaAux.before(fechaHasta)) {
				if(voucherRepository.corroborarDisponibilidadCrear(producto, fechaAux, fechaAux)==true) {
					FechasDisponibles fechaDisponible = new FechasDisponibles();
					fechaDisponible.setProducto(producto);
					fechaDisponible.setFechaDesde(fechaAux);
					while(voucherRepository.corroborarDisponibilidadCrear(producto, fechaAux, fechaAux)==true) {
						fechaAux = voucherRepository.sumarUnDia(fechaAux);
						if(fechaAux.after(fechaHasta)) {
							fechaDisponible.setMemo("");
							break;
						}
						fechaDisponible.setFechaHasta(fechaAux);
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						fechaDisponible.setMemo("Disponibilidad hasta las 10:00 a.m.  del día "+sdf.format(fechaAux));
					}
					listaFechasDisponibles.add(fechaDisponible);
				}
				fechaAux = voucherRepository.sumarUnDia(fechaAux);
			}
		}
		else {
			FechasDisponibles fechasDisponibles = new FechasDisponibles();
			fechasDisponibles.setProducto(producto);
			fechasDisponibles.setFechaDesde(fechaDesde);
			fechasDisponibles.setFechaHasta(fechaHasta);
			fechasDisponibles.setMemo("Consultar Disponibilidad al proveedor");
			listaFechasDisponibles.add(fechasDisponibles);
		}
		return listaFechasDisponibles;
	}
	
	public List<Producto> choices0ListarDisponibilidadPorProducto(){
		return productoRepository.listarHabilitados();
	}
	
	public String validateListarDisponibilidadPorProducto(final Producto voucherProducto, final Date fechaDesde,
			final Date fechaHasta) {
		if (fechaDesde.after(fechaHasta)||fechaDesde.equals(fechaHasta))
			return "La fecha desde no puede ser anterior o igual a la fecha hasta";
		return "";
	}
	
	@SuppressWarnings("deprecation")
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-calendar", named = "Listar Disponibilidad Por Localidad")
	@MemberOrder(sequence = "60")
	public List<FechasDisponibles> listarDisponibilidadPorLocalidad(@ParameterLayout(named="Localidad")final Localidad localidad,
			@ParameterLayout(named="Fecha Desde")final Date fechaDesde,
			@ParameterLayout(named="Fecha Hasta")final Date fechaHasta){
		fechaDesde.setHours(12);
		fechaHasta.setHours(12);
		List<FechasDisponibles> listaFechasDisponibles = new ArrayList<>();
		List<Producto> listaProductos = productoRepository.buscarProductoPorLocalidad(localidad);
		for(int indice = 0;indice<listaProductos.size();indice++) {
			if(listaProductos.get(indice).getProductoAlojamientoPropio()==true) {
				Date fechaAux = fechaDesde;
				while(fechaAux.before(fechaHasta)) {
					if(voucherRepository.corroborarDisponibilidadCrear(listaProductos.get(indice), fechaAux, fechaAux)==true) {
						FechasDisponibles fechaDisponible = new FechasDisponibles();
						fechaDisponible.setProducto(listaProductos.get(indice));
						fechaDisponible.setFechaDesde(fechaAux);
						while(voucherRepository.corroborarDisponibilidadCrear(listaProductos.get(indice), fechaAux, fechaAux)==true) {
							fechaAux = voucherRepository.sumarUnDia(fechaAux);
							if(fechaAux.after(fechaHasta)) {
								fechaDisponible.setMemo("");
								break;
							}
							fechaDisponible.setFechaHasta(fechaAux);
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							fechaDisponible.setMemo("Disponibilidad hasta las 10:00 a.m.  del día "+sdf.format(fechaAux));
						}
						listaFechasDisponibles.add(fechaDisponible);
					}
					fechaAux = voucherRepository.sumarUnDia(fechaAux);
				}
			}
			else {
				FechasDisponibles fechasDisponibles = new FechasDisponibles();
				fechasDisponibles.setProducto(listaProductos.get(indice));
				fechasDisponibles.setFechaDesde(fechaDesde);
				fechasDisponibles.setFechaHasta(fechaHasta);
				fechasDisponibles.setMemo("Consultar Disponibilidad al proveedor");
				listaFechasDisponibles.add(fechasDisponibles);
			}
		}
		return listaFechasDisponibles;
	}
	
	public List<Localidad> choices0ListarDisponibilidadPorLocalidad(){
		return localidadRepository.listar();
	}
	
	public String validateListarDisponibilidadPorLocalidad(final Localidad localidad, final Date fechaDesde,
			final Date fechaHasta) {
		if (fechaDesde.after(fechaHasta)||fechaDesde.equals(fechaHasta))
			return "La fecha desde no puede ser anterior o igual a la fecha hasta";
		return "";
	}
	//listarDisponibilidadPorLocalidad que devuelve una lista de fechasDisponibles
	
	@Inject
	ReservaRepository reservaRepository;
	
	@Inject
	VoucherRepository voucherRepository;
	
	@Inject
	ProductoRepository productoRepository;
	
	@Inject
	LocalidadRepository localidadRepository;

}
