package gs.example.globalsolution.repository;

import gs.example.globalsolution.model.faturas.Faturas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaturasRepository extends JpaRepository<Faturas, Long> {
}
