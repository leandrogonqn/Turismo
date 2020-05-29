package domainapp.modules.simple.dom.reserva;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.localidad.Localidad;
import domainapp.modules.simple.dom.localidad.LocalidadRepository;
import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.producto.ProductoRepository;
import domainapp.modules.simple.dom.voucher.VoucherRepository;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Reserva.class)
public class ReservaRepository {

	public List<Reserva> listar() {
		return repositoryService.allInstances(Reserva.class);
	}

	public List<Reserva> listarHabilitados(final boolean reservaHabilitado) {
		return repositoryService.allMatches(new QueryDefault<>(Reserva.class, "listarHabilitados", "reservaHabilitado", reservaHabilitado));
	}
	
	public List<Reserva> buscarPorCodigo(final int reservaCodigo) {
		return repositoryService
				.allMatches(new QueryDefault<>(Reserva.class, "buscarPorCodigo", "reservaCodigo", reservaCodigo));
	}
	
	public List<FechasDisponibles> listarDisponibilidad(Date fechaDesde, Date fechaHasta){
		List<Producto> listaProductos = productoRepository.listarHabilitados();
		fechaDesde.setHours(12);
		fechaHasta.setHours(12);
		List<FechasDisponibles> listaFechasDisponibles = new ArrayList<>();
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
	
	public String validateListarDisponibilidad(final Date fechaDesde,
			final Date fechaHasta) {
		if (fechaDesde.after(fechaHasta)||fechaDesde.equals(fechaHasta))
			return "La fecha desde no puede ser anterior o igual a la fecha hasta";
		return "";
	}
	
	//cuando ya este creado el viewmodel fechasDisponibles hacer metodo listarDisponibilidad que devuelve una lista de fechasDisponibles
	
	@Inject
	RepositoryService repositoryService;
	
	@Inject
	ProductoRepository productoRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
	
	@Inject
	VoucherRepository voucherRepository;
	
}
