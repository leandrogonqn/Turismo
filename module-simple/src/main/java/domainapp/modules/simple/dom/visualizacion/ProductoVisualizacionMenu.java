package domainapp.modules.simple.dom.visualizacion;

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

import domainapp.modules.simple.dom.categoria.Categoria;
import domainapp.modules.simple.dom.categoria.CategoriaRepository;
import domainapp.modules.simple.dom.empresa.Empresa;
import domainapp.modules.simple.dom.localidad.Localidad;
import domainapp.modules.simple.dom.localidad.LocalidadRepository;
import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.producto.ProductoRepository;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.dom.proveedor.ProveedorRepository;
@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Empresa.class, objectType="simple.ProductoVisualizacionMenu")
@DomainServiceLayout(named = "Producto", menuOrder = "30.2")
public class ProductoVisualizacionMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todos los Productos")
	@MemberOrder(sequence = "2")
	public List<Producto> listar(){
		return productoRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Productos por Codigo")
	@MemberOrder(sequence = "3")
	public List<Producto> buscarPorCodigo(@ParameterLayout(named="Codigo") final int productoCodigo){
		return productoRepository.buscarPorCodigo(productoCodigo);
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Productos por Localidad")
	@MemberOrder(sequence = "4")
	public List<Producto> buscarPorLocalidad(@ParameterLayout(named="Localidad") final Localidad productoLocalidad){
		return productoRepository.buscarProductoPorLocalidad(productoLocalidad);
	}
	
	public List<Localidad> choices0BuscarPorLocalidad(){
		return localidadRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Productos por Categoria")
	@MemberOrder(sequence = "5")
	public List<Producto> buscarPorCategoria(@ParameterLayout(named="Categoria") final Categoria productoCategoria){
		return productoRepository.buscarProductoPorCategoria(productoCategoria);
	}
	
	public List<Categoria> choices0BuscarPorCategoria(){
		return categoriaRepository.listarHabilitados();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Productos por Proveedor")
	@MemberOrder(sequence = "6")
	public List<Producto> buscarPorProveedor(@ParameterLayout(named="Proveedor") final Proveedor productoProveedor){
		return productoRepository.buscarProductoPorProveedor(productoProveedor);
	}
	
	public List<Proveedor> choices0BuscarPorProveedor() {
		return proveedorRepository.listar();
	}
	
	@Inject
	ProductoRepository productoRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
	
	@Inject
	CategoriaRepository categoriaRepository;
	
	@Inject
	ProveedorRepository proveedorRepository;

}
