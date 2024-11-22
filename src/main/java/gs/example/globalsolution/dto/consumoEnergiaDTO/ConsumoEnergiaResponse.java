package gs.example.globalsolution.dto.consumoEnergiaDTO;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.hateoas.Link;

import java.sql.Date;

public record ConsumoEnergiaResponse (
        Long id,
        CadastroDispositivos cadastroDispositivos,
        Date dataHora,
        Integer consumoKWH,
        Integer precoKWH,
        Link link
){
}
