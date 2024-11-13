package gs.example.globalsolution.dto.cadastroDispositivosDTO;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.sql.Date;
public record CadastroDispositivosRequest(
        @NotNull(message = "O ID do dispositivo não pode ser nulo.")
        Long id,
        Usuario usuario,
        @NotBlank(message = "O tipo do dispositivo não pode estar em branco.")
        @Size(max = 255, message = "O tipo do dispositivo não pode exceder 255 caracteres.")
        String tipoDispositivo,
        @NotNull(message = "A data de instalação não pode ser nula.")
        @PastOrPresent(message = "A data de instalação não pode estar no futuro.")
        Date dataInstalacao
) {
}
