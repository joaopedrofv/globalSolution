package gs.example.globalsolution.service;

import gs.example.globalsolution.dto.pagamentosDTO.PagamentosRequest;
import gs.example.globalsolution.dto.pagamentosDTO.PagamentosResponse;
import gs.example.globalsolution.model.pagamentos.Pagamentos;
import gs.example.globalsolution.model.pagamentos.FormaPagamento;
import gs.example.globalsolution.model.pagamentos.StatusPagamento;
import org.springframework.stereotype.Service;

@Service
public class PagamentosMapper {

    // Mapper para converter PagamentosRequest para Pagamentos
    public Pagamentos toEntity(PagamentosRequest pagamentosRequest) {
        Pagamentos pagamentos = new Pagamentos();
        pagamentos.setId(pagamentosRequest.id());
        pagamentos.setUsuario(pagamentosRequest.usuario());
        pagamentos.setValor(pagamentosRequest.valor());
        pagamentos.setDataPagamento(pagamentosRequest.dataPagamento());
        pagamentos.setFormaPagamento(pagamentosRequest.formaPagamento());
        pagamentos.setStatusPagamento(pagamentosRequest.statusPagamento());

        return pagamentos;
    }

    // Mapper para converter Pagamentos para PagamentosResponse
    public PagamentosResponse toResponse(Pagamentos pagamentos) {
        return new PagamentosResponse(
                pagamentos.getId(),
                pagamentos.getUsuario(),
                pagamentos.getValor(),
                pagamentos.getDataPagamento(),
                pagamentos.getFormaPagamento(),
                pagamentos.getStatusPagamento()
        );
    }
}
