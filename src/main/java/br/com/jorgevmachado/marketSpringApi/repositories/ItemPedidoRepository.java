package br.com.jorgevmachado.marketSpringApi.repositories;

import br.com.jorgevmachado.marketSpringApi.domain.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
