package gs.example.globalsolution.dto.consumoEnergiaDTO;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.sql.Date;

public record ConsumoEnergiaResponse (
        Long id,
        CadastroDispositivos cadastroDispositivos,  // Opcional se não for marcado como NOT NULL
        Date dataHora,
        Integer consumoKWH,
        Integer precoKWH
){
}
