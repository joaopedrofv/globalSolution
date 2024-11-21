package gs.example.globalsolution.controller;

import gs.example.globalsolution.dto.notificacoesDTO.NotificacoesRequest;
import gs.example.globalsolution.dto.notificacoesDTO.NotificacoesResponse;
import gs.example.globalsolution.model.notificacoes.Notificacoes;
import gs.example.globalsolution.repository.NotificacoesRepository;
import gs.example.globalsolution.service.NotificacoesMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/notificacoes", produces = {"application/json"})
@Tag(name = "Notificações", description = "Operações relacionadas a notificações do sistema.")
public class NotificacoesController {

    @Autowired
    private NotificacoesRepository notificacoesRepository;
    @Autowired
    private NotificacoesMapper notificacoesMapper;

    @Operation(summary = "Criar notificação.",
            description = "Cria uma nova notificação de acordo com os dados proferidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificação criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente."),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @RolesAllowed("ADMIN")
    @PostMapping
    public ResponseEntity<NotificacoesResponse> create(@Valid @RequestBody NotificacoesRequest request) {
        if (notificacoesRepository.existsById(request.id())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Notificacoes notificacoes = notificacoesMapper.toEntity(request);
        Notificacoes salva = notificacoesRepository.save(notificacoes);

        Link selfLink = linkTo(methodOn(NotificacoesController.class).read(salva.getId())).withSelfRel();
        NotificacoesResponse response = notificacoesMapper.toResponse(salva, selfLink);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar todas notificações.",
            description = "Busca todas as notificações existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhuma notificação encontrada."),
            @ApiResponse(responseCode = "200", description = "Lista de notificações retornada com sucesso!")
    })
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping
    public ResponseEntity<List<NotificacoesResponse>> readAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notificacoes> notificacoesPage = notificacoesRepository.findAll(pageable);

        if (notificacoesPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<NotificacoesResponse> responses = new ArrayList<>();
        for (Notificacoes notificacao : notificacoesPage.getContent()) {
            Link selfLink = linkTo(methodOn(NotificacoesController.class).read(notificacao.getId())).withSelfRel();
            responses.add(notificacoesMapper.toResponse(notificacao, selfLink));
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Buscar notificação.",
            description = "Busca uma notificação existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Notificação não encontrada."),
            @ApiResponse(responseCode = "200", description = "Notificação encontrada com sucesso!")
    })
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<NotificacoesResponse> read(@PathVariable Long id) {
        Optional<Notificacoes> notificacao = notificacoesRepository.findById(id);
        if (notificacao.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link selfLink = linkTo(methodOn(NotificacoesController.class).read(id)).withSelfRel();
        NotificacoesResponse response = notificacoesMapper.toResponse(notificacao.get(), selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar notificação.",
            description = "Atualiza uma notificação existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Notificação não encontrada."),
            @ApiResponse(responseCode = "200", description = "Notificação atualizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @RolesAllowed("ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<NotificacoesResponse> update(
            @PathVariable Long id, @Valid @RequestBody NotificacoesRequest request) {

        Optional<Notificacoes> existente = notificacoesRepository.findById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Notificacoes notificacoes = notificacoesMapper.toEntity(request);
        notificacoes.setId(id); // Garante que o ID não será alterado
        Notificacoes atualizado = notificacoesRepository.save(notificacoes);

        Link selfLink = linkTo(methodOn(NotificacoesController.class).read(id)).withSelfRel();
        NotificacoesResponse response = notificacoesMapper.toResponse(atualizado, selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir notificação.",
            description = "Exclui uma notificação existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Notificação não encontrada."),
            @ApiResponse(responseCode = "200", description = "Notificação excluída com sucesso!")
    })
    @RolesAllowed({"USER", "ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Notificacoes> notificacao = notificacoesRepository.findById(id);
        if (notificacao.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        notificacoesRepository.delete(notificacao.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
