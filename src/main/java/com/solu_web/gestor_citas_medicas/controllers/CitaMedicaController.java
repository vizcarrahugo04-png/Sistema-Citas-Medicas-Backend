package com.solu_web.gestor_citas_medicas.controllers;

import com.solu_web.gestor_citas_medicas.dtos.CitaMedicaDTO;
import com.solu_web.gestor_citas_medicas.services.ICitaMedicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/citas")
@RequiredArgsConstructor
public class CitaMedicaController {

    private final ICitaMedicaService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CitaMedicaDTO>>> findAll() {

        List<EntityModel<CitaMedicaDTO>> citas = service.findAll()
                .stream()
                .map(this::addLinks)
                .toList();

        CollectionModel<EntityModel<CitaMedicaDTO>> collection =
                CollectionModel.of(citas);

        collection.add(
                linkTo(methodOn(CitaMedicaController.class)
                        .findAll())
                        .withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CitaMedicaDTO>> findById(
            @PathVariable Integer id) {

        CitaMedicaDTO dto = service.findById(id);

        return ResponseEntity.ok(addLinks(dto));
    }

    @PostMapping
    public ResponseEntity<EntityModel<CitaMedicaDTO>> save(
            @Valid @RequestBody CitaMedicaDTO dto) {

        CitaMedicaDTO saved = service.save(dto);

        return ResponseEntity
                .created(
                        linkTo(methodOn(CitaMedicaController.class)
                                .findById(saved.getIdCita()))
                                .toUri()
                )
                .body(addLinks(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CitaMedicaDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CitaMedicaDTO dto) {

        dto.setIdCita(id);

        CitaMedicaDTO updated = service.update(dto, id);

        return ResponseEntity.ok(addLinks(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    private EntityModel<CitaMedicaDTO> addLinks(CitaMedicaDTO cita) {

        EntityModel<CitaMedicaDTO> resource =
                EntityModel.of(cita);

        resource.add(
                linkTo(methodOn(CitaMedicaController.class)
                        .findById(cita.getIdCita()))
                        .withSelfRel()
        );

        resource.add(
                linkTo(methodOn(CitaMedicaController.class)
                        .findAll())
                        .withRel("citas")
        );

        resource.add(
                linkTo(methodOn(CitaMedicaController.class)
                        .delete(cita.getIdCita()))
                        .withRel("eliminar")
        );

        return resource;
    }
}