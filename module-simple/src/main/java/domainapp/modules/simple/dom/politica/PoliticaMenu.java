package domainapp.modules.simple.dom.politica;

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

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Politica.class, objectType="simple.PoliticaMenu")
@DomainServiceLayout(named = "Datos", menuOrder = "60.3")
public class PoliticaMenu {
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todas las Politicas")
	@MemberOrder(sequence = "2")
	public List<Politica> listar() {
		return politicaRepository.listar();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Crear Politica")
	@MemberOrder(sequence = "1.2")
	public Politica crear(@ParameterLayout(named = "Titulo") final String politicaTitulo,
			@ParameterLayout(named = "Texto", multiLine=20) final String politicaTexto) {
		return politicaRepository.crear(politicaTitulo, politicaTexto);
	}

	@javax.inject.Inject
	PoliticaRepository politicaRepository;

}
