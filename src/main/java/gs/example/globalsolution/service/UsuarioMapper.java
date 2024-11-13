package gs.example.globalsolution.service;

import gs.example.globalsolution.dto.usarioDTO.UsuarioRequest;
import gs.example.globalsolution.dto.usarioDTO.UsuarioResponse;
import gs.example.globalsolution.model.usuario.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMapper {

    // Mapper para converter UsuarioRequest para Usuario (entidade)
    public Usuario toEntity(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioRequest.id());
        usuario.setNome(usuarioRequest.nome());
        usuario.setEmail(usuarioRequest.email());
        usuario.setTelefone(usuarioRequest.telefone());
        usuario.setEndereco(usuarioRequest.endereco());
        usuario.setDataCriacao(usuarioRequest.dataCriacao());

        return usuario;
    }

    // Mapper para converter Usuario (entidade) para UsuarioResponse
    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getEndereco(),
                usuario.getDataCriacao()
        );
    }
}
