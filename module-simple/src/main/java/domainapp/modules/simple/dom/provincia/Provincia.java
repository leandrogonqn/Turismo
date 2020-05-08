package domainapp.modules.simple.dom.provincia;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;

@DomainObject(nature = Nature.VIEW_MODEL, objectType="Provincia")
public class Provincia implements Comparable<Provincia>{
	
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name", getProvinciasNombre());
	}
	// endregion
	
	// Constructor
	public Provincia() {
		// TODO Auto-generated constructor stub
	}
	
	public Provincia(int provinciasId, String provinciaNombre) {
		setProvinciasId(provinciasId);
		setProvinciasNombre(provinciaNombre);
	}

	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Id")
	private int provinciasId;

	public int getProvinciasId() {
		return provinciasId;
	}

	public void setProvinciasId(int provinciasId) {
		this.provinciasId = provinciasId;
	}
	
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre")
	private String provinciasNombre;

	public String getProvinciasNombre() {
		return provinciasNombre;
	}

	public void setProvinciasNombre(String provinciasNombre) {
		this.provinciasNombre = provinciasNombre;
	}

	// endregion
	
	@Override
	public String toString() {
		return getProvinciasNombre();
	}

	@Override
	public int compareTo(final Provincia provincia) {
		return this.provinciasNombre.compareTo(provincia.provinciasNombre);
	}
	
}
