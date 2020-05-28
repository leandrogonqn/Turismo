package domainapp.modules.simple.dom.reservaafiliado;

public enum CanalDePago {
	Debito_Automatico("DebitoAutomatico"), Efectivo("Efectivo"), Planilla("Planilla");

	private final String nombre;

	public String getNombre() {
		return nombre;
	}

	private CanalDePago(String nom) {
		nombre = nom;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
