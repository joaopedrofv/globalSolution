package gs.example.globalsolution.dto.cadastroDispositivosDTO;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.Link;

import java.sql.Date;

public record CadastroDispositivosResponse (
        Long id,
        Usuario usuario,
        String tipoDispositivo,
        Date dataInstalacao,
        Link link
){
}
