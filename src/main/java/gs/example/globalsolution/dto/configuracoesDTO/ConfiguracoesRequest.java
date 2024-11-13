package gs.example.globalsolution.dto.configuracoesDTO;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

public record ConfiguracoesRequest (
        @NotNull(message = "O ID das configurações não pode ser nulo.")
        Long id,
        Usuario usuario,
        Timestamp corteInicio,
        Timestamp corteFim,
        @PositiveOrZero(message = "LIMITE_ALERTA deve ser um número positivo ou zero.")
        Long alerta,
        @Size(max = 255, message = "PREFERENCIAS não pode exceder 255 caracteres.")
        String preferencias
){
}
