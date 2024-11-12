package gs.example.globalsolution.repository;


import gs.example.globalsolution.model.consumoEnergia.ConsumoEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumoEnergiaRepository extends JpaRepository<ConsumoEnergia, Long> {
}
