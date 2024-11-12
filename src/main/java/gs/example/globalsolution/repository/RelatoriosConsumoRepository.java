package gs.example.globalsolution.repository;

import gs.example.globalsolution.model.relatoriosConsumo.RelatoriosConsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatoriosConsumoRepository extends JpaRepository<RelatoriosConsumo, Long> {
}
