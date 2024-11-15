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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/consumo-energia", produces = {"application/json"})
@Tag(name = "api-consumo-energia")
public class ConsumoEnergiaController {

    @Autowired
    private ConsumoEnergiaRepository consumoEnergiaRepository;
    @Autowired
    private ConsumoEnergiaMapper consumoEnergiaMapper;

    @Operation(summary = "Cria um novo registro de consumo de energia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consumo de energia criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente.",
                    content = @Content(schema = @Schema()))
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

    @Operation(summary = "Busca todos os registros de consumo de energia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum registro de consumo de energia encontrado."),
            @ApiResponse(responseCode = "200", description = "Lista de consumos de energia retornada com sucesso!")
    })
    @GetMapping
    public ResponseEntity<List<ConsumoEnergiaResponse>> readAll() {
        List<ConsumoEnergia> consumos = consumoEnergiaRepository.findAll();
        if (consumos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<ConsumoEnergiaResponse> responses = new ArrayList<>();
        for (ConsumoEnergia consumo : consumos) {
            Link selfLink = linkTo(methodOn(ConsumoEnergiaController.class).read(consumo.getId())).withSelfRel();
            responses.add(consumoEnergiaMapper.consumoEnergiaToResponseDTO(consumo, selfLink));
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Busca um consumo de energia por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Consumo de energia não encontrado."),
            @ApiResponse(responseCode = "200", description = "Consumo de energia encontrado com sucesso!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConsumoEnergiaResponse> read(@PathVariable Long id) {
        Optional<ConsumoEnergia> consumo = consumoEnergiaRepository.findById(id);
        if (consumo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link selfLink = linkTo(methodOn(ConsumoEnergiaController.class).read(id)).withSelfRel();
        ConsumoEnergiaResponse response = consumoEnergiaMapper.consumoEnergiaToResponseDTO(consumo.get(), selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza um consumo de energia existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Consumo de energia não encontrado."),
            @ApiResponse(responseCode = "200", description = "Consumo de energia atualizado com sucesso!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ConsumoEnergiaResponse> update(
            @PathVariable Long id, @Valid @RequestBody ConsumoEnergiaRequest request) {

        Optional<ConsumoEnergia> existente = consumoEnergiaRepository.findById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ConsumoEnergia consumoEnergia = consumoEnergiaMapper.requestToConsumoEnergia(request);
        consumoEnergia.setId(id); // Garante a atualização do registro existente
        ConsumoEnergia atualizado = consumoEnergiaRepository.save(consumoEnergia);

        Link selfLink = linkTo(methodOn(ConsumoEnergiaController.class).read(id)).withSelfRel();
        ConsumoEnergiaResponse response = consumoEnergiaMapper.consumoEnergiaToResponseDTO(atualizado, selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Exclui um consumo de energia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Consumo de energia não encontrado."),
            @ApiResponse(responseCode = "200", description = "Consumo de energia excluído com sucesso!")
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
