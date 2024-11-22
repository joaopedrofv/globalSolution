package gs.example.globalsolution.controller;

import gs.example.globalsolution.dto.consumoEnergiaDTO.ConsumoEnergiaRequest;
import gs.example.globalsolution.dto.consumoEnergiaDTO.ConsumoEnergiaResponse;
import gs.example.globalsolution.model.consumoEnergia.ConsumoEnergia;
import gs.example.globalsolution.repository.ConsumoEnergiaRepository;
import gs.example.globalsolution.service.ConsumoEnergiaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value = "/consumo-energia", produces = {"application/json"})
@Tag(name = "Consumo de Energia", description = "Operações relacionadas a registros de consumo de energia.")
@PreAuthorize("hasRole('ADMIN')")
public class ConsumoEnergiaController {

    @Autowired
    private ConsumoEnergiaRepository consumoEnergiaRepository;
    @Autowired
    private ConsumoEnergiaMapper consumoEnergiaMapper;

    @Operation(summary = "Criar registro de consumo.",
            description = "Cria um registro de consumo de energia de acordo com os dados proferidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consumo de energia criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente.",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @PostMapping
    public ResponseEntity<ConsumoEnergiaResponse> create(@Valid @RequestBody ConsumoEnergiaRequest request) {
        if (consumoEnergiaRepository.existsById(request.id())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ConsumoEnergia consumoEnergia = consumoEnergiaMapper.requestToConsumoEnergia(request);
        ConsumoEnergia salva = consumoEnergiaRepository.save(consumoEnergia);

        Link selfLink = linkTo(methodOn(ConsumoEnergiaController.class).read(salva.getId())).withSelfRel();
        ConsumoEnergiaResponse response = consumoEnergiaMapper.consumoEnergiaToResponseDTO(salva, selfLink);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar todos registros de consumo.",
            description = "Busca todos os registros de consumo de energia existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum registro de consumo de energia encontrado."),
            @ApiResponse(responseCode = "200", description = "Lista de consumos de energia retornada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @GetMapping
    public ResponseEntity<List<ConsumoEnergiaResponse>> readAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ConsumoEnergia> consumosPage = consumoEnergiaRepository.findAll(pageable);

        if (consumosPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<ConsumoEnergiaResponse> responses = new ArrayList<>();
        for (ConsumoEnergia consumo : consumosPage.getContent()) {
            Link selfLink = linkTo(methodOn(ConsumoEnergiaController.class).read(consumo.getId())).withSelfRel();
            responses.add(consumoEnergiaMapper.consumoEnergiaToResponseDTO(consumo, selfLink));
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Buscar registro de consumo.",
            description = "Busca um registro de consumo de energia existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Consumo de energia não encontrado."),
            @ApiResponse(responseCode = "200", description = "Consumo de energia encontrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConsumoEnergiaResponse> read(@PathVariable Long id) {
        Optional<ConsumoEnergia> consumo = consumoEnergiaRepository.findById(id);
        if (consumo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link listaLink = linkTo(methodOn(ConsumoEnergiaController.class).readAll(0, 10))
                .withRel("Lista de consumos de energia");

        ConsumoEnergiaResponse response = consumoEnergiaMapper.consumoEnergiaToResponseDTO(consumo.get(), listaLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar registro de consumo.",
            description = "Atualiza um registro de consumo de energia existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Consumo de energia não encontrado."),
            @ApiResponse(responseCode = "200", description = "Consumo de energia atualizado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ConsumoEnergiaResponse> update(
            @PathVariable Long id, @Valid @RequestBody ConsumoEnergiaRequest request) {

        Optional<ConsumoEnergia> existente = consumoEnergiaRepository.findById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ConsumoEnergia consumoEnergia = consumoEnergiaMapper.requestToConsumoEnergia(request);
        consumoEnergia.setId(id);
        ConsumoEnergia atualizado = consumoEnergiaRepository.save(consumoEnergia);

        Link selfLink = linkTo(methodOn(ConsumoEnergiaController.class).read(id)).withSelfRel();
        ConsumoEnergiaResponse response = consumoEnergiaMapper.consumoEnergiaToResponseDTO(atualizado, selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir um consumo de energia.",
            description = "Exclui um registro de consumo de energia de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Consumo de energia não encontrado."),
            @ApiResponse(responseCode = "200", description = "Consumo de energia excluído com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário não tem permissão para acessar este recurso.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<ConsumoEnergia> consumo = consumoEnergiaRepository.findById(id);
        if (consumo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        consumoEnergiaRepository.delete(consumo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
