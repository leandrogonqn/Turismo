package domainapp.modules.simple.dom.reserva;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Reserva.class)
public class ReservaRepository {

	public List<Reserva> listar() {
		return repositoryService.allInstances(Reserva.class);
	}

	public List<Reserva> listarHabilitados(final boolean reservaHabilitado) {
		return repositoryService.allMatches(new QueryDefault<>(Reserva.class, "listarHabilitados", "reservaHabilitado", reservaHabilitado));
	}
	
	public List<Reserva> buscarPorCodigo(final int reservaCodigo) {
		return repositoryService
				.allMatches(new QueryDefault<>(Reserva.class, "buscarPorCodigo", "reservaCodigo", reservaCodigo));
	}
	
	//cuando ya este creado el viewmodel fechasDisponibles hacer metodo listarDisponibilidad que devuelve una lista de fechasDisponibles
	
	@Inject
	RepositoryService repositoryService;
	
}
