package com.solu_web.gestor_citas_medicas.controllers;

import com.solu_web.gestor_citas_medicas.dtos.DoctorDTO;
import com.solu_web.gestor_citas_medicas.services.IDoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/doctores")
@RequiredArgsConstructor
public class DoctorController {

    private final IDoctorService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DoctorDTO>>> findAll() {

        List<EntityModel<DoctorDTO>> doctores = service.findAll()
                .stream()
                .map(this::addLinks)
                .toList();

        CollectionModel<EntityModel<DoctorDTO>> collection =
                CollectionModel.of(doctores);

        collection.add(
                linkTo(methodOn(DoctorController.class)
                        .findAll())
                        .withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DoctorDTO>> findById(
            @PathVariable Integer id) {

        DoctorDTO dto = service.findById(id);

        return ResponseEntity.ok(addLinks(dto));
    }

    @PostMapping
    public ResponseEntity<EntityModel<DoctorDTO>> save(
            @Valid @RequestBody DoctorDTO dto) {

        DoctorDTO saved = service.save(dto);

        return ResponseEntity
                .created(
                        linkTo(methodOn(DoctorController.class)
                                .findById(saved.getIdDoctor()))
                                .toUri()
                )
                .body(addLinks(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DoctorDTO>> update(
            @Valid @RequestBody DoctorDTO dto,
            @PathVariable Integer id) {

        DoctorDTO updated = service.update(dto, id);

        return ResponseEntity.ok(addLinks(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    private EntityModel<DoctorDTO> addLinks(DoctorDTO doctor) {

        EntityModel<DoctorDTO> resource =
                EntityModel.of(doctor);

        resource.add(
                linkTo(methodOn(DoctorController.class)
                        .findById(doctor.getIdDoctor()))
                        .withSelfRel()
        );

        resource.add(
                linkTo(methodOn(DoctorController.class)
                        .findAll())
                        .withRel("doctores")
        );

        resource.add(
                linkTo(methodOn(DoctorController.class)
                        .delete(doctor.getIdDoctor()))
                        .withRel("eliminar")
        );

        return resource;
    }
}