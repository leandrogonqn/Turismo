package domainapp.modules.simple.dom.producto;

import java.util.List;
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

import domainapp.modules.simple.dom.categoria.Categoria;
import domainapp.modules.simple.dom.categoria.CategoriaRepository;
import domainapp.modules.simple.dom.localidad.Localidad;
import domainapp.modules.simple.dom.localidad.LocalidadRepository;
import domainapp.modules.simple.dom.proveedor.Proveedor;
import domainapp.modules.simple.dom.proveedor.ProveedorRepository;
import edu.umd.cs.findbugs.annotations.Nullable;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Producto.class, objectType="simple.ProductoMenu")
@DomainServiceLayout(named = "Productos", menuOrder = "30.2")
public class ProductoMenu {
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Crear Producto")
	@MemberOrder(sequence = "1")
	public Producto crear(@ParameterLayout(named="Codigo")final int productoCodigo, 
			@ParameterLayout(named="Alojamiento Propio") final boolean productoAlojamientoPropio,
			@Nullable @ParameterLayout(named="Proveedor") @Parameter(optionality=Optionality.OPTIONAL) final Proveedor productoProveedor,
			@ParameterLayout(named="Categoria") final Categoria productoCategoria,
			@Nullable @ParameterLayout(named="Direccion") @Parameter(optionality=Optionality.OPTIONAL) final String productoDireccion,
			@ParameterLayout(named="Localidad") final Localidad productoLocalidad, 
			@Nullable @ParameterLayout(named="Telefono") @Parameter(optionality=Optionality.OPTIONAL) final String productoTelefono) {
		return productoRepository.crear(productoCodigo, productoAlojamientoPropio, productoProveedor, productoCategoria, 
				productoDireccion, productoLocalidad.getLocalidadId() , productoTelefono);
	}
	
	public List<Proveedor> choices2Crear(final int productoCodigo, final boolean productoAlojamientoPropio, final Proveedor productoProveedor,
			final Categoria productoCategoria, final String productoDireccion, final Localidad productoLocalidad, final String productoTelefono){
		if(productoAlojamientoPropio==true)
			return null;
		return proveedorRepository.listar();
	}
	
	public List<Categoria> choices3Crear(){
		return categoriaRepository.listarHabilitados();
	}
	
	public List<Localidad> choices5Crear(){
		return localidadRepository.listar();
	}
	
	public String validateCrear(final int productoCodigo, final boolean productoAlojamientoPropio, final Proveedor productoProveedor,
			final Categoria productoCategoria, final String productoDireccion, final Localidad productoLocalidad, final String productoTelefono) {
		if(productoAlojamientoPropio==false & productoProveedor==null)
			return "Si el alojamiento no es propio, el proveedor no puede ser nulo";
		if(productoAlojamientoPropio==true & productoProveedor!=null)
			return "Si el alojamiento es propio, el vendedor debe ser nulo";
		return "";
	}

	@Inject
	ProductoRepository productoRepository;
	
	@Inject
	ProveedorRepository proveedorRepository;
	
	@Inject
	CategoriaRepository categoriaRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
	
}
