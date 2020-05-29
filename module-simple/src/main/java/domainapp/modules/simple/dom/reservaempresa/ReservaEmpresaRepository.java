package domainapp.modules.simple.dom.reservaempresa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.empresa.Empresa;
import domainapp.modules.simple.dom.preciohistorico.TipoPrecio;
import domainapp.modules.simple.dom.producto.Producto;
import domainapp.modules.simple.dom.voucher.Voucher;
import domainapp.modules.simple.dom.voucher.VoucherRepository;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = ReservaEmpresa.class)
public class ReservaEmpresaRepository {

	public ReservaEmpresa crear(final int reservaCodigo, final Date reservaFecha, final Empresa reservaCliente, 
			final Producto voucherProducto,	final Date voucherFechaIngreso, final Date voucherFechaEgreso, final int voucherCantidadPasajeros, 
			TipoPrecio precioHistoricoTipoPrecio, final String voucherObservaciones, 
			final String reservaMemo, final String voucherUsuario) {
		List<Voucher> reservaListaVoucher = new ArrayList<Voucher>();
		Voucher v = voucherRepository.crear(voucherProducto, voucherFechaIngreso, voucherFechaEgreso, voucherCantidadPasajeros, voucherObservaciones, voucherUsuario, precioHistoricoTipoPrecio);
		reservaListaVoucher.add(v);
		ReservaEmpresa reserva = new ReservaEmpresa(reservaCodigo, reservaCliente, reservaFecha, reservaListaVoucher, reservaMemo);
		v.setVoucherReserva(reserva);
		serviceRegistry.injectServicesInto(reserva);
		repositoryService.persist(reserva);
		return reserva;
	}
	
	public List<ReservaEmpresa> listar() {
		return repositoryService.allInstances(ReservaEmpresa.class);
	}
	
	@Inject
	RepositoryService repositoryService;
	
	@Inject
	VoucherRepository voucherRepository;
	
	@Inject
	ServiceRegistry2 serviceRegistry;
}
