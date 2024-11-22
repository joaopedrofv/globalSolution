package gs.example.globalsolution.controller;

import gs.example.globalsolution.dto.usarioDTO.UsuarioRequest;
import gs.example.globalsolution.dto.usarioDTO.UsuarioResponse;
import gs.example.globalsolution.model.usuario.Usuario;
import gs.example.globalsolution.repository.UsuarioRepository;
import gs.example.globalsolution.service.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/usuarios", produces = {"application/json"})
@Tag(name = "Usuários", description = "Operações relacionadas a usuários.")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Operation(summary = "Criar novo usuário.",
            description = "Cria um novo usuário de acordo com os dados proferidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente."),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest request) {
        if (usuarioRepository.existsById(request.id())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = usuarioMapper.toEntity(request);
        Usuario salvo = usuarioRepository.save(usuario);

        Link selfLink = linkTo(methodOn(UsuarioController.class).read(salvo.getId())).withSelfRel();
        UsuarioResponse response = usuarioMapper.toResponse(salvo, selfLink);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar todos usuários.",
            description = "Busca todos os usuários existentes com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado."),
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);

        if (usuariosPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UsuarioResponse> responses = new ArrayList<>();
        for (Usuario usuario : usuariosPage) {
            Link selfLink = linkTo(methodOn(UsuarioController.class).read(usuario.getId())).withSelfRel();
            responses.add(usuarioMapper.toResponse(usuario, selfLink));
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Buscar usuário.",
            description = "Busca um usuário existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> read(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link listaLink = linkTo(methodOn(UsuarioController.class).readAll(0, 10))
                .withRel("Lista de usuários");
        UsuarioResponse response = usuarioMapper.toResponse(usuario.get(), listaLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar usuário.",
            description = "Atualiza um usuário existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(
            @PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {

        Optional<Usuario> existente = usuarioRepository.findById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setId(id);
        Usuario atualizado = usuarioRepository.save(usuario);

        Link selfLink = linkTo(methodOn(UsuarioController.class).read(id)).withSelfRel();
        UsuarioResponse response = usuarioMapper.toResponse(atualizado, selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir usuário.",
            description = "Exclui um usuário existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        usuarioRepository.delete(usuario.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
