package gs.example.globalsolution.service;

import gs.example.globalsolution.dto.faturasDTO.FaturasRequest;
import gs.example.globalsolution.dto.faturasDTO.FaturasResponse;
import gs.example.globalsolution.model.faturas.Faturas;
import gs.example.globalsolution.model.faturas.StatusFatura;
import org.springframework.stereotype.Service;

@Service
public class FaturasMapper {

    // Mapper para converter FaturasRequest para Faturas
    public Faturas toEntity(FaturasRequest faturasRequest) {
        Faturas faturas = new Faturas();
        faturas.setId(faturasRequest.id());
        faturas.setUsuario(faturasRequest.usuario());
        faturas.setDispositivos(faturasRequest.dispositivos());
        faturas.setValorTotal(faturasRequest.valorTotal());
        faturas.setDataVencimento(faturasRequest.dataVencimento());
        faturas.setDataEmissao(faturasRequest.dataEmissao());

        // Convertendo statusFatura de String para o Enum StatusFatura usando o nome
        StatusFatura status = StatusFatura.valueOf(faturasRequest.statusFatura().toUpperCase());
        faturas.setStatusFatura(status);

        return faturas;
    }

    // Mapper para converter Faturas para FaturasResponse
    public FaturasResponse toResponse(Faturas faturas) {
        return new FaturasResponse(
                faturas.getId(),
                faturas.getUsuario(),
                faturas.getDispositivos(),
                faturas.getValorTotal(),
                faturas.getDataVencimento(),
                faturas.getDataEmissao(),
                faturas.getStatusFatura().getDescricao() // Usando a descrição do enum
        );
    }
}
