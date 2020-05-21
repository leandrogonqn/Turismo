package domainapp.modules.simple.dom.preciohistorico;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import domainapp.modules.simple.dom.producto.Producto;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = PrecioHistorico.class)
public class PrecioHistoricoRepository {
	
	public PrecioHistorico crear(final Producto precioHistoricoProducto, final Date precioHistoricoFechaDesde, 
			final Date precioHistoricoFechaHasta, final TipoPrecio precioHistoricoTipoPrecio, final Double precioHistoricoPrecio) {
		PrecioHistorico object = new PrecioHistorico(precioHistoricoProducto, precioHistoricoFechaDesde, precioHistoricoFechaHasta, precioHistoricoTipoPrecio, precioHistoricoPrecio);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;		
	}

	public List<PrecioHistorico> listar() {
		return repositoryService.allInstances(PrecioHistorico.class);
	}
	
	public List<PrecioHistorico> listarPreciosPorProductoPorTipoDeAfiliado(final Producto precioHistoricoProducto, 
			final TipoPrecio precioHistoricoTipoPrecio, final boolean precioHistoricoHabilitado) {
		return repositoryService.allMatches(new QueryDefault<>(PrecioHistorico.class, "listarPreciosPorProductoPorTipoDeAfiliado", 
				"precioHistoricoProducto", precioHistoricoProducto, "precioHistoricoTipoPrecio", precioHistoricoTipoPrecio, "precioHistoricoHabilitado", precioHistoricoHabilitado));
	}

	public List<PrecioHistorico> listarPreciosPorProducto (final Producto precioHistoricoProducto, final boolean precioHistoricoHabilitado) {
		return repositoryService.allMatches(new QueryDefault<>(PrecioHistorico.class, "listarPreciosPorProducto", 
						"precioHistoricoProducto", precioHistoricoProducto, "precioHistoricoHabilitado", precioHistoricoHabilitado));
	}
	
	public Double mostrarPrecioPorFecha(final Producto precioHistoricoProducto,
			final TipoPrecio precioHistoricoTipoPrecio, final Date precioHistoricoFecha) {
		Double precio = 0.0;
		List<PrecioHistorico> listaPrecios = listarPreciosPorProductoPorTipoDeAfiliado(precioHistoricoProducto,
				precioHistoricoTipoPrecio, true);
		Iterator<PrecioHistorico> iterar = listaPrecios.iterator();
		while (iterar.hasNext()) {
			PrecioHistorico listPrecio = iterar.next();
			if ((listPrecio.getPrecioHistoricoFechaDesde().before(precioHistoricoFecha)
					|| listPrecio.getPrecioHistoricoFechaDesde().equals(precioHistoricoFecha))
					&& listPrecio.getPrecioHistoricoFechaHasta().after(precioHistoricoFecha)
					|| listPrecio.getPrecioHistoricoFechaHasta().equals(precioHistoricoFecha)) {
				precio = listPrecio.getPrecioHistoricoPrecio();
				break;
			}
		}
		return precio;
	}
	
	public List<PrecioHistorico> listarPreciosPorRangoDeFecha(Producto precioHistoricoProducto, Date precioHistoricoFechaDesde, Date precioHistoricoFechaHasta){
		List<PrecioHistorico> lista = listarPreciosPorProducto(precioHistoricoProducto, true);
		Iterator<PrecioHistorico> it = lista.iterator();
		while (it.hasNext()) {
			PrecioHistorico item = it.next();
			if (precioHistoricoFechaDesde.after(item.getPrecioHistoricoFechaHasta()))
				it.remove();
			if (precioHistoricoFechaHasta.before(item.getPrecioHistoricoFechaDesde()))
				it.remove();
		}
		Collections.sort(lista);
		return lista;
	}
	
	public boolean verificarCrearPrecio(Producto precioHistoricoProducto, TipoPrecio precioHistoricoTipoPrecio, 
			Date precioHistoricoFechaDesde, Date precioHistoricoFechaHasta) {
		List<PrecioHistorico> listaPrecios = listarPreciosPorProductoPorTipoDeAfiliado(precioHistoricoProducto, precioHistoricoTipoPrecio, true);
		for(int indice = 0; indice<listaPrecios.size();indice++) {
			if ((precioHistoricoFechaDesde.before(listaPrecios.get(indice).getPrecioHistoricoFechaHasta())||precioHistoricoFechaDesde.equals(listaPrecios.get(indice).getPrecioHistoricoFechaHasta()))
					& (precioHistoricoFechaHasta.after(listaPrecios.get(indice).getPrecioHistoricoFechaDesde())||precioHistoricoFechaHasta.equals(listaPrecios.get(indice).getPrecioHistoricoFechaDesde())))
				return false; 
		}
		return true;
	}
	
	public boolean verificarActualizarPrecio(Producto precioHistoricoProducto, TipoPrecio precioHistoricoTipoPrecio, 
			Date precioHistoricoFechaDesde, Date precioHistoricoFechaHasta, PrecioHistorico precioHistorico) {
		List<PrecioHistorico> listaPrecios = listarPreciosPorProductoPorTipoDeAfiliado(precioHistoricoProducto, precioHistoricoTipoPrecio, true);
		Iterator<PrecioHistorico> it = listaPrecios.iterator();
		while (it.hasNext()) {
			PrecioHistorico item = it.next();
			if (item == precioHistorico)
				it.remove();
		}
		for(int indice = 0; indice<listaPrecios.size();indice++) {
			if ((precioHistoricoFechaDesde.before(listaPrecios.get(indice).getPrecioHistoricoFechaHasta())||precioHistoricoFechaDesde.equals(listaPrecios.get(indice).getPrecioHistoricoFechaHasta()))
					& (precioHistoricoFechaHasta.after(listaPrecios.get(indice).getPrecioHistoricoFechaDesde())||precioHistoricoFechaHasta.equals(listaPrecios.get(indice).getPrecioHistoricoFechaDesde())))
				return false; 
		}
		return true;
	}
	
	@Inject
	RepositoryService repositoryService;
	
	@Inject
	ServiceRegistry2 serviceRegistry;
	
}
