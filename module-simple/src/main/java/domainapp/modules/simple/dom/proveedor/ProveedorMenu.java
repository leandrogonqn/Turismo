package domainapp.modules.simple.dom.proveedor;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Proveedor.class, objectType="simple.ProveedorMenu")
@DomainServiceLayout(named = "Proveedor", menuOrder = "20")
public class ProveedorMenu {
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todos los Proveedores")
	@MemberOrder(sequence = "2")
	public List<Proveedor> listar() {
		return proveedorRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar proveedor Por Id")
	@MemberOrder(sequence = "7")
	public Proveedor buscarPorId(@ParameterLayout(named = "Id") final int proveedorId) {
		return proveedorRepository.buscarPorId(proveedorId);
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Proveedor Por Razon Social")
	@MemberOrder(sequence = "5")
	public List<Proveedor> buscarPorRazonSocial(@ParameterLayout(named = "Razon Social") final String proveedorRazonSocial) {
		return proveedorRepository.buscarPorRazonSocial(proveedorRazonSocial);
	}

	@javax.inject.Inject
	ProveedorRepository proveedorRepository;
	
}
