package domainapp.modules.simple.dom.localidad;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;

//import domainapp.modules.simple.dom.producto.ProductoRepository;
//import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.provincia.Provincia;
import domainapp.modules.simple.dom.provincia.ProvinciaRepository;

@DomainObject(nature = Nature.VIEW_MODEL, objectType="Localidad")
public class Localidad implements Comparable<Localidad> {

	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name",
				getLocalidadesNombre() + " - " + this.getProvincia().getProvinciasNombre());
	}
	// endregion
	
	public static final int NAME_LENGTH = 200;
	
	// Constructor
	public Localidad() {
		// TODO Auto-generated constructor stub
	}
	
	public Localidad(int localidadId, String localidadNombre, int localidadProvinciaId) {
		setLocalidadId(localidadId);
		setLocalidadesNombre(localidadNombre);
		setLocalidadProvinciaId(localidadProvinciaId);
	}
	
	@Property(editing = Editing.DISABLED, hidden=Where.EVERYWHERE)
	@PropertyLayout(named = "Id")
	private int localidadId;

	public int getLocalidadId() {
		return localidadId;
	}

	public void setLocalidadId(int localidadId) {
		this.localidadId = localidadId;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre")
	private String localidadesNombre;

	public String getLocalidadesNombre() {
		return localidadesNombre;
	}

	public void setLocalidadesNombre(String localidadesNombre) {
		this.localidadesNombre = localidadesNombre;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false", name = "provinciaId")
	@Property(editing = Editing.DISABLED, hidden=Where.EVERYWHERE)
	@PropertyLayout(named = "Provincia Id")
	private int localidadProvinciaId;

	public int getLocalidadProvinciaId() {
		return localidadProvinciaId;
	}

	public void setLocalidadProvinciaId(int localidadProvinciaId) {
		this.localidadProvinciaId = localidadProvinciaId;
	}
	
	@ActionLayout(named="Provincia")
	public Provincia getProvincia() {
		return provinciaRepository.buscarPorId(this.localidadProvinciaId);
	}
	
	// region > toString, compareTo
	@Override
	public String toString() {
		return getLocalidadesNombre();
	}

	@Override
	public int compareTo(final Localidad localidad) {
		return this.localidadesNombre.compareTo(localidad.localidadesNombre);
	}
	// endregion
	
	// acciones
	
//	@Action(semantics = SemanticsOf.SAFE)
//	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar produtos en esta Localidad")
//	public List<Producto> buscarProductoPorLocalidad() {
//		return productoRepository.buscarProductoPorLocalidad(this);
//	}
	// region > injected dependencies
	
	@javax.inject.Inject
	ProvinciaRepository provinciaRepository;
	
//	@Inject
//	ProductoRepository productoRepository;
	
}
