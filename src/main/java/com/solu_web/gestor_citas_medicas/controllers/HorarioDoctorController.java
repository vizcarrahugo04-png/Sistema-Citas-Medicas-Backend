package com.solu_web.gestor_citas_medicas.controllers;

import com.solu_web.gestor_citas_medicas.dtos.HorarioDoctorDTO;
import com.solu_web.gestor_citas_medicas.services.IHorarioDoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/horarios")
@RequiredArgsConstructor
public class HorarioDoctorController {

    private final IHorarioDoctorService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HorarioDoctorDTO>>> findAll() {

        List<EntityModel<HorarioDoctorDTO>> horarios = service.findAll()
                .stream()
                .map(this::addLinks)
                .toList();

        CollectionModel<EntityModel<HorarioDoctorDTO>> collection =
                CollectionModel.of(horarios);

        collection.add(
                linkTo(methodOn(HorarioDoctorController.class)
                        .findAll())
                        .withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<HorarioDoctorDTO>> findById(
            @PathVariable Integer id) {

        HorarioDoctorDTO dto = service.findById(id);

        return ResponseEntity.ok(addLinks(dto));
    }

    @PostMapping
    public ResponseEntity<EntityModel<HorarioDoctorDTO>> save(
            @Valid @RequestBody HorarioDoctorDTO dto) {

        HorarioDoctorDTO saved = service.save(dto);

        return ResponseEntity
                .created(
                        linkTo(methodOn(HorarioDoctorController.class)
                                .findById(saved.getIdHorario()))
                                .toUri()
                )
                .body(addLinks(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<HorarioDoctorDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody HorarioDoctorDTO dto) {

        dto.setIdHorario(id);

        HorarioDoctorDTO updated = service.update(dto, id);

        return ResponseEntity.ok(addLinks(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    private EntityModel<HorarioDoctorDTO> addLinks(HorarioDoctorDTO horario) {

        EntityModel<HorarioDoctorDTO> resource =
                EntityModel.of(horario);

        resource.add(
                linkTo(methodOn(HorarioDoctorController.class)
                        .findById(horario.getIdHorario()))
                        .withSelfRel()
        );

        resource.add(
                linkTo(methodOn(HorarioDoctorController.class)
                        .findAll())
                        .withRel("horarios")
        );

        resource.add(
                linkTo(methodOn(HorarioDoctorController.class)
                        .delete(horario.getIdHorario()))
                        .withRel("eliminar")
        );

        return resource;
    }
}