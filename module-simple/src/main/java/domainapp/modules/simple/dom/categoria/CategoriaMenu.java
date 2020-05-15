package domainapp.modules.simple.dom.categoria;

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

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Categoria.class, objectType="simple.CategoriaMenu")
@DomainServiceLayout(named = "Productos", menuOrder = "30.1")
public class CategoriaMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Crear Categoria")
	@MemberOrder(sequence = "1")
	public Categoria crear(@ParameterLayout(named="Codigo") final int categoriaCodigo,
			@ParameterLayout(named = "Nombre") final String categoriaNombre) {
		return categoriaRepository.crear(categoriaCodigo, categoriaNombre);
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todas las Categorias")
	@MemberOrder(sequence = "2")
	public List<Categoria> listar() {
		return categoriaRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar categoria Por codigo")
	@MemberOrder(sequence = "3")
	public List<Categoria> buscarPorCodigo(@ParameterLayout(named = "Codigo") final int categoriaCodigo) {
		return categoriaRepository.buscarPorCodigo(categoriaCodigo);
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Categoria Por Nombre")
	@MemberOrder(sequence = "4")
	public List<Categoria> buscarPorNombre(@ParameterLayout(named = "Nombre") final String categoriaNombre) {
		return categoriaRepository.buscarPorNombre(categoriaNombre);

	}
	
	@javax.inject.Inject
	CategoriaRepository categoriaRepository;
	
}
