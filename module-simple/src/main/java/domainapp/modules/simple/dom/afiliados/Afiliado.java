package domainapp.modules.simple.dom.afiliados;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import domainapp.modules.simple.dom.localidad.Localidad;
import domainapp.modules.simple.dom.localidad.LocalidadRepository;

@DomainObject(nature = Nature.VIEW_MODEL, objectType="Afiliado")
public class Afiliado implements Comparable<Afiliado> {

	// region > title
	public TranslatableString title() {
		String activo;
		if(getAfiliadoActivo()==true) {
			activo = "Activo";
		}else {
			activo = "Retirado";
		}
		return TranslatableString.tr("Afiliado " + activo + 
				" - LP "+getAfiliadoLP()+ " - "+
				getPersonaJuridicaNombre() + " " + getPersonaJuridicaApellido()
				+ " - DNI "+ getPersonaJuridicaDni());
	}
	// endregion
	
	public String iconName() {
		String activo;
		if(getAfiliadoActivo()==true) {
			activo = "A";
		}else {
			activo = "R";
		}
		return activo;
	}
	
	// region > constructor
	public Afiliado() {
	}

	public Afiliado(final String afiliadoNombre) {
		setPersonaJuridicaNombre(afiliadoNombre);
	}

	public Afiliado(int afiliadoId, boolean afiliadoActivo, String afiliadoLP, int afiliadoDni, String afiliadoApellido, String afiliadoNombre,
			String afiliadoCuitCuil, String afiliadoDireccion, int afiliadoLocalidadId, String afiliadoTelefono, 
			String afiliadoMail, String afiliadoCBU) {
		super();
		setAfiliadoId(afiliadoId);
		setAfiliadoActivo(afiliadoActivo);
		setAfiliadoLP(afiliadoLP);
		setPersonaJuridicaDni(afiliadoDni);
		setPersonaJuridicaApellido(afiliadoApellido);
		setPersonaJuridicaNombre(afiliadoNombre);
		setPersonaCuitCuil(afiliadoCuitCuil);
		setPersonaDireccion(afiliadoDireccion);
		setPersonaLocalidadId(afiliadoLocalidadId);
		setPersonaTelefono(afiliadoTelefono);
		setPersonaMail(afiliadoMail);
		setAfiliadoCBU(afiliadoCBU);
	}
	// endregion
	
	public static final int NAME_LENGTH = 40;
	
	@Property(editing = Editing.DISABLED, hidden=Where.EVERYWHERE)
	@PropertyLayout(named = "Id")
	private int afiliadoId;

	public int getAfiliadoId() {
		return afiliadoId;
	}

	public void setAfiliadoId(int afiliadoId) {
		this.afiliadoId = afiliadoId;
	}
	
	@Property(editing = Editing.DISABLED, hidden=Where.EVERYWHERE)
	@PropertyLayout(named = "Activo")
	private boolean afiliadoActivo;

	public boolean getAfiliadoActivo() {
		return afiliadoActivo;
	}

	public void setAfiliadoActivo(boolean afiliadoActivo) {
		this.afiliadoActivo = afiliadoActivo;
	}
	
	@ActionLayout(named="Estado")	 
	public String getEstado() {
		String a;
		if(getAfiliadoActivo()==true) {
			a = "Activo";
		}else {
			a = "Retirado";
		}
		return a;
	}
	
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "LP")
	private String afiliadoLP;

	public String getAfiliadoLP() {
		return afiliadoLP;
	}

	public void setAfiliadoLP(String afiliadoLP) {
		this.afiliadoLP = afiliadoLP;
	}
	
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "DNI")
	private int personaJuridicaDni;

	public int getPersonaJuridicaDni() {
		return personaJuridicaDni;
	}

	public void setPersonaJuridicaDni(int personaJuridicaDni) {
		this.personaJuridicaDni = personaJuridicaDni;
	}
	
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Apellido")
	private String personaJuridicaApellido;

	public String getPersonaJuridicaApellido() {
		return personaJuridicaApellido;
	}

	public void setPersonaJuridicaApellido(String personaJuridicaApellido) {
		this.personaJuridicaApellido = personaJuridicaApellido;
	}
	
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre")
	private String personaJuridicaNombre;

	public String getPersonaJuridicaNombre() {
		return personaJuridicaNombre;
	}

	public void setPersonaJuridicaNombre(final String personaJuridicaNombre) {
		this.personaJuridicaNombre = personaJuridicaNombre;
	}
	
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
	
  @Property(
          editing = Editing.DISABLED
  )
  @PropertyLayout(named="LocalidadId", hidden=Where.EVERYWHERE)
  private int personaLocalidadId;

	public int getPersonaLocalidadId() {
		return personaLocalidadId;
	}

	public void setPersonaLocalidadId(int personaLocalidadId) {
		this.personaLocalidadId = personaLocalidadId;
	}
	
	@ActionLayout(named="Localidad")
	public Localidad getLocalidad() {
		return localidadRepository.buscarPorId(this.personaLocalidadId);
	}

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
	
	@Property(editing = Editing.DISABLED, hidden=Where.EVERYWHERE)
	@PropertyLayout(named = "CBU")
	private String afiliadoCBU;

	public String getAfiliadoCBU() {
		return afiliadoCBU;
	}

	public void setAfiliadoCBU(String afiliadoCBU) {
		this.afiliadoCBU = afiliadoCBU;
	}
	
	@Override
	public String toString() {
		String activo;
		if(getAfiliadoActivo()==true) {
			activo = "Activo";
		}else {
			activo = "Retirado";
		}
		String afiliado = "Afiliado " + activo + 
				" - LP "+getAfiliadoLP()+ " - "+
				getPersonaJuridicaNombre() + " " + getPersonaJuridicaApellido();
		return afiliado;
	}

	@Override
	public int compareTo(Afiliado o) {
		return this.getPersonaJuridicaApellido().compareTo(o.getPersonaJuridicaApellido());  
	}
	
	@javax.inject.Inject
	LocalidadRepository localidadRepository;
	
}
