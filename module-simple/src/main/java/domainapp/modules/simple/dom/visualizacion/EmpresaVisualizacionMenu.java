package domainapp.modules.simple.dom.visualizacion;

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

import domainapp.modules.simple.dom.empresa.Empresa;
import domainapp.modules.simple.dom.empresa.EmpresaRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Empresa.class, objectType="simple.EmpresaVisualizacionMenu")
@DomainServiceLayout(named = "Clientes", menuOrder = "10.1")
public class EmpresaVisualizacionMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Todas las Empresas")
	@MemberOrder(sequence = "2")
	public List<Empresa> listar() {
		return empresaRepository.listar();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar Por Razon Social")
	@MemberOrder(sequence = "3")
	public List<Empresa> buscarPorRazonSocial(@ParameterLayout(named = "RazonSocial") final String empresaRazonSocial) {
		return empresaRepository.buscarPorRazonSocial(empresaRazonSocial);
	}
	
	@javax.inject.Inject
	EmpresaRepository empresaRepository;
	
}
