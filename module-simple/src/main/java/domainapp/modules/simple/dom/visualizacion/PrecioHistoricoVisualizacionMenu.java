package domainapp.modules.simple.dom.visualizacion;

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

import domainapp.modules.simple.dom.preciohistorico.PrecioHistorico;
import domainapp.modules.simple.dom.preciohistorico.PrecioHistoricoRepository;
import domainapp.modules.simple.dom.preciohistorico.TipoPrecio;
import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.producto.ProductoRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.PrecioHistoricoVisualizacionMenu", repositoryFor = PrecioHistorico.class)
@DomainServiceLayout(named = "Productos", menuOrder = "30.3")
public class PrecioHistoricoVisualizacionMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Mostrar precio de producto por rango de fecha")
	@MemberOrder(sequence = "1")
	public List<PrecioHistorico> listarPreciosPorRangoDeFecha(@ParameterLayout(named = "Producto") final Producto precioHistoricoProducto,
			@ParameterLayout(named = "Fecha Desde") final Date precioHistoricoFechaDesde,
			@ParameterLayout(named = "Fecha Hasta") final Date precioHistoricoFechaHasta) {
		return precioHistoricoRepository.listarPreciosPorRangoDeFecha(precioHistoricoProducto, precioHistoricoFechaDesde, precioHistoricoFechaHasta);
	}
	
	public List<Producto> choices0ListarPreciosPorRangoDeFecha() {
		return productoRepository.listarHabilitados();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Precios por Producto y Tipo Cliente")
	@MemberOrder(sequence = "2")
	public List<PrecioHistorico> listarPreciosPorProductoPorTipoDeAfiliado(
			@ParameterLayout(named = "Producto") final Producto precioHistoricoProducto,
			@ParameterLayout(named = "Tipo Precio") final TipoPrecio precioHistoricoTipoPrecio) {
		return precioHistoricoRepository.listarPreciosPorProductoPorTipoDeAfiliado(precioHistoricoProducto, precioHistoricoTipoPrecio, true);
	}
	
	public List<Producto> choices0ListarPreciosPorProductoPorTipoDeAfiliado() {
		return productoRepository.listarHabilitados();
	}
	
	@Inject
	PrecioHistoricoRepository precioHistoricoRepository;
	
	@javax.inject.Inject
	ProductoRepository productoRepository;
	
	
}
