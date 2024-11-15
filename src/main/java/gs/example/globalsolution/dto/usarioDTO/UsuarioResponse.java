package gs.example.globalsolution.dto.usarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.Link;

import java.sql.Date;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        String endereco,
        Date dataCriacao,
        Link link
) {
}
