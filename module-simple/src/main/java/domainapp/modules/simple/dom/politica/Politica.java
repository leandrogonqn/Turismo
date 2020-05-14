package domainapp.modules.simple.dom.politica;

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
import org.apache.isis.applib.services.i18n.TranslatableString;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Politica")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "politicaId")
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Politica {
	
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name", getPoliticaTitulo());
	}
	// endregion

	public static final int NAME_LENGTH = 200;

	// Constructor
	public Politica(String politicaTitulo, String politicaTexto) {
			setPoliticaTitulo(politicaTitulo);
			setPoliticaTexto(politicaTexto);
		}

	public Politica() {
			// TODO Auto-generated constructor stub
		}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Titulo")
	private String politicaTitulo;

	public String getPoliticaTitulo() {
		return politicaTitulo;
	}

	public void setPoliticaTitulo(String politicaTitulo) {
		this.politicaTitulo = politicaTitulo;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false", length = 10000)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Texto")
	private String politicaTexto;

	public String getPoliticaTexto() {
		return politicaTexto;
	}

	public void setPoliticaTexto(String politicaTexto) {
		this.politicaTexto = politicaTexto;
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "politicaTitulo")
	public Politica actualizarTitulo(@ParameterLayout(named = "Titulo") final String politicaTitulo) {
		setPoliticaTitulo(politicaTitulo);
		return this;
	}

	public String default0ActualizarTitulo() {
		return getPoliticaTitulo();
	}
	
	@Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "politicaTexto")
	public Politica actualizarTexto(@ParameterLayout(named = "Texto", multiLine=20) final String politicaTexto) {
		setPoliticaTexto(politicaTexto);
		return this;
	}

	public String default0ActualizarTexto() {
		return getPoliticaTexto();
	}
	
	// region > toString, compareTo
	@Override
	public String toString() {
		return getPoliticaTitulo();
	}

	// endregion
	
}
