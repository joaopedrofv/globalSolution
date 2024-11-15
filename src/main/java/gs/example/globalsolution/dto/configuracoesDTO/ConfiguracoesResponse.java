package gs.example.globalsolution.dto.configuracoesDTO;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.Link;

import java.sql.Timestamp;

public record ConfiguracoesResponse(
        Long id,
        Usuario usuario,
        Timestamp corteInicio,
        Timestamp corteFim,
        Long alerta,
        String preferencias,
        Link link
) {
}
