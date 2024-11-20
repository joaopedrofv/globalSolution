package gs.example.globalsolution.dto.faturasDTO;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import gs.example.globalsolution.model.faturas.StatusFatura;
import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public record FaturasRequest(
        @NotNull(message = "O ID da fatura não pode ser nulo.")
        Long id,
        @NotNull(message = "O usuário não pode ser nulo.")
        Usuario usuario,
        @NotNull(message = "O dispositivo associado à fatura não pode ser nulo.")
        CadastroDispositivos dispositivos,
        @NotNull(message = "O valor total da fatura não pode ser nulo.")
        @Positive(message = "O valor total da fatura deve ser um valor positivo.")
        Long valorTotal,
        @NotNull(message = "A data de vencimento não pode ser nula.")
        Date dataVencimento,
        @NotNull(message = "A data de emissão não pode ser nula.")
        Date dataEmissao,
        @NotBlank(message = "O status da fatura não pode estar em branco.")
        @Size(max = 20, message = "O status da fatura não pode exceder 20 caracteres.")
        String statusFatura
) {
}
