package gs.example.globalsolution.controller;

import gs.example.globalsolution.dto.statusDispositivosDTO.StatusDispositivosRequest;
import gs.example.globalsolution.dto.statusDispositivosDTO.StatusDispositivosResponse;
import gs.example.globalsolution.model.statusDispositivos.StatusDispositivos;
import gs.example.globalsolution.repository.StatusDispositivosRepository;
import gs.example.globalsolution.service.StatusDispositivosMapper;
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
@RequestMapping(value = "/statusDispositivos", produces = {"application/json"})
@Tag(name = "api-statusDispositivos")
public class StatusDispositivosController {

    @Autowired
    private StatusDispositivosRepository statusDispositivosRepository;
    @Autowired
    private StatusDispositivosMapper statusDispositivosMapper;

    @Operation(summary = "Cria um novo status para o dispositivo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Status do dispositivo criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente.")
    })
    @PostMapping
    public ResponseEntity<StatusDispositivosResponse> create(@Valid @RequestBody StatusDispositivosRequest request) {
        if (statusDispositivosRepository.existsById(request.id())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        StatusDispositivos statusDispositivos = statusDispositivosMapper.toEntity(request);
        StatusDispositivos salva = statusDispositivosRepository.save(statusDispositivos);

        Link selfLink = linkTo(methodOn(StatusDispositivosController.class).read(salva.getId())).withSelfRel();
        StatusDispositivosResponse response = statusDispositivosMapper.toResponse(salva, selfLink);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca todos os status dos dispositivos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum status encontrado."),
            @ApiResponse(responseCode = "200", description = "Lista de status retornada com sucesso!")
    })
    @GetMapping
    public ResponseEntity<List<StatusDispositivosResponse>> readAll() {
        List<StatusDispositivos> statusDispositivos = statusDispositivosRepository.findAll();
        if (statusDispositivos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<StatusDispositivosResponse> responses = new ArrayList<>();
        for (StatusDispositivos status : statusDispositivos) {
            Link selfLink = linkTo(methodOn(StatusDispositivosController.class).read(status.getId())).withSelfRel();
            responses.add(statusDispositivosMapper.toResponse(status, selfLink));
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Busca um status do dispositivo por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Status não encontrado."),
            @ApiResponse(responseCode = "200", description = "Status encontrado com sucesso!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StatusDispositivosResponse> read(@PathVariable Long id) {
        Optional<StatusDispositivos> status = statusDispositivosRepository.findById(id);
        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link selfLink = linkTo(methodOn(StatusDispositivosController.class).read(id)).withSelfRel();
        StatusDispositivosResponse response = statusDispositivosMapper.toResponse(status.get(), selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza um status de dispositivo existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Status não encontrado."),
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StatusDispositivosResponse> update(
            @PathVariable Long id, @Valid @RequestBody StatusDispositivosRequest request) {

        Optional<StatusDispositivos> existente = statusDispositivosRepository.findById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        StatusDispositivos statusDispositivos = statusDispositivosMapper.toEntity(request);
        statusDispositivos.setId(id); // Garante que o ID não será alterado
        StatusDispositivos atualizado = statusDispositivosRepository.save(statusDispositivos);

        Link selfLink = linkTo(methodOn(StatusDispositivosController.class).read(id)).withSelfRel();
        StatusDispositivosResponse response = statusDispositivosMapper.toResponse(atualizado, selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Exclui um status de dispositivo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Status não encontrado."),
            @ApiResponse(responseCode = "200", description = "Status excluído com sucesso!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<StatusDispositivos> status = statusDispositivosRepository.findById(id);
        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        statusDispositivosRepository.delete(status.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
