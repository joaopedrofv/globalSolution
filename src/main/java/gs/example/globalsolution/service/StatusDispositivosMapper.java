package gs.example.globalsolution.service;

import gs.example.globalsolution.dto.statusDispositivosDTO.StatusDispositivosRequest;
import gs.example.globalsolution.dto.statusDispositivosDTO.StatusDispositivosResponse;
import gs.example.globalsolution.model.statusDispositivos.StatusDispositivos;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

@Service
public class StatusDispositivosMapper {

    // Mapper para converter StatusDispositivosRequest para StatusDispositivos
    public StatusDispositivos toEntity(StatusDispositivosRequest statusDispositivosRequest) {
        StatusDispositivos statusDispositivos = new StatusDispositivos();
        statusDispositivos.setId(statusDispositivosRequest.id());
        statusDispositivos.setDispositivos(statusDispositivosRequest.dispositivos());
        statusDispositivos.setStatus(statusDispositivosRequest.status());
        statusDispositivos.setDataStatus(statusDispositivosRequest.dataStatus());

        return statusDispositivos;
    }

    // Mapper para converter StatusDispositivos para StatusDispositivosResponse
    public StatusDispositivosResponse toResponse(StatusDispositivos statusDispositivos, Link link) {
        return new StatusDispositivosResponse(
                statusDispositivos.getId(),
                statusDispositivos.getDispositivos(),
                statusDispositivos.getStatus(),
                statusDispositivos.getDataStatus(),
                link
        );
    }
}
