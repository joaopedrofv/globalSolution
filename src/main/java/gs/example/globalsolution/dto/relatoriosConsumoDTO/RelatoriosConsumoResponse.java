package gs.example.globalsolution.dto.relatoriosConsumoDTO;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.*;
import org.springframework.hateoas.Link;

import java.sql.Date;

public record RelatoriosConsumoResponse(
        Long id,
        Usuario usuario,
        Date periodoInicio,
        Date periodoFim,
        int totalConsumidoKWH,
        int totalPago,
        String analiseTexto,
        Date dataCriacao,
        Link link
) {
}
