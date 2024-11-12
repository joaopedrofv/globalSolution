package gs.example.globalsolution.repository;

import gs.example.globalsolution.model.configuracoes.Configuracoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracoesRepository extends JpaRepository<Configuracoes, Long> {
}
