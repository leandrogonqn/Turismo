package domainapp.modules.simple.dom.proveedor;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;

@DomainObject(nature = Nature.VIEW_MODEL, objectType="Proveedor")
public class Proveedor implements Comparable<Proveedor> {
	
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name", "Id: " + getProveedorId() + " - " + getProveedorRazonSocial());
	}
	// endregion
	
	// Constructor
	public Proveedor() {
		// TODO Auto-generated constructor stub
	}
	
	public Proveedor(int proveedorId, String proveedorCuit, String proveedorRazonSocial) {
		setProveedorId(proveedorId);
		setProveedorCuit(proveedorCuit);
		setProveedorRazonSocial(proveedorRazonSocial);
	}
	
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(named = "Id")
	private int proveedorId;
	
	public int getProveedorId() {
		return proveedorId;
	}
	
	public void setProveedorId(int proveedorId) {
		this.proveedorId = proveedorId;
	}
	
	 @Property(
	           editing = Editing.DISABLED
	 )
	 @PropertyLayout(named="Cuit", hidden=Where.ALL_TABLES)
	 private String proveedorCuit;
	 public String getProveedorCuit() {
		return proveedorCuit;
	 }
	 public void setProveedorCuit(String proveedorCuit) {
		this.proveedorCuit = proveedorCuit;
	 }	

	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Razon Social")
	private String proveedorRazonSocial;

	public String getProveedorRazonSocial() {
		return proveedorRazonSocial;
	}

	public void setProveedorRazonSocial(String proveedorRazonSocial) {
		this.proveedorRazonSocial = proveedorRazonSocial;
	}
	
	// region > toString, compareTo
	@Override
	public String toString() {
		return getProveedorRazonSocial();
	}

	@Override
	public int compareTo(final Proveedor proveedor) {
		return this.proveedorRazonSocial.compareTo(proveedor.proveedorRazonSocial);
	}

}
