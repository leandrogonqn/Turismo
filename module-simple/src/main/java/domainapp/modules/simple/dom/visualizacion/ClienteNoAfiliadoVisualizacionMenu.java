package domainapp.modules.simple.dom.visualizacion;

import java.util.List;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import domainapp.modules.simple.dom.clientenoafiliado.ClienteNoAfiliado;
import domainapp.modules.simple.dom.clientenoafiliado.ClienteNoAfiliadoRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = ClienteNoAfiliado.class, objectType="simple.ClienteNoAfiliadoVisualizacionMenu")
@DomainServiceLayout(named = "Clientes", menuOrder = "10.1")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class ClienteNoAfiliadoVisualizacionMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Todos los no afiliados")
	@MemberOrder(sequence = "2")
	public List<ClienteNoAfiliado> listar() {
		return clienteNoAfiliadoRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar no afiliado Por DNI")
	@MemberOrder(sequence = "3")
	public List<ClienteNoAfiliado> buscarPorDni(@ParameterLayout(named = "DNI") @Parameter(maxLength=8) final int personaDni) {
		return clienteNoAfiliadoRepository.buscarPorDNI(personaDni);
	}
	
	public String validateBuscarPorDni(final int personaDni) {
		if (Integer.toString(personaDni).length()<6)
			return "Largo del dni incorrecto";
		return "";
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar no afiliado 	Por Nombre")
	@MemberOrder(sequence = "4")
	public List<ClienteNoAfiliado> buscarPorNombre(@ParameterLayout(named = "Nombre") final String clienteNombre) {
		return clienteNoAfiliadoRepository.buscarPorNombre(clienteNombre);
	}
	
	@javax.inject.Inject
	ClienteNoAfiliadoRepository clienteNoAfiliadoRepository;
	
}
