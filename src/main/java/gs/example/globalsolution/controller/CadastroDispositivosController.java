package gs.example.globalsolution.controller;

import gs.example.globalsolution.dto.cadastroDispositivosDTO.CadastroDispositivosRequest;
import gs.example.globalsolution.dto.cadastroDispositivosDTO.CadastroDispositivosResponse;
import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import gs.example.globalsolution.repository.CadastroDispositivosRepository;
import gs.example.globalsolution.service.CadastroDispositivosMapper;
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
@RequestMapping(value = "/dispositivos", produces = {"application/json"})
@Tag(name = "api-dispositivos")
public class CadastroDispositivosController {

    @Autowired
    private CadastroDispositivosRepository dispositivosRepository;
    @Autowired
    private CadastroDispositivosMapper dispositivosMapper;

    @Operation(summary = "Cria um cadastro de dispositivo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos ou ID já existente.",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<CadastroDispositivosResponse> create(@Valid @RequestBody CadastroDispositivosRequest request) {
        if (dispositivosRepository.existsById(request.id())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CadastroDispositivos dispositivo = dispositivosMapper.requestToCadastroDispositivos(request);
        CadastroDispositivos salvo = dispositivosRepository.save(dispositivo);

        Link selfLink = linkTo(methodOn(CadastroDispositivosController.class).read(salvo.getId())).withSelfRel();
        CadastroDispositivosResponse response = dispositivosMapper.cadastroDispositivosToResponseDTO(salvo, selfLink);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca todos os dispositivos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum dispositivo encontrado."),
            @ApiResponse(responseCode = "200", description = "Lista de dispositivos retornada com sucesso!")
    })
    @GetMapping
    public ResponseEntity<List<CadastroDispositivosResponse>> readAll() {
        List<CadastroDispositivos> dispositivos = dispositivosRepository.findAll();
        if (dispositivos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CadastroDispositivosResponse> responses = new ArrayList<>();
        for (CadastroDispositivos dispositivo : dispositivos) {
            Link selfLink = linkTo(methodOn(CadastroDispositivosController.class).read(dispositivo.getId())).withSelfRel();
            responses.add(dispositivosMapper.cadastroDispositivosToResponseDTO(dispositivo, selfLink));
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Busca um dispositivo por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado."),
            @ApiResponse(responseCode = "200", description = "Dispositivo encontrado com sucesso!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CadastroDispositivosResponse> read(@PathVariable Long id) {
        Optional<CadastroDispositivos> dispositivo = dispositivosRepository.findById(id);
        if (dispositivo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link selfLink = linkTo(methodOn(CadastroDispositivosController.class).read(id)).withSelfRel();
        CadastroDispositivosResponse response = dispositivosMapper.cadastroDispositivosToResponseDTO(dispositivo.get(), selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza um dispositivo existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado."),
            @ApiResponse(responseCode = "200", description = "Dispositivo atualizado com sucesso!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CadastroDispositivosResponse> update(
            @PathVariable Long id, @Valid @RequestBody CadastroDispositivosRequest request) {

        Optional<CadastroDispositivos> existente = dispositivosRepository.findById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CadastroDispositivos dispositivo = dispositivosMapper.requestToCadastroDispositivos(request);
        dispositivo.setId(id); // Garante a atualização do registro existente
        CadastroDispositivos atualizado = dispositivosRepository.save(dispositivo);

        Link selfLink = linkTo(methodOn(CadastroDispositivosController.class).read(id)).withSelfRel();
        CadastroDispositivosResponse response = dispositivosMapper.cadastroDispositivosToResponseDTO(atualizado, selfLink);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Exclui um dispositivo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado."),
            @ApiResponse(responseCode = "200", description = "Dispositivo excluído com sucesso!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<CadastroDispositivos> dispositivo = dispositivosRepository.findById(id);
        if (dispositivo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        dispositivosRepository.delete(dispositivo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
