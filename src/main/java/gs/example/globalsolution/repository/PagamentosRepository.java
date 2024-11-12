package gs.example.globalsolution.repository;

import gs.example.globalsolution.model.pagamentos.Pagamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentosRepository extends JpaRepository<Pagamentos, Long> {
}
