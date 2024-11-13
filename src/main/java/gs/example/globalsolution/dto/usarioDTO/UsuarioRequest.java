package gs.example.globalsolution.dto.usarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public record UsuarioRequest(
        Long id,
        @NotBlank(message = "O nome não pode estar em branco.")
        @Size(max = 255, message = "O nome não pode ter mais de 255 caracteres.")
        String nome,
        @NotBlank(message = "O email não pode estar em branco.")
        @Email(message = "O email deve ser válido.")
        @Size(max = 255, message = "O email não pode ter mais de 255 caracteres.")
        String email,
        @Size(max = 15, message = "O telefone não pode ter mais de 15 caracteres.")
        String telefone,
        @Size(max = 255, message = "O endereço não pode ter mais de 255 caracteres.")
        String endereco,
        @PastOrPresent(message = "A data de criação deve estar no passado ou no presente.")
        Date dataCriacao
) {
}
