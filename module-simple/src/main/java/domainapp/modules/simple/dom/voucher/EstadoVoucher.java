package domainapp.modules.simple.dom.voucher;

public enum EstadoVoucher implements IEstadoVoucher{
	presupuestado("presupuestado"){
		public void confirmar(Voucher voucher) {
			if(voucher.getVoucherProducto().getProductoAutorizacion()==true) {
				voucher.setVoucherEstado(prereserva);
			}
			else {
				voucher.setVoucherEstado(reservado);
			}
		}
	},
	reservado("reservado"),
	prereserva("prereserva"){
		
		public void autorizar (Voucher voucher) {
			voucher.setVoucherEstado(reservado);
		}
		
		public void desautorizar(Voucher voucher) {
			voucher.setVoucherEstado(no_autorizado);
		}
	},
	no_autorizado("no autorizado"){
	},
	anulado("anulado"){
		public void anular(Voucher voucher) {
			
		}
	};
	
	@Override
	public void autorizar(Voucher voucher) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmar(Voucher voucher) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desautorizar(Voucher voucher) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void anular(Voucher voucher) {
		// TODO Auto-generated method stub
		voucher.setVoucherEstado(anulado);
	}
	
	private final String nombre;

	public String getNombre() {
		return nombre;
	}

	private EstadoVoucher(String nom) {
		nombre = nom;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

}
