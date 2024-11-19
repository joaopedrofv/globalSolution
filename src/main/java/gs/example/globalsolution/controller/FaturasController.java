package gs.example.globalsolution.controller;

import gs.example.globalsolution.dto.faturasDTO.FaturasRequest;
import gs.example.globalsolution.dto.faturasDTO.FaturasResponse;
import gs.example.globalsolution.model.faturas.Faturas;
import gs.example.globalsolution.repository.FaturasRepository;
import gs.example.globalsolution.service.FaturasMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/faturas", produces = {"application/json"})
@Tag(name = "Faturas", description = "Operações relacionadas a faturas.")
public class FaturasController {

    @Autowired
    private FaturasRepository faturasRepository;
    @Autowired
    private FaturasMapper faturasMapper;

    @Operation(summary = "Criar nova fatura.",
            description = "Cria uma nova fatura de acordo com os dados proferidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fatura criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente.",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<FaturasResponse> create(@Valid @RequestBody FaturasRequest request) {
        if (faturasRepository.existsById(request.id())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Faturas faturas = faturasMapper.toEntity(request);
        Faturas salva = faturasRepository.save(faturas);

        Link selfLink = linkTo(methodOn(FaturasController.class).read(salva.getId())).withSelfRel();
        FaturasResponse response = faturasMapper.toResponse(salva, selfLink);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar todas faturas.",
            description = "Busca todas as faturas existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhuma fatura encontrada."),
            @ApiResponse(responseCode = "200", description = "Lista de faturas retornada com sucesso!")
    })
    @GetMapping
    public ResponseEntity<List<FaturasResponse>> readAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Faturas> faturasPage = faturasRepository.findAll(pageable);

        if (faturasPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<FaturasResponse> responses = new ArrayList<>();
        for (Faturas fatura : faturasPage.getContent()) {
            Link selfLink = linkTo(methodOn(FaturasController.class).read(fatura.getId())).withSelfRel();
            responses.add(faturasMapper.toResponse(fatura, selfLink));
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Buscar fatura.",
            description = "Busca uma fatura existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Fatura não encontrada."),
            @ApiResponse(responseCode = "200", description = "Fatura encontrada com sucesso!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FaturasResponse> read(@PathVariable Long id) {
        Optional<Faturas> fatura = faturasRepository.findById(id);
        if (fatura.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link selfLink = linkTo(methodOn(FaturasController.class).read(id)).withSelfRel();
        FaturasResponse response = faturasMapper.toResponse(fatura.get(), selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar fatura.",
            description = "Atualiza uma fatura existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Fatura não encontrada."),
            @ApiResponse(responseCode = "200", description = "Fatura atualizada com sucesso!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FaturasResponse> update(
            @PathVariable Long id, @Valid @RequestBody FaturasRequest request) {

        Optional<Faturas> existente = faturasRepository.findById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Faturas faturas = faturasMapper.toEntity(request);
        faturas.setId(id); // Garante que o ID não será alterado
        Faturas atualizado = faturasRepository.save(faturas);

        Link selfLink = linkTo(methodOn(FaturasController.class).read(id)).withSelfRel();
        FaturasResponse response = faturasMapper.toResponse(atualizado, selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir fatura.",
            description = "Exclui uma fatura existente de acordo com o ID associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Fatura não encontrada."),
            @ApiResponse(responseCode = "200", description = "Fatura excluída com sucesso!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Faturas> fatura = faturasRepository.findById(id);
        if (fatura.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        faturasRepository.delete(fatura.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
