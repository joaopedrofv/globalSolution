package gs.example.globalsolution.controller;

import gs.example.globalsolution.dto.configuracoesDTO.ConfiguracoesRequest;
import gs.example.globalsolution.dto.configuracoesDTO.ConfiguracoesResponse;
import gs.example.globalsolution.model.configuracoes.Configuracoes;
import gs.example.globalsolution.repository.ConfiguracoesRepository;
import gs.example.globalsolution.service.ConfiguracoesMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/configuracoes", produces = {"application/json"})
@Tag(name = "Configurações", description = "Operações relacionadas a configurações de dispositivos.")
@PreAuthorize("hasRole('ADMIN')") // Garante que somente administradores terão acesso a todas as operações do controlador
public class ConfiguracoesController {

    @Autowired
    private ConfiguracoesRepository configuracoesRepository;
    @Autowired
    private ConfiguracoesMapper configuracoesMapper;

    @Operation(summary = "Criar configuração de dispositivo.",
            description = "Cria um novo registro de configuração de acordo com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Configuração criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente.",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<ConfiguracoesResponse> create(@Valid @RequestBody ConfiguracoesRequest request) {
        if (configuracoesRepository.existsById(request.id())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Configuracoes configuracao = configuracoesMapper.requestToConfiguracoes(request);
        Configuracoes salva = configuracoesRepository.save(configuracao);

        Link selfLink = linkTo(methodOn(ConfiguracoesController.class).read(salva.getId())).withSelfRel();
        ConfiguracoesResponse response = configuracoesMapper.configuracoesToResponseDTO(salva, selfLink);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar todas configurações paginadas.",
            description = "Busca todas informações de dispositivos existentes com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhuma configuração encontrada."),
            @ApiResponse(responseCode = "200", description = "Lista de configurações retornada com sucesso!")
    })
    @GetMapping
    public ResponseEntity<List<ConfiguracoesResponse>> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size); // Cria um Pageable com base nos parâmetros page e size
        Page<Configuracoes> configuracoesPage = configuracoesRepository.findAll(pageable);

        if (configuracoesPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<ConfiguracoesResponse> responses = new ArrayList<>();
        for (Configuracoes configuracao : configuracoesPage) {
            Link selfLink = linkTo(methodOn(ConfiguracoesController.class).read(configuracao.getId())).withSelfRel();
            responses.add(configuracoesMapper.configuracoesToResponseDTO(configuracao, selfLink));
        }

        return ResponseEntity.ok(responses);
    }


    @Operation(summary = "Buscar configuração.",
            description = "Busca uma configuração de dispositivo existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Configuração não encontrada."),
            @ApiResponse(responseCode = "200", description = "Configuração encontrada com sucesso!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConfiguracoesResponse> read(@PathVariable Long id) {
        Optional<Configuracoes> configuracao = configuracoesRepository.findById(id);
        if (configuracao.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link selfLink = linkTo(methodOn(ConfiguracoesController.class).read(id)).withSelfRel();
        ConfiguracoesResponse response = configuracoesMapper.configuracoesToResponseDTO(configuracao.get(), selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar configuração.",
            description = "Atualiza a configuração de um dispositivo existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Configuração não encontrada."),
            @ApiResponse(responseCode = "200", description = "Configuração atualizada com sucesso!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracoesResponse> update(
            @PathVariable Long id, @Valid @RequestBody ConfiguracoesRequest request) {

        Optional<Configuracoes> existente = configuracoesRepository.findById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Configuracoes configuracao = configuracoesMapper.requestToConfiguracoes(request);
        configuracao.setId(id); // Garante a atualização do registro existente
        Configuracoes atualizado = configuracoesRepository.save(configuracao);

        Link selfLink = linkTo(methodOn(ConfiguracoesController.class).read(id)).withSelfRel();
        ConfiguracoesResponse response = configuracoesMapper.configuracoesToResponseDTO(atualizado, selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir configuração.",
            description = "Exclui uma configuração de dispositivo existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Configuração não encontrada."),
            @ApiResponse(responseCode = "200", description = "Configuração excluída com sucesso!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Configuracoes> configuracao = configuracoesRepository.findById(id);
        if (configuracao.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        configuracoesRepository.delete(configuracao.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
