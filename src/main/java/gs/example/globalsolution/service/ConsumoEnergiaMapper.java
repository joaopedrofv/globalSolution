package gs.example.globalsolution.service;

import gs.example.globalsolution.dto.consumoEnergiaDTO.ConsumoEnergiaRequest;
import gs.example.globalsolution.dto.consumoEnergiaDTO.ConsumoEnergiaResponse;
import gs.example.globalsolution.model.consumoEnergia.ConsumoEnergia;
import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

@Service
public class ConsumoEnergiaMapper {

    // Converte ConsumoEnergiaRequest (DTO de entrada) para ConsumoEnergia (Modelo)
    public ConsumoEnergia requestToConsumoEnergia(ConsumoEnergiaRequest consumoEnergiaRequest) {
        ConsumoEnergia consumoEnergia = new ConsumoEnergia();
        consumoEnergia.setId(consumoEnergiaRequest.id());
        consumoEnergia.setDispositivos(consumoEnergiaRequest.cadastroDispositivos());
        consumoEnergia.setDataHora(consumoEnergiaRequest.dataHora());
        consumoEnergia.setConsumoKWH(consumoEnergiaRequest.consumoKWH());
        consumoEnergia.setPrecoKWH(consumoEnergiaRequest.precoKWH());
        return consumoEnergia;
    }

    // Converte ConsumoEnergia (Modelo) para ConsumoEnergiaResponse (DTO de resposta)
    public ConsumoEnergiaResponse consumoEnergiaToResponseDTO(ConsumoEnergia consumoEnergia, Link link) {
        return new ConsumoEnergiaResponse(
                consumoEnergia.getId(),
                consumoEnergia.getDispositivos(),
                consumoEnergia.getDataHora(),
                consumoEnergia.getConsumoKWH(),
                consumoEnergia.getPrecoKWH(),
                link
        );
    }
}
