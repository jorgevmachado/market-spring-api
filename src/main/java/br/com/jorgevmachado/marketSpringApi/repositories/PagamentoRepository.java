package br.com.jorgevmachado.marketSpringApi.repositories;

import br.com.jorgevmachado.marketSpringApi.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
