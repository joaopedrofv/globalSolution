package gs.example.globalsolution.controller;

import gs.example.globalsolution.dto.relatoriosConsumoDTO.RelatoriosConsumoRequest;
import gs.example.globalsolution.dto.relatoriosConsumoDTO.RelatoriosConsumoResponse;
import gs.example.globalsolution.model.relatoriosConsumo.RelatoriosConsumo;
import gs.example.globalsolution.repository.RelatoriosConsumoRepository;
import gs.example.globalsolution.service.RelatoriosConsumoMapper;
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
@RequestMapping(value = "/relatoriosConsumo", produces = {"application/json"})
@Tag(name = "api-relatoriosConsumo")
public class RelatoriosConsumoController {

    @Autowired
    private RelatoriosConsumoRepository relatoriosConsumoRepository;
    @Autowired
    private RelatoriosConsumoMapper relatoriosConsumoMapper;

    @Operation(summary = "Cria um novo relatório de consumo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Relatório de consumo criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente.")
    })
    @PostMapping
    public ResponseEntity<RelatoriosConsumoResponse> create(@Valid @RequestBody RelatoriosConsumoRequest request) {
        if (relatoriosConsumoRepository.existsById(request.id())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RelatoriosConsumo relatoriosConsumo = relatoriosConsumoMapper.toEntity(request);
        RelatoriosConsumo salva = relatoriosConsumoRepository.save(relatoriosConsumo);

        Link selfLink = linkTo(methodOn(RelatoriosConsumoController.class).read(salva.getId())).withSelfRel();
        RelatoriosConsumoResponse response = relatoriosConsumoMapper.toResponse(salva, selfLink);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca todos os relatórios de consumo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum relatório encontrado."),
            @ApiResponse(responseCode = "200", description = "Lista de relatórios retornada com sucesso!")
    })
    @GetMapping
    public ResponseEntity<List<RelatoriosConsumoResponse>> readAll() {
        List<RelatoriosConsumo> relatoriosConsumo = relatoriosConsumoRepository.findAll();
        if (relatoriosConsumo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<RelatoriosConsumoResponse> responses = new ArrayList<>();
        for (RelatoriosConsumo relatorio : relatoriosConsumo) {
            Link selfLink = linkTo(methodOn(RelatoriosConsumoController.class).read(relatorio.getId())).withSelfRel();
            responses.add(relatoriosConsumoMapper.toResponse(relatorio, selfLink));
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Busca um relatório de consumo por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Relatório não encontrado."),
            @ApiResponse(responseCode = "200", description = "Relatório encontrado com sucesso!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RelatoriosConsumoResponse> read(@PathVariable Long id) {
        Optional<RelatoriosConsumo> relatorio = relatoriosConsumoRepository.findById(id);
        if (relatorio.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link selfLink = linkTo(methodOn(RelatoriosConsumoController.class).read(id)).withSelfRel();
        RelatoriosConsumoResponse response = relatoriosConsumoMapper.toResponse(relatorio.get(), selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza um relatório de consumo existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Relatório não encontrado."),
            @ApiResponse(responseCode = "200", description = "Relatório atualizado com sucesso!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RelatoriosConsumoResponse> update(
            @PathVariable Long id, @Valid @RequestBody RelatoriosConsumoRequest request) {

        Optional<RelatoriosConsumo> existente = relatoriosConsumoRepository.findById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RelatoriosConsumo relatoriosConsumo = relatoriosConsumoMapper.toEntity(request);
        relatoriosConsumo.setId(id); // Garante que o ID não será alterado
        RelatoriosConsumo atualizado = relatoriosConsumoRepository.save(relatoriosConsumo);

        Link selfLink = linkTo(methodOn(RelatoriosConsumoController.class).read(id)).withSelfRel();
        RelatoriosConsumoResponse response = relatoriosConsumoMapper.toResponse(atualizado, selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Exclui um relatório de consumo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Relatório não encontrado."),
            @ApiResponse(responseCode = "200", description = "Relatório excluído com sucesso!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<RelatoriosConsumo> relatorio = relatoriosConsumoRepository.findById(id);
        if (relatorio.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        relatoriosConsumoRepository.delete(relatorio.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
