package gs.example.globalsolution.service;

import gs.example.globalsolution.dto.cadastroDispositivosDTO.CadastroDispositivosRequest;
import gs.example.globalsolution.dto.cadastroDispositivosDTO.CadastroDispositivosResponse;
import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import gs.example.globalsolution.model.usuario.Usuario;
import org.springframework.stereotype.Service;

@Service
public class CadastroDispositivosMapper {

    // CadastroDispositivosRequest (Record) para CadastroDispositivos
    public CadastroDispositivos requestToCadastroDispositivos(CadastroDispositivosRequest cadastroDispositivosRequest) {
        CadastroDispositivos cadastroDispositivos = new CadastroDispositivos();
        cadastroDispositivos.setId(cadastroDispositivosRequest.id());
        cadastroDispositivos.setUsuario(cadastroDispositivosRequest.usuario());
        cadastroDispositivos.setTipoDispositivo(cadastroDispositivosRequest.tipoDispositivo());
        cadastroDispositivos.setDataInstalacao(cadastroDispositivosRequest.dataInstalacao());
        return cadastroDispositivos;
    }

    // CadastroDispositivos para CadastroDispositivosResponse (DTO de resposta)
    public CadastroDispositivosResponse cadastroDispositivosToResponseDTO(CadastroDispositivos cadastroDispositivos) {
        return new CadastroDispositivosResponse(
                cadastroDispositivos.getId(),
                cadastroDispositivos.getUsuario(),
                cadastroDispositivos.getTipoDispositivo(),
                cadastroDispositivos.getDataInstalacao()
        );
    }
}
