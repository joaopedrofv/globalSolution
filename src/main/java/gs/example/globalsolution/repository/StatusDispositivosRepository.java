package gs.example.globalsolution.repository;

import gs.example.globalsolution.model.statusDispositivos.StatusDispositivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusDispositivosRepository extends JpaRepository<StatusDispositivos, Long> {
}
