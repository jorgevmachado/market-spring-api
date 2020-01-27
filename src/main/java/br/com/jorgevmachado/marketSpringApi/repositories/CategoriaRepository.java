package br.com.jorgevmachado.marketSpringApi.repositories;

import br.com.jorgevmachado.marketSpringApi.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
