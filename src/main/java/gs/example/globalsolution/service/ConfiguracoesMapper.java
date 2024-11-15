package gs.example.globalsolution.service;

import gs.example.globalsolution.dto.configuracoesDTO.ConfiguracoesRequest;
import gs.example.globalsolution.dto.configuracoesDTO.ConfiguracoesResponse;
import gs.example.globalsolution.model.configuracoes.Configuracoes;
import gs.example.globalsolution.model.usuario.Usuario;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracoesMapper {

    // ConfiguracoesRequest (Record) para Configuracoes (Modelo)
    public Configuracoes requestToConfiguracoes(ConfiguracoesRequest configuracoesRequest) {
        Configuracoes configuracoes = new Configuracoes();
        configuracoes.setId(configuracoesRequest.id());
        configuracoes.setUsuario(configuracoesRequest.usuario());
        configuracoes.setCorteInicio(configuracoesRequest.corteInicio());
        configuracoes.setCorteFim(configuracoesRequest.corteFim());
        configuracoes.setAlerta(configuracoesRequest.alerta());
        configuracoes.setPreferencias(configuracoesRequest.preferencias());
        return configuracoes;
    }

    // Configuracoes (Modelo) para ConfiguracoesResponse (DTO de Resposta)
    public ConfiguracoesResponse configuracoesToResponseDTO(Configuracoes configuracoes, Link link) {
        return new ConfiguracoesResponse(
                configuracoes.getId(),
                configuracoes.getUsuario(),
                configuracoes.getCorteInicio(),
                configuracoes.getCorteFim(),
                configuracoes.getAlerta(),
                configuracoes.getPreferencias(),
                link
        );
    }
}
