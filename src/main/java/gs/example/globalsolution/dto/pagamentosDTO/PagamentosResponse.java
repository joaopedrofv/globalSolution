package gs.example.globalsolution.dto.pagamentosDTO;

import gs.example.globalsolution.model.pagamentos.FormaPagamento;
import gs.example.globalsolution.model.pagamentos.StatusPagamento;
import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.sql.Date;

public record PagamentosResponse(
        Long id,
        Usuario usuario,
        int valor,
        Date dataPagamento,
        FormaPagamento formaPagamento,
        StatusPagamento statusPagamento
) {
}
