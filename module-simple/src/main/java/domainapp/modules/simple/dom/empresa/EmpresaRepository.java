package domainapp.modules.simple.dom.empresa;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.localidad.Localidad;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Empresa.class)
public class EmpresaRepository {

	public List<Empresa> listar() {
		return repositoryService.allInstances(Empresa.class);
	}

	public List<Empresa> listarHabilitados() {
		return repositoryService.allMatches(new QueryDefault<>(Empresa.class, "listarHabilitados"));
	}

	public List<Empresa> listarInhabilitados() {
		return repositoryService.allMatches(new QueryDefault<>(Empresa.class, "listarInhabilitados"));
	}

	public List<Empresa> buscarPorRazonSocial(final String empresaRazonSocial) {
		return repositoryService.allMatches(new QueryDefault<>(Empresa.class, "buscarPorRazonSocial",
				"empresaRazonSocial", empresaRazonSocial.toLowerCase()));
	}

	public Empresa crear(String empresaRazonSocial, String personaCuitCuil, String personaDireccion, 
			Localidad personaLocalidad,	String personaTelefono, String personaMail) {
		final Empresa object = new Empresa(empresaRazonSocial, personaCuitCuil, personaDireccion, personaLocalidad.getLocalidadId(),
				personaTelefono, personaMail);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}
	
	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
