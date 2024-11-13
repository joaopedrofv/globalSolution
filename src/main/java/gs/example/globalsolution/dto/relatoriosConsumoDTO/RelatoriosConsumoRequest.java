package gs.example.globalsolution.dto.relatoriosConsumoDTO;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.*;

import java.sql.Date;

public record RelatoriosConsumoRequest(
        Long id,
        @NotNull(message = "O usuário não pode ser nulo.")
        Usuario usuario,
        @NotNull(message = "O período de início não pode ser nulo.")
        @PastOrPresent(message = "O período de início deve estar no passado ou no presente.")
        Date periodoInicio,
        @NotNull(message = "O período de fim não pode ser nulo.")
        @PastOrPresent(message = "O período de fim deve estar no passado ou no presente.")
        Date periodoFim,
        @Min(value = 0, message = "O total consumido em KWH deve ser maior ou igual a zero.")
        int totalConsumidoKWH,
        @Min(value = 0, message = "O total pago deve ser maior ou igual a zero.")
        int totalPago,
        @NotBlank(message = "A análise em texto não pode estar em branco.")
        @Size(max = 255, message = "A análise em texto não pode ter mais de 255 caracteres.")
        String analiseTexto,
        @NotNull(message = "A data de criação não pode ser nula.")
        @PastOrPresent(message = "A data de criação deve estar no passado ou no presente.")
        Date dataCriacao
) {
}
