package gs.example.globalsolution.dto.statusDispositivosDTO;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import gs.example.globalsolution.model.statusDispositivos.Status;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record StatusDispositivosResponse(
        Long id,
        CadastroDispositivos dispositivos,
        Status status,
        Timestamp dataStatus
) {
}
