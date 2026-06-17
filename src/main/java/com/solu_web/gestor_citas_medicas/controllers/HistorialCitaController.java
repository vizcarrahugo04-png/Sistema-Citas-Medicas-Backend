package com.solu_web.gestor_citas_medicas.controllers;

import com.solu_web.gestor_citas_medicas.dtos.HistorialCitaDTO;
import com.solu_web.gestor_citas_medicas.services.IHistorialCitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/historial-citas")
@RequiredArgsConstructor
public class HistorialCitaController {

    private final IHistorialCitaService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HistorialCitaDTO>>> findAll() {

        List<EntityModel<HistorialCitaDTO>> historiales = service.findAll()
                .stream()
                .map(this::addLinks)
                .toList();

        CollectionModel<EntityModel<HistorialCitaDTO>> collection =
                CollectionModel.of(historiales);

        collection.add(
                linkTo(methodOn(HistorialCitaController.class)
                        .findAll())
                        .withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<HistorialCitaDTO>> findById(
            @PathVariable Integer id) {

        HistorialCitaDTO dto = service.findById(id);

        return ResponseEntity.ok(addLinks(dto));
    }

    @PostMapping
    public ResponseEntity<EntityModel<HistorialCitaDTO>> save(
            @Valid @RequestBody HistorialCitaDTO dto) {

        HistorialCitaDTO saved = service.save(dto);

        return ResponseEntity
                .created(
                        linkTo(methodOn(HistorialCitaController.class)
                                .findById(saved.getIdHistorial()))
                                .toUri()
                )
                .body(addLinks(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<HistorialCitaDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody HistorialCitaDTO dto) {

        dto.setIdHistorial(id);

        HistorialCitaDTO updated = service.update(dto, id);

        return ResponseEntity.ok(addLinks(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    private EntityModel<HistorialCitaDTO> addLinks(HistorialCitaDTO historial) {

        EntityModel<HistorialCitaDTO> resource =
                EntityModel.of(historial);

        resource.add(
                linkTo(methodOn(HistorialCitaController.class)
                        .findById(historial.getIdHistorial()))
                        .withSelfRel()
        );

        resource.add(
                linkTo(methodOn(HistorialCitaController.class)
                        .findAll())
                        .withRel("historial-citas")
        );

        resource.add(
                linkTo(methodOn(HistorialCitaController.class)
                        .delete(historial.getIdHistorial()))
                        .withRel("eliminar")
        );

        return resource;
    }
}