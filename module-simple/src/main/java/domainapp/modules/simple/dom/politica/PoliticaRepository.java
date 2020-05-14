package domainapp.modules.simple.dom.politica;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Politica.class)
public class PoliticaRepository {

	public List<Politica> listar() {
		return repositoryService.allInstances(Politica.class);
	}

	public Politica crear(final String politicaNombre, final String politicaTexto) {
		final Politica object = new Politica(politicaNombre, politicaTexto);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
	
}
