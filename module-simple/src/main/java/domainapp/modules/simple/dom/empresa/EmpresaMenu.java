package domainapp.modules.simple.dom.empresa;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

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

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Empresa.class, objectType="simple.EmpresaMenu")
@DomainServiceLayout(named = "Clientes", menuOrder = "10.1")
public class EmpresaMenu {
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Crear Empresa")
	@MemberOrder(sequence = "1")
	public Empresa crear(@ParameterLayout(named = "RazonSocial") final String empresaRazonSocial,
			@Nullable @ParameterLayout(named = "Cuit") @Parameter(optionality = Optionality.OPTIONAL) final String personaCuitCuil,
			@Nullable @ParameterLayout(named = "Dirección") @Parameter(optionality = Optionality.OPTIONAL) final String personaDireccion,
			@ParameterLayout(named = "Localidad") final Localidad personaLocalidad,
			@Nullable @ParameterLayout(named = "Teléfono") final String personaTelefono,
			@Nullable @ParameterLayout(named = "E-Mail") @Parameter(optionality = Optionality.OPTIONAL) final String personaMail) {
		return empresaRepository.crear(empresaRazonSocial, personaCuitCuil, personaDireccion, personaLocalidad,
				personaTelefono, personaMail);
	}
	
	public List<Localidad> choices3Crear() {
		return localidadesRepository.listar();
	}
	
	//este es el validador para que lp no tenga menos de 6, cbu no tenga menos de 22
	public String validateCrear(final String empresaRazonSocial, final String afiliadoCuitCuil,	final String afiliadoDireccion, final Localidad afiliadoLocalidad,  
			final String afiliadoTelefono, final String afiliadoMail) {
		
		if (afiliadoMail != null){
			// Patrón para validar el email
	        Pattern pattern = Pattern
	                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); 
	        
	        // El email a validar
	        String email = afiliadoMail;
	 
	        Matcher mather = pattern.matcher(email);
	 
	        if (mather.find() == false) {
	            return "El mail ingresado es invalido";
	        }
		}
        return "";
	}

	@javax.inject.Inject
	EmpresaRepository empresaRepository;

	@javax.inject.Inject
	LocalidadRepository localidadesRepository;
	
}
