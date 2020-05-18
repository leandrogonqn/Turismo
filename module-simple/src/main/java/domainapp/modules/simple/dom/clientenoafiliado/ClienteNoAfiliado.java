package domainapp.modules.simple.dom.clientenoafiliado;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
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

@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.modules.simple.dom.clientenoafiliado.ClienteNoAfiliado "
			+ "WHERE personaJuridicaNombre.toLowerCase().indexOf(:personaJuridicaNombre) >= 0 "),
	@javax.jdo.annotations.Query(name = "buscarPorDNI", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.modules.simple.dom.clientenoafiliado.ClienteNoAfiliado " + "WHERE personaJuridicaDni == :personaJuridicaDni"),
	@javax.jdo.annotations.Query(name = "listarHabilitados", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.modules.simple.dom.clientenoafiliado.ClienteNoAfiliado " + "WHERE personaHabilitado == true "),
	@javax.jdo.annotations.Query(name = "listarInhabilitados", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.modules.simple.dom.clientenoafiliado.ClienteNoAfiliado " + "WHERE personaHabilitado == false ") })
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "ClienteNoAfiliado")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "clienteNoAfiliadoId")
@javax.jdo.annotations.Unique(name = "DNI_Apellido_UNQ", members = { "personaJuridicaDni", "personaJuridicaApellido" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class ClienteNoAfiliado implements Comparable<ClienteNoAfiliado> {
	
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("Cliente No Afiliado: {personaNombre}", "personaNombre",
				getPersonaJuridicaNombre() + " " + getPersonaJuridicaApellido() + " DNI: " + getPersonaJuridicaDni());
	}
	// endregion
	
	public String cssClass() {
		return (getPersonaHabilitado() == true) ? "habilitado" : "inhabilitado";
	}

	// region > constructor
	public ClienteNoAfiliado() {
	}

	public ClienteNoAfiliado(final String personaNombre) {
		setPersonaJuridicaNombre(personaNombre);
	}

	public ClienteNoAfiliado(int personaDni, String personaApellido, String personaNombre, String personaCuitCuil, 
			String personaDireccion, int personaLocalidadId,  String personaTelefono,
			String personaMail) {
		setPersonaJuridicaDni(personaDni);
		setPersonaJuridicaApellido(personaApellido);
		setPersonaJuridicaNombre(personaNombre);
		setPersonaCuitCuil(personaCuitCuil);
		setPersonaDireccion(personaDireccion);
		setPersonaLocalidadId(personaLocalidadId);
		setPersonaTelefono(personaTelefono);
		setPersonaMail(personaMail);
		setPersonaHabilitado(true);
	}
	
	public static final int NAME_LENGTH = 30;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "DNI")
	private int personaJuridicaDni;

	public int getPersonaJuridicaDni() {
		return personaJuridicaDni;
	}

	public void setPersonaJuridicaDni(int personaJuridicaDni) {
		this.personaJuridicaDni = personaJuridicaDni;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Apellido")
	private String personaJuridicaApellido;

	public String getPersonaJuridicaApellido() {
		return personaJuridicaApellido;
	}

	public void setPersonaJuridicaApellido(String personaJuridicaApellido) {
		this.personaJuridicaApellido = personaJuridicaApellido;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre")
	private String personaJuridicaNombre;

	public String getPersonaJuridicaNombre() {
		return personaJuridicaNombre;
	}

	public void setPersonaJuridicaNombre(final String personaJuridicaNombre) {
		this.personaJuridicaNombre = personaJuridicaNombre;
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

	@javax.jdo.annotations.Column(allowsNull = "false")
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

	@javax.jdo.annotations.Column(allowsNull = "true")
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
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "personaNombre")
	public ClienteNoAfiliado actualizarNombre(@ParameterLayout(named = "Nombre") final String personaNombre) {
		setPersonaJuridicaNombre(personaNombre);
		return this;
	}

	public String default0ActualizarNombre() {
		return getPersonaJuridicaNombre();
	}

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "personaApellido")
	public ClienteNoAfiliado actualizarApellido(@ParameterLayout(named = "Apellido") final String personaApellido) {
		setPersonaJuridicaApellido(personaApellido);
		return this;
	}

	public String default0ActualizarApellido() {
		return getPersonaJuridicaApellido();
	}

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "personaDni")
	public ClienteNoAfiliado actualizarDni(@ParameterLayout(named = "Numero de Documento") @Parameter(maxLength=8) final int personaDni) {
		setPersonaJuridicaDni(personaDni);
		return this;
	}

	public int default0ActualizarDni() {
		return getPersonaJuridicaDni();
	}
	
	public String validateActualizarDni(final int personaDni) {
		if (Integer.toString(personaDni).length()<6)
			return "Largo del dni incorrecto";
		return "";
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "personaCuitCuil")
	public ClienteNoAfiliado actualizarCuitCuil(@ParameterLayout(named = "Cuit/Cuil") final String personaCuitCuil) {
		setPersonaCuitCuil(personaCuitCuil);
		return this;
	}

	public String default0ActualizarCuitCuil() {
		return getPersonaCuitCuil();
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "personaDireccion")
	public ClienteNoAfiliado actualizarDireccion(@ParameterLayout(named = "Direccion") final String personaDireccion) {
		setPersonaDireccion(personaDireccion);
		return this;
	}

	public String default0ActualizarDireccion() {
		return getPersonaDireccion();
	}

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "personaLocalidad")
	public ClienteNoAfiliado actualizarLocalidad(@ParameterLayout(named = "Localidades") final Localidad personaLocalidad) {
		setPersonaLocalidadId(personaLocalidad.getLocalidadId());
		return this;
	}

	public List<Localidad> choices0ActualizarLocalidad() {
		return localidadRepository.listar();
	}

	public Localidad default0ActualizarLocalidad() {
		return getLocalidad();
	}


	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "personaTelefono")
	public ClienteNoAfiliado actualizarTelefono(@ParameterLayout(named = "Telefono") final String personaTelefono) {
		setPersonaTelefono(personaTelefono);
		return this;
	}

	public String default0ActualizarTelefono() {
		return getPersonaTelefono();
	}	
	

	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "personaMail")
	public ClienteNoAfiliado actualizarMail(@ParameterLayout(named = "Mail") final String personaMail) {
		setPersonaMail(personaMail);
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

	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "personaHabilitado")
	public ClienteNoAfiliado actualizarHabilitado(@ParameterLayout(named = "Habilitado") final boolean personaHabilitado) {
		setPersonaHabilitado(personaHabilitado);
		return this;
	}

	public boolean default0ActualizarHabilitado() {
		return getPersonaHabilitado();
	}
	
	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarClienteNoAfiliado() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setPersonaHabilitado(false);
	}
	// endregion

	@Override
	public String toString() {
		String clienteNoAfiliado = "Cliente No Afiliado: " + getPersonaJuridicaNombre() + " " + 
					getPersonaJuridicaApellido() + " DNI: " + getPersonaJuridicaDni();
		return clienteNoAfiliado;
	}

	@Override
	public int compareTo(ClienteNoAfiliado o) {
		return this.getPersonaJuridicaApellido().compareTo(o.getPersonaJuridicaApellido());  
	}

	// endregion

	// accion
	@ActionLayout(hidden=Where.EVERYWHERE)
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
	LocalidadRepository localidadRepository;

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	
}
