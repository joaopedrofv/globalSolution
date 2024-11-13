package gs.example.globalsolution.dto.notificacoesDTO;

import gs.example.globalsolution.model.notificacoes.TipoNotificacao;
import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public record NotificacoesResponse(
        Long id,
        Usuario usuario,
        String mensagem,
        TipoNotificacao tipo,
        Date dataEnvio,
        Boolean leitura
) {
}
