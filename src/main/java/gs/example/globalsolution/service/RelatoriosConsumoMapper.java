package gs.example.globalsolution.service;

import gs.example.globalsolution.dto.relatoriosConsumoDTO.RelatoriosConsumoRequest;
import gs.example.globalsolution.dto.relatoriosConsumoDTO.RelatoriosConsumoResponse;
import gs.example.globalsolution.model.relatoriosConsumo.RelatoriosConsumo;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

@Service
public class RelatoriosConsumoMapper {

    // Mapper para converter RelatoriosConsumoRequest para RelatoriosConsumo
    public RelatoriosConsumo toEntity(RelatoriosConsumoRequest relatoriosConsumoRequest) {
        RelatoriosConsumo relatoriosConsumo = new RelatoriosConsumo();
        relatoriosConsumo.setId(relatoriosConsumoRequest.id());
        relatoriosConsumo.setUsuario(relatoriosConsumoRequest.usuario());
        relatoriosConsumo.setPeriodoInicio(relatoriosConsumoRequest.periodoInicio());
        relatoriosConsumo.setPeriodoFim(relatoriosConsumoRequest.periodoFim());
        relatoriosConsumo.setTotalConsumidoKWH(relatoriosConsumoRequest.totalConsumidoKWH());
        relatoriosConsumo.setTotalPago(relatoriosConsumoRequest.totalPago());
        relatoriosConsumo.setAnaliseTexto(relatoriosConsumoRequest.analiseTexto());
        relatoriosConsumo.setDataCriacao(relatoriosConsumoRequest.dataCriacao());

        return relatoriosConsumo;
    }

    // Mapper para converter RelatoriosConsumo para RelatoriosConsumoResponse
    public RelatoriosConsumoResponse toResponse(RelatoriosConsumo relatoriosConsumo, Link link) {
        return new RelatoriosConsumoResponse(
                relatoriosConsumo.getId(),
                relatoriosConsumo.getUsuario(),
                relatoriosConsumo.getPeriodoInicio(),
                relatoriosConsumo.getPeriodoFim(),
                relatoriosConsumo.getTotalConsumidoKWH(),
                relatoriosConsumo.getTotalPago(),
                relatoriosConsumo.getAnaliseTexto(),
                relatoriosConsumo.getDataCriacao(),
                link
        );
    }
}
