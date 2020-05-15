package domainapp.modules.simple.dom.visualizacion;

import java.util.List;

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

import domainapp.modules.simple.dom.afiliados.Afiliado;
import domainapp.modules.simple.dom.afiliados.AfiliadoRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Afiliado.class, objectType="simple.AfiliadoVisualizacionMenu")
@DomainServiceLayout(named = "Clientes", menuOrder = "10.1")
public class AfiliadoVisualizacionMenu {
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Todos los afiliados")
	@MemberOrder(sequence = "2")
	public List<Afiliado> listar() {
		return afiliadoRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar Afiliados Por LP")
	@MemberOrder(sequence = "3.1")
	public List<Afiliado> buscarPorLP(@ParameterLayout(named = "LP") @Parameter(maxLength=6) final String afiliadoLP) {
		return afiliadoRepository.buscarPorLP(afiliadoLP);
	}
	
	public String validateBuscarPorLP(final String afiliadoLP) {
		if (afiliadoLP.length()<=5) {
			return "LP debe contener 6 digitos";
		}
        if (isNumeric(afiliadoLP) == false) {
            return "Todos los caracteres del LP deben ser numericos";
        }
		return "";
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar Afiliados Por Id")
	@MemberOrder(sequence = "3.1")
	public Afiliado buscarPorID(@ParameterLayout(named = "Id") final int afiliadoId) {
		return afiliadoRepository.buscarPorId(afiliadoId);
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar afiliado Por DNI")
	@MemberOrder(sequence = "3.2")
	public List<Afiliado> buscarPorDni(@ParameterLayout(named = "DNI") @Parameter(maxLength=8) final int personaDni) {
		return afiliadoRepository.buscarPorDNI(personaDni);
	}
	
	public String validateBuscarPorDni(final int personaDni) {
		if (Integer.toString(personaDni).length()<6)
			return "Largo del dni incorrecto";
		return "";
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar afiliado Por Nombre")
	@MemberOrder(sequence = "4")
	public List<Afiliado> buscarPorNombre(@ParameterLayout(named = "Nombre") final String clienteNombre) {
		return afiliadoRepository.buscarPorNombre(clienteNombre);
	}

    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
	
	@javax.inject.Inject
	AfiliadoRepository afiliadoRepository;

}
