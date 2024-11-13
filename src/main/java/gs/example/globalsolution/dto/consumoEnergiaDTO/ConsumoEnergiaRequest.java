package gs.example.globalsolution.dto.consumoEnergiaDTO;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.sql.Date;

public record ConsumoEnergiaRequest (
        @NotNull(message = "O ID do consumo de energia não pode ser nulo.")
        Long id,
        CadastroDispositivos cadastroDispositivos,  // Opcional se não for marcado como NOT NULL
        @NotNull(message = "A data e hora do consumo de energia não podem ser nulas.")
        Date dataHora,
        @NotNull(message = "O consumo de energia em KWH não pode ser nulo.")
        @PositiveOrZero(message = "O consumo de energia em KWH deve ser um valor positivo ou zero.")
        Integer consumoKWH,
        @NotNull(message = "O preço por KWH não pode ser nulo.")
        @PositiveOrZero(message = "O preço por KWH deve ser um valor positivo ou zero.")
        Integer precoKWH
){
}
