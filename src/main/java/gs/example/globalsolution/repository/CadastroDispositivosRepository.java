package gs.example.globalsolution.repository;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroDispositivosRepository extends JpaRepository<CadastroDispositivos, Long> {
}
