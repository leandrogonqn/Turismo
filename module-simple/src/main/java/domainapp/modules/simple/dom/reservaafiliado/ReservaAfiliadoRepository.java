package domainapp.modules.simple.dom.reservaafiliado;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.preciohistorico.TipoPrecio;
import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.voucher.Voucher;
import domainapp.modules.simple.dom.voucher.VoucherRepository;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = ReservaAfiliado.class)
public class ReservaAfiliadoRepository {
	
	public ReservaAfiliado crear(final int reservaCodigo, final Date reservaFecha, final int clienteId, final Producto voucherProducto,
			final Date voucherFechaIngreso, final Date voucherFechaEgreso, final int voucherCantidadPasajeros, TipoPrecio precioHistoricoTipoPrecio,
			final String voucherObservaciones, final CanalDePago reservaCanalDePago, final String reservaMemo, 
			final String voucherUsuario) {
		List<Voucher> reservaListaVoucher = new ArrayList<Voucher>();
		Voucher v = voucherRepository.crear(voucherProducto, voucherFechaIngreso, voucherFechaEgreso, voucherCantidadPasajeros, 
				voucherObservaciones, voucherUsuario, precioHistoricoTipoPrecio);
		reservaListaVoucher.add(v);
		final ReservaAfiliado reservaAfiliado = new ReservaAfiliado(reservaCodigo, clienteId, reservaFecha, reservaListaVoucher, 
				reservaCanalDePago, reservaMemo);
		v.setVoucherReserva(reservaAfiliado);
		serviceRegistry.injectServicesInto(reservaAfiliado);
		repositoryService.persist(reservaAfiliado);
		return reservaAfiliado;
	}
	
	public List<ReservaAfiliado> listar() {
		return repositoryService.allInstances(ReservaAfiliado.class);
	}
	
	@Inject
	RepositoryService repositoryService;
	
	@Inject
	ServiceRegistry2 serviceRegistry;
	
	@Inject
	VoucherRepository voucherRepository;

}
