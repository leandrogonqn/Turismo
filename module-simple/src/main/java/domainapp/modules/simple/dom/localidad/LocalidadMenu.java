package domainapp.modules.simple.dom.localidad;

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

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Localidad.class, objectType="simple.LocalidadMenu")
@DomainServiceLayout(named = "Datos", menuOrder = "60.2")
public class LocalidadMenu {
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todas las Localidades")
	@MemberOrder(sequence = "2")
	public List<Localidad> listar() {
		return localidadesRepository.listar();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Localidad Por Nombre")
	@MemberOrder(sequence = "5")
	public List<Localidad> buscarPorNombre(@ParameterLayout(named = "Nombre") final String localidadNombre) {
		return localidadesRepository.buscarPorNombre(localidadNombre);

	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar Afiliados Por Id")
	@MemberOrder(sequence = "3.1")
	public Localidad buscarPorID(@ParameterLayout(named = "Id") final int localidadId) {
		return localidadesRepository.buscarPorId(localidadId);
	}
	
	@javax.inject.Inject
	LocalidadRepository localidadesRepository;

}
