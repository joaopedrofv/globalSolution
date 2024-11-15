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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "api-notificacoes")
public class NotificacoesController {

    @Autowired
    private NotificacoesRepository notificacoesRepository;
    @Autowired
    private NotificacoesMapper notificacoesMapper;

    @Operation(summary = "Cria uma nova notificação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificação criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente.")
    })
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

    @Operation(summary = "Busca todas as notificações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhuma notificação encontrada."),
            @ApiResponse(responseCode = "200", description = "Lista de notificações retornada com sucesso!")
    })
    @GetMapping
    public ResponseEntity<List<NotificacoesResponse>> readAll() {
        List<Notificacoes> notificacoes = notificacoesRepository.findAll();
        if (notificacoes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<NotificacoesResponse> responses = new ArrayList<>();
        for (Notificacoes notificacao : notificacoes) {
            Link selfLink = linkTo(methodOn(NotificacoesController.class).read(notificacao.getId())).withSelfRel();
            responses.add(notificacoesMapper.toResponse(notificacao, selfLink));
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Busca uma notificação por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Notificação não encontrada."),
            @ApiResponse(responseCode = "200", description = "Notificação encontrada com sucesso!")
    })
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

    @Operation(summary = "Atualiza uma notificação existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Notificação não encontrada."),
            @ApiResponse(responseCode = "200", description = "Notificação atualizada com sucesso!")
    })
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

    @Operation(summary = "Exclui uma notificação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Notificação não encontrada."),
            @ApiResponse(responseCode = "200", description = "Notificação excluída com sucesso!")
    })
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
