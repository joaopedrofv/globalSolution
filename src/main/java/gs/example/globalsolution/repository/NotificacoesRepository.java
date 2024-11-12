package gs.example.globalsolution.repository;

import gs.example.globalsolution.model.notificacoes.Notificacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacoesRepository extends JpaRepository<Notificacoes, Long> {
}
