package domainapp.modules.simple.dom.empresa;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import domainapp.modules.simple.dom.localidad.Localidad;
import domainapp.modules.simple.dom.localidad.LocalidadRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Empresa")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "empresaId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "buscarPorRazonSocial", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.modules.simple.dom.empresa.Empresa "
				+ "WHERE empresaRazonSocial.toLowerCase().indexOf(:empresaRazonSocial) >= 0 "),
		@javax.jdo.annotations.Query(name = "listarHabilitados", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.modules.simple.dom.empresa.Empresa " + "WHERE personaHabilitado == true "),
		@javax.jdo.annotations.Query(name = "listarInhabilitados", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.modules.simple.dom.empresa.Empresa " + "WHERE personaHabilitado == false ") })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Empresa implements Comparable<Empresa> {
	
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("Empresa: {empresaRazonSocial}", "empresaRazonSocial",
				getEmpresaRazonSocial() + " Cuit/Cuil: " + getPersonaCuitCuil());
	}
	// endregion
	
	public String cssClass() {
		return (getPersonaHabilitado() == true) ? "habilitado" : "inhabilitado";
	}

	// region > constructor
	public Empresa() {
	}

	public Empresa(String empresaRazonSocial, String personaCuitCuil, String personaDireccion, 
			int personaLocalidadId,	String personaTelefono, String personaMail) {
		setEmpresaRazonSocial(empresaRazonSocial);
		setPersonaCuitCuil(personaCuitCuil);
		setPersonaDireccion(personaDireccion);
		setPersonaLocalidadId(personaLocalidadId);
		setPersonaTelefono(personaTelefono);
		setPersonaMail(personaMail);
		setPersonaHabilitado(true);
	}
	// endregion

	// region > name (read-only property)
	public static final int NAME_LENGTH = 30;

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Razon Social")
	private String empresaRazonSocial;

	public String getEmpresaRazonSocial() {
		return empresaRazonSocial;
	}

	public void setEmpresaRazonSocial(final String empresaRazonSocial) {
		this.empresaRazonSocial = empresaRazonSocial;
	}
	
	 @javax.jdo.annotations.Column(allowsNull = "true")
	 @Property(
	           editing = Editing.DISABLED
	 )
	 @PropertyLayout(named="Cuit/Cuil", hidden=Where.ALL_TABLES)
	 private String personaCuitCuil;
	 public String getPersonaCuitCuil() {
		return personaCuitCuil;
	 }
	 public void setPersonaCuitCuil(String personaCuitCuil) {
		this.personaCuitCuil = personaCuitCuil;
	 }	
	 
	@javax.jdo.annotations.Column(allowsNull = "true", length = NAME_LENGTH)
   @Property(
           editing = Editing.DISABLED
   )
   @PropertyLayout(named="Direccion", hidden=Where.ALL_TABLES)
   private String personaDireccion;

   public String getPersonaDireccion() {
		return personaDireccion;
	}
	public void setPersonaDireccion(String personaDireccion) {
		this.personaDireccion = personaDireccion;
	}

	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "LocalidadId", hidden = Where.EVERYWHERE)
	private int personaLocalidadId;

	public int getPersonaLocalidadId() {
		return personaLocalidadId;
	}

	public void setPersonaLocalidadId(int personaLocalidadId) {
		this.personaLocalidadId = personaLocalidadId;
	}

	public Localidad getLocalidad() {
		return localidadRepository.buscarPorId(this.personaLocalidadId);
	}
		
   @javax.jdo.annotations.Column(allowsNull = "true", length = NAME_LENGTH)
   @Property(
           editing = Editing.DISABLED
   )
   @PropertyLayout(named="Telefono")
   private String personaTelefono;

   public String getPersonaTelefono() {
		return personaTelefono;
	}
	public void setPersonaTelefono(String personaTelefono) {
		this.personaTelefono = personaTelefono;
	}	
	
   @javax.jdo.annotations.Column(allowsNull = "true", length = NAME_LENGTH)
   @Property(
           editing = Editing.DISABLED
   )
   @PropertyLayout(named="Mail", hidden=Where.ALL_TABLES)
   private String personaMail;

   public String getPersonaMail() {
		return personaMail;
	}
	public void setPersonaMail(String personaMail) {
		this.personaMail = personaMail;
	}	
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Habilitado", hidden=Where.ALL_TABLES)
    private boolean personaHabilitado;
 
    public boolean getPersonaHabilitado() {
		return personaHabilitado;
	}
	public void setPersonaHabilitado(boolean personaHabilitado) {
		this.personaHabilitado = personaHabilitado;
	}	

	// endregion

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "afiliadoLocalidad")
	public Empresa actualizarLocalidad(@ParameterLayout(named = "Localidades") final Localidad afiliadoLocalidad) {
		setPersonaLocalidadId(afiliadoLocalidad.getLocalidadId());
		return this;
	}

	public List<Localidad> choices0ActualizarLocalidad() {
		return localidadRepository.listar();
	}

	public Localidad default0ActualizarLocalidad() {
		return getLocalidad();
	}

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "empresaRazonSocial")
	public Empresa actualizarRazonSocial(@ParameterLayout(named = "Razon Social") final String empresaRazonSocial) {
		setEmpresaRazonSocial(empresaRazonSocial);
		return this;
	}

	public String default0ActualizarRazonSocial() {
		return getEmpresaRazonSocial();
	}

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "afiliadoCuitCuil")
	public Empresa actualizarCuitCuil(@ParameterLayout(named = "Cuit/Cuil") final String afiliadoCuitCuil) {
		setPersonaCuitCuil(afiliadoCuitCuil);
		return this;
	}

	public String default0ActualizarCuitCuil() {
		return getPersonaCuitCuil();
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "afiliadoDireccion")
	public Empresa actualizarDireccion(@ParameterLayout(named = "Direccion") final String afiliadoDireccion) {
		setPersonaDireccion(afiliadoDireccion);
		return this;
	}

	public String default0ActualizarDireccion() {
		return getPersonaDireccion();
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "afiliadoTelefono")
	public Empresa actualizarTelefono(@ParameterLayout(named = "Telefono") final String afiliadoTelefono) {
		setPersonaTelefono(afiliadoTelefono);
		return this;
	}

	public String default0ActualizarTelefono() {
		return getPersonaTelefono();
	}

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "afiliadoMail")
	public Empresa actualizarMail(@ParameterLayout(named = "Mail") final String afiliadoMail) {
		setPersonaMail(afiliadoMail);
		return this;
	}

	public String default0ActualizarMail() {
		return getPersonaMail();
	}

	public String validateActualizarMail(final String personaMail) {
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

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "afiliadoHabilitado")
	public Empresa actualizarHabilitado(@ParameterLayout(named = "Habilitado") final boolean afiliadoHabilitado) {
		setPersonaHabilitado(afiliadoHabilitado);
		return this;
	}

	public boolean default0ActualizarHabilitado() {
		return getPersonaHabilitado();
	}
	
	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarEmpresa() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setPersonaHabilitado(false);
	}

	// endregion
	
	@Override
	public String toString() {
		return "Empresa: " + getEmpresaRazonSocial() + " Cuit/Cuil: " + getPersonaCuitCuil();
	}

	@Override
	public int compareTo(final Empresa empresa) {
		return this.empresaRazonSocial.compareTo(empresa.empresaRazonSocial);
	}
	
	@javax.inject.Inject
	LocalidadRepository localidadRepository;
	
	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

}
