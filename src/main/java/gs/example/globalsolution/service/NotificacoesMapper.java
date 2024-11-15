package gs.example.globalsolution.service;

import gs.example.globalsolution.dto.notificacoesDTO.NotificacoesRequest;
import gs.example.globalsolution.dto.notificacoesDTO.NotificacoesResponse;
import gs.example.globalsolution.model.notificacoes.Notificacoes;
import gs.example.globalsolution.model.notificacoes.TipoNotificacao;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

@Service
public class NotificacoesMapper {

    // Mapper para converter NotificacoesRequest para Notificacoes
    public Notificacoes toEntity(NotificacoesRequest notificacoesRequest) {
        Notificacoes notificacoes = new Notificacoes();
        notificacoes.setId(notificacoesRequest.id());
        notificacoes.setUsuario(notificacoesRequest.usuario());
        notificacoes.setMensagem(notificacoesRequest.mensagem());
        notificacoes.setTipo(notificacoesRequest.tipo());
        notificacoes.setDataEnvio(notificacoesRequest.dataEnvio());
        notificacoes.setLeitura(notificacoesRequest.leitura());

        return notificacoes;
    }

    // Mapper para converter Notificacoes para NotificacoesResponse
    public NotificacoesResponse toResponse(Notificacoes notificacoes, Link link) {
        return new NotificacoesResponse(
                notificacoes.getId(),
                notificacoes.getUsuario(),
                notificacoes.getMensagem(),
                notificacoes.getTipo(),
                notificacoes.getDataEnvio(),
                notificacoes.getLeitura(),
                link
        );
    }
}
