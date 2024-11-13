package gs.example.globalsolution.dto.statusDispositivosDTO;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import gs.example.globalsolution.model.statusDispositivos.Status;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.sql.Timestamp;

public record StatusDispositivosRequest(
        Long id,
        @NotNull(message = "O dispositivo não pode ser nulo.")
        CadastroDispositivos dispositivos,
        @NotNull(message = "O status não pode ser nulo.")
        Status status,
        @NotNull(message = "A data de status não pode ser nula.")
        Timestamp dataStatus
) {
}
