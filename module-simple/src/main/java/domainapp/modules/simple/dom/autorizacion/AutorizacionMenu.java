package domainapp.modules.simple.dom.autorizacion;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

import domainapp.modules.simple.dom.voucher.EstadoVoucher;
import domainapp.modules.simple.dom.voucher.Voucher;
import domainapp.modules.simple.dom.voucher.VoucherRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType="simple.AutorizacionesMenu")
@DomainServiceLayout(named = "Autorizaciones", menuOrder = "90")
public class AutorizacionMenu {
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar y autorizar")
	@MemberOrder(sequence = "1")
	public List<Voucher> listarVoucherParaAutorizar(){
		return voucherRepository.buscarVoucherPorEstado(EstadoVoucher.prereserva);
	}
	
	@Inject
	VoucherRepository voucherRepository;

}
