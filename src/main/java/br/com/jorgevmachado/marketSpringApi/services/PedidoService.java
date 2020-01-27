package br.com.jorgevmachado.marketSpringApi.services;

import java.util.Date;
import java.util.Optional;

import br.com.jorgevmachado.marketSpringApi.domain.Cliente;
import br.com.jorgevmachado.marketSpringApi.domain.ItemPedido;
import br.com.jorgevmachado.marketSpringApi.domain.PagamentoComBoleto;
import br.com.jorgevmachado.marketSpringApi.domain.Pedido;
import br.com.jorgevmachado.marketSpringApi.domain.enumerations.EstadoPagamento;
import br.com.jorgevmachado.marketSpringApi.repositories.ItemPedidoRepository;
import br.com.jorgevmachado.marketSpringApi.repositories.PagamentoRepository;
import br.com.jorgevmachado.marketSpringApi.repositories.PedidoRepository;
import br.com.jorgevmachado.marketSpringApi.security.UserSpringSecurity;
import br.com.jorgevmachado.marketSpringApi.services.esceptions.AuthorizationException;
import br.com.jorgevmachado.marketSpringApi.services.esceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort.Direction;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ClienteService clienteService;

//	@Autowired
//	private  EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id: " +
								id +
								", Tipo: " +
								Pedido.class.getName()
				)
		);
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.find(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
//		emailService.sendOrderConfirmationEmail(obj);
//		emailService.sendOrderConfirmationHtmlEmail(obj);
//		System.out.println(obj);
		return obj;
	}

	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSpringSecurity user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(user.getId());
		return repository.findByCliente(cliente, pageRequest);
	}
}
