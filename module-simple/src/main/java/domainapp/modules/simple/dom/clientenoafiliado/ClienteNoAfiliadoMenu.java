package domainapp.modules.simple.dom.clientenoafiliado;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import domainapp.modules.simple.dom.localidad.Localidad;
import domainapp.modules.simple.dom.localidad.LocalidadRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = ClienteNoAfiliado.class, objectType="simple.ClienteNoAfiliadoMenu")
@DomainServiceLayout(named = "Clientes", menuOrder = "10.1")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class ClienteNoAfiliadoMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Crear Cliente No Afiliado")
	@MemberOrder(sequence = "1")
	public ClienteNoAfiliado crear(@ParameterLayout(named = "DNI") @Parameter(maxLength=8) final int personaDni,
			@ParameterLayout(named = "Apellido") final String personaApellido,
			@ParameterLayout(named = "Nombre") final String personaNombre,
			@Nullable @ParameterLayout(named = "Cuit/Cuil") @Parameter(optionality = Optionality.OPTIONAL) final String personaCuitCuil,
			@Nullable @ParameterLayout(named = "Domicilio") @Parameter(optionality = Optionality.OPTIONAL) final String personaDireccion,
			@ParameterLayout(named = "Localidad") final Localidad personaLocalidad,
			@ParameterLayout(named = "Teléfono") final String personaTelefono,
			@Nullable @ParameterLayout(named = "E-Mail") @Parameter(optionality = Optionality.OPTIONAL) final String personaMail) {
		return clienteNoAfiliadoRepository.crear(personaDni, personaApellido, personaNombre, personaCuitCuil, personaDireccion, 
				personaLocalidad.getLocalidadId(), personaTelefono, personaMail);
	}
	
	public List<Localidad> choices5Crear() {
		return localidadesRepository.listar();
	}
	
	//este es el validador para que lp no tenga menos de 6, cbu no tenga menos de 22
	public String validateCrear(final int personaDni, final String personaApellido, final String personaNombre, final String personaCuitCuil,
			final String personaDireccion, final Localidad personaLocalidad,  final String personaTelefono, final String personaMail) {
		
		if (Integer.toString(personaDni).length()<6)
			return "Largo del dni incorrecto";
		
		if (personaMail != null){
			// Patrón para validar el email
	        Pattern pattern = Pattern
	                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); 
	        
	        // El email a validar
	        String email = personaMail;
	 
	        Matcher mather = pattern.matcher(email);
	 
	        if (mather.find() == false) {
	            return "El mail ingresado es invalido";
	        }
		}
        return "";
	}
	
	@javax.inject.Inject
	ClienteNoAfiliadoRepository clienteNoAfiliadoRepository;

	@javax.inject.Inject
	LocalidadRepository localidadesRepository;
	
}
