package gs.example.globalsolution.dto.faturasDTO;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.Link;

import java.sql.Date;

public record FaturasResponse(
        Long id,
        Usuario usuario,
        CadastroDispositivos dispositivos,
        Integer valorTotal,
        Date dataVencimento,
        Date dataEmissao,
        String statusFatura,
        Link link
) {
}
