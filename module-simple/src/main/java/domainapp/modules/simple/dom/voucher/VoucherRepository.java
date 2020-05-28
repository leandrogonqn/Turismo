package domainapp.modules.simple.dom.voucher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.preciohistorico.PrecioHistorico;
import domainapp.modules.simple.dom.preciohistorico.PrecioHistoricoRepository;
import domainapp.modules.simple.dom.preciohistorico.TipoPrecio;
import domainapp.modules.simple.dom.producto.Producto;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Voucher.class)
public class VoucherRepository {

	public Voucher crear(final Producto voucherProducto, final Date voucherFechaIngreso, final Date voucherFechaEgreso,
			final int voucherCantidadPasajeros, final String voucherObservaciones, final String voucherUsuario, 
			final TipoPrecio precioHistoricoTipoPrecio) {
		int voucherCantidadNoches = calcularCantidadDeNoches(voucherFechaIngreso, voucherFechaEgreso);
		Double voucherPrecioTotal = calcularPrecioTotal(voucherProducto, voucherFechaIngreso, voucherFechaEgreso, precioHistoricoTipoPrecio);
		Voucher v = new Voucher(voucherProducto, voucherFechaIngreso, voucherFechaEgreso, voucherCantidadNoches, 
				voucherCantidadPasajeros, voucherPrecioTotal, voucherObservaciones, voucherUsuario);
		serviceRegistry.injectServicesInto(v);
		repositoryService.persist(v);
		return v;
	}
	
	public int calcularCantidadDeNoches(Date voucherFechaIngreso, Date voucherFechaEgreso) {
		int dias =(int) ((voucherFechaEgreso.getTime()-voucherFechaIngreso.getTime())/86400000);
		return dias;
	}
	
	public Double calcularPrecioTotal(final Producto voucherProducto, final Date voucherFechaIngreso, final Date voucherFechaEgreso,
			TipoPrecio precioHistoricoTipoPrecio) {
		Double precio = 0.0;
		Double precioAux = 0.0;
		Date fecha = voucherFechaIngreso;
		while(!fecha.equals(voucherFechaEgreso)) {
			precioAux = precioHistoricoRepository.mostrarPrecioPorFecha(voucherProducto, precioHistoricoTipoPrecio, fecha);
			if(precioAux==0) {
				precio = precio + traerUltimoPrecioCargado(fecha, voucherProducto, precioHistoricoTipoPrecio);
			}
			else {
				precio = precio + precioAux;
			}
			fecha=sumarUnDia(fecha);
		}
		return precio;
	}
	 
	public Date sumarUnDia(Date fecha) {
		Calendar fechasumada = Calendar.getInstance();//OJO A ESTO!!! AL GENERAR LA INSTANCIA SE SETEA CON FECHA DE HOY
		fechasumada.setTime(fecha);
		fechasumada.add(Calendar.DAY_OF_YEAR, 1);
		return fechasumada.getTime();
	}
	
	public Double traerUltimoPrecioCargado(Date fecha, Producto voucherProducto, TipoPrecio precioHistoricoTipoPrecio) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Double precio = 0.0;
		Date fechaAux = null;
		try {
			fechaAux = sdf.parse("01-01-2000");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<PrecioHistorico> lista = precioHistoricoRepository.listarPreciosPorProductoPorTipoDeAfiliado(voucherProducto, precioHistoricoTipoPrecio, true);
		Iterator<PrecioHistorico> iterar = lista.iterator();
		while(iterar.hasNext()) {
			PrecioHistorico item = iterar.next();
			if((item.getPrecioHistoricoFechaHasta().before(fecha))&(item.getPrecioHistoricoFechaHasta().after(fechaAux))) {
				precio = item.getPrecioHistoricoPrecio();
				fechaAux = item.getPrecioHistoricoFechaHasta();
			}
		}
		messageService.warnUser("PRECAUCION: Precio del dia "+ sdf.format(fecha)+ " no esta cargado, se tomo el ultimo precio cargado");
		return precio;
	}
	
	public List<Voucher> listar() {
		return repositoryService.allInstances(Voucher.class);
	}

	public List<Voucher> buscarVoucherPorEstado(EstadoVoucher voucherEstado) {
		return repositoryService.allMatches(new QueryDefault<>(Voucher.class, "buscarVoucherPorEstado", "voucherEstado", 
				voucherEstado));
	}
	
	public List<Voucher> buscarVoucherPorProductoYEstado(EstadoVoucher voucherEstado, Producto voucherProducto,
			Date voucherFechaDesde, Date voucherFechaHasta){
		List<Voucher> listaVoucher = new ArrayList<>();
		listaVoucher = repositoryService.allMatches(new QueryDefault<>(Voucher.class, "buscarVoucherPorProductoYEstado", "voucherProducto", 
				voucherProducto, "voucherEstado", voucherEstado));
		List<Voucher> list = new ArrayList<>();
		for(int indice = 0;indice<listaVoucher.size();indice++) {
			if(voucherFechaDesde.before(listaVoucher.get(indice).getVoucherFechaEgreso())&
					voucherFechaHasta.after(listaVoucher.get(indice).getVoucherFechaIngreso())) {
				list.add(listaVoucher.get(indice));
			}
		}
		return list;
	}
	
	public boolean corroborarDisponibilidadCrear(Producto voucherProducto, Date fechaIngreso, Date fechaEgreso) {
		boolean v = true;
		if (voucherProducto.getProductoAlojamientoPropio()==true) {
			List<Voucher> listaVoucher = buscarVoucherPorProductoYEstado(EstadoVoucher.prereserva, voucherProducto,
					fechaIngreso, fechaEgreso);
			listaVoucher.addAll(
					buscarVoucherPorProductoYEstado(EstadoVoucher.reservado, voucherProducto, fechaIngreso, fechaEgreso));
			if (listaVoucher.isEmpty())
				v = true;
			else
				v = false;
		}
		else
			v=true;
		return v;
	}
	
	public boolean corroborarDisponibilidadActualizar(Producto voucherProducto, Date fechaIngreso, Date fechaEgreso, Voucher voucher) {
		boolean v = false;
		if (voucherProducto.getProductoAlojamientoPropio()==true) {
			List<Voucher> listaVoucher = buscarVoucherPorProductoYEstado(EstadoVoucher.prereserva, voucherProducto,
					fechaIngreso, fechaEgreso);
			listaVoucher.addAll(
					buscarVoucherPorProductoYEstado(EstadoVoucher.reservado, voucherProducto, fechaIngreso, fechaEgreso));
			List<Voucher> list = new ArrayList<>();
			for(int indice = 0;indice<listaVoucher.size();indice++) {
				if(listaVoucher.get(indice)!=voucher) {
					list.add(listaVoucher.get(indice));
				}
			}
			if (list.isEmpty())
				v = true;
		}
		else
			v=true;
		return v;
	}
	
	@Inject
	PrecioHistoricoRepository precioHistoricoRepository;
	
	@Inject
	RepositoryService repositoryService;
	
	@Inject
	ServiceRegistry2 serviceRegistry;
	
	@Inject
	MessageService messageService;
}
