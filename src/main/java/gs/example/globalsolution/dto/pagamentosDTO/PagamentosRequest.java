package gs.example.globalsolution.dto.pagamentosDTO;

import gs.example.globalsolution.model.pagamentos.FormaPagamento;
import gs.example.globalsolution.model.pagamentos.StatusPagamento;
import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.sql.Date;

public record PagamentosRequest(
        Long id,
        @NotNull(message = "O usuário não pode ser nulo.")
        Usuario usuario,
        @NotNull(message = "O valor do pagamento não pode ser nulo.")
        @Min(value = 0, message = "O valor do pagamento deve ser maior ou igual a zero.")
        Long valor,
        @PastOrPresent(message = "A data de pagamento não pode ser no futuro.")
        Date dataPagamento,
        @NotNull(message = "A forma de pagamento não pode ser nula.")
        FormaPagamento formaPagamento,
        @NotNull(message = "O status de pagamento não pode ser nulo.")
        StatusPagamento statusPagamento
) {
}
