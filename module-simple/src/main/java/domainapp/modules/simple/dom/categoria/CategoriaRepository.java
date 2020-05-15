package domainapp.modules.simple.dom.categoria;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Categoria.class)
public class CategoriaRepository {	
	
	public Categoria crear(final int categoriaCodigo, final String categoriaNombre) {
		final Categoria object = new Categoria(categoriaCodigo, categoriaNombre);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}
	
	public List<Categoria> listar() {
		return repositoryService.allInstances(Categoria.class);
	}
	
	public List<Categoria> buscarPorNombre(final String categoriaNombre) {
		return repositoryService.allMatches(new QueryDefault<>(Categoria.class, "buscarPorNombre", "categoriaNombre",
				categoriaNombre.toLowerCase()));
	}

	public List<Categoria> listarHabilitados() {
		return repositoryService.allMatches(new QueryDefault<>(Categoria.class, "listarHabilitados"));
	}

	public List<Categoria> listarInhabilitados() {
		return repositoryService.allMatches(new QueryDefault<>(Categoria.class, "listarInhabilitados"));
	}
	
	public List<Categoria> buscarPorCodigo(final int categoriaCodigo) {
		return repositoryService
				.allMatches(new QueryDefault<>(Categoria.class, "buscarPorCodigo", "categoriaCodigo", categoriaCodigo));
	}
	
	@Inject
	RepositoryService repositoryService;
	
	@Inject
	ServiceRegistry2 serviceRegistry;

}
