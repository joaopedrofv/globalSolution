package gs.example.globalsolution.dto.notificacoesDTO;

import gs.example.globalsolution.model.notificacoes.TipoNotificacao;
import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public record NotificacoesRequest(
        Long id,
        @NotNull(message = "O usuário não pode ser nulo.")
        Usuario usuario,
        @NotBlank(message = "A mensagem não pode estar em branco.")
        @Size(max = 255, message = "A mensagem não pode ter mais de 255 caracteres.")
        String mensagem,
        @NotNull(message = "O tipo de notificação não pode ser nulo.")
        TipoNotificacao tipo,
        @NotNull(message = "A data de envio não pode ser nula.")
        Date dataEnvio,
        @NotNull(message = "O campo de leitura não pode ser nulo.")
        Boolean leitura
) {
}
