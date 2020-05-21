package domainapp.modules.simple.dom.preciohistorico;

public enum TipoPrecio {
	Activo("Activo"), Retirado("Retirado"), No_Afiliado("No Afiliado");
	
	private final String nombre;
	
	public String getNombre() {
		return nombre;
	}
	
	private TipoPrecio(String nombre) {
		this.nombre=nombre;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
	
}
