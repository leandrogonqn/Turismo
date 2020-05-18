package domainapp.modules.simple.dom.clientenoafiliado;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = ClienteNoAfiliado.class)
public class ClienteNoAfiliadoRepository {

	public ClienteNoAfiliado crear(final int personaDni, final String personaApellido, final String personaNombre, 
			final String personaCuitCuil, final String personaDireccion, int personaLocalidadId, 
			 final String personaTelefono, final String personaMail) {
		final ClienteNoAfiliado object = new ClienteNoAfiliado(personaDni, personaApellido, personaNombre, 
				personaCuitCuil, personaDireccion, personaLocalidadId, personaTelefono, personaMail);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}
	
	public List<ClienteNoAfiliado> listar() {
		return repositoryService.allInstances(ClienteNoAfiliado.class);
	}

	public List<ClienteNoAfiliado> listarHabilitados() {
		return repositoryService.allMatches(new QueryDefault<>(ClienteNoAfiliado.class, "listarHabilitados"));
	}

	public List<ClienteNoAfiliado> listarInhabilitados() {
		return repositoryService.allMatches(new QueryDefault<>(ClienteNoAfiliado.class, "listarInhabilitados"));
	}

	public List<ClienteNoAfiliado> buscarPorNombre(final String personaJuridicaNombre) {
		return repositoryService.allMatches(
				new QueryDefault<>(ClienteNoAfiliado.class, "buscarPorNombre", "personaJuridicaNombre", personaJuridicaNombre.toLowerCase()));
	}
	
	public List<ClienteNoAfiliado> buscarPorDNI(final int personaJuridicaDni) {
		return repositoryService
				.allMatches(new QueryDefault<>(ClienteNoAfiliado.class, "buscarPorDNI", "personaJuridicaDni", personaJuridicaDni));
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
	
}
