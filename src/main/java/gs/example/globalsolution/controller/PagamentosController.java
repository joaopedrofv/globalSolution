package gs.example.globalsolution.controller;

import gs.example.globalsolution.dto.pagamentosDTO.PagamentosRequest;
import gs.example.globalsolution.dto.pagamentosDTO.PagamentosResponse;
import gs.example.globalsolution.model.pagamentos.Pagamentos;
import gs.example.globalsolution.repository.PagamentosRepository;
import gs.example.globalsolution.service.PagamentosMapper;
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
@RequestMapping(value = "/pagamentos", produces = {"application/json"})
@Tag(name = "api-pagamentos")
public class PagamentosController {

    @Autowired
    private PagamentosRepository pagamentosRepository;
    @Autowired
    private PagamentosMapper pagamentosMapper;

    @Operation(summary = "Cria um novo pagamento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente.")
    })
    @PostMapping
    public ResponseEntity<PagamentosResponse> create(@Valid @RequestBody PagamentosRequest request) {
        if (pagamentosRepository.existsById(request.id())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pagamentos pagamentos = pagamentosMapper.toEntity(request);
        Pagamentos salva = pagamentosRepository.save(pagamentos);

        Link selfLink = linkTo(methodOn(PagamentosController.class).read(salva.getId())).withSelfRel();
        PagamentosResponse response = pagamentosMapper.toResponse(salva, selfLink);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca todos os pagamentos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum pagamento encontrado."),
            @ApiResponse(responseCode = "200", description = "Lista de pagamentos retornada com sucesso!")
    })
    @GetMapping
    public ResponseEntity<List<PagamentosResponse>> readAll() {
        List<Pagamentos> pagamentos = pagamentosRepository.findAll();
        if (pagamentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<PagamentosResponse> responses = new ArrayList<>();
        for (Pagamentos pagamento : pagamentos) {
            Link selfLink = linkTo(methodOn(PagamentosController.class).read(pagamento.getId())).withSelfRel();
            responses.add(pagamentosMapper.toResponse(pagamento, selfLink));
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Busca um pagamento por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado."),
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado com sucesso!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagamentosResponse> read(@PathVariable Long id) {
        Optional<Pagamentos> pagamento = pagamentosRepository.findById(id);
        if (pagamento.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link selfLink = linkTo(methodOn(PagamentosController.class).read(id)).withSelfRel();
        PagamentosResponse response = pagamentosMapper.toResponse(pagamento.get(), selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza um pagamento existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado."),
            @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagamentosResponse> update(
            @PathVariable Long id, @Valid @RequestBody PagamentosRequest request) {

        Optional<Pagamentos> existente = pagamentosRepository.findById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pagamentos pagamentos = pagamentosMapper.toEntity(request);
        pagamentos.setId(id); // Garante que o ID não será alterado
        Pagamentos atualizado = pagamentosRepository.save(pagamentos);

        Link selfLink = linkTo(methodOn(PagamentosController.class).read(id)).withSelfRel();
        PagamentosResponse response = pagamentosMapper.toResponse(atualizado, selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Exclui um pagamento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado."),
            @ApiResponse(responseCode = "200", description = "Pagamento excluído com sucesso!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Pagamentos> pagamento = pagamentosRepository.findById(id);
        if (pagamento.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        pagamentosRepository.delete(pagamento.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
