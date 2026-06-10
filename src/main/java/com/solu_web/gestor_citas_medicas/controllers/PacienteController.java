package com.solu_web.gestor_citas_medicas.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solu_web.gestor_citas_medicas.dtos.PacienteDTO;
import com.solu_web.gestor_citas_medicas.services.IPacienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final IPacienteService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PacienteDTO>>> findAll(){
        List<EntityModel<PacienteDTO>> pacientes = service.findAll().stream().map(this::addLinks).toList();

        CollectionModel<EntityModel<PacienteDTO>> collection = CollectionModel.of(pacientes);

        collection.add(
            linkTo(methodOn(PacienteController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PacienteDTO>> findById(@PathVariable Integer id){
        PacienteDTO paciente = service.findById(id);
        return ResponseEntity.ok(addLinks(paciente));
    }

    @PostMapping
    public ResponseEntity<EntityModel<PacienteDTO>> save(@Valid @RequestBody PacienteDTO pacienteDTO){
        PacienteDTO saved = service.save(pacienteDTO);

        return ResponseEntity.created(linkTo(methodOn(PacienteController.class).findById(saved.getIdPaciente())).toUri()).body(addLinks(saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PacienteDTO>> update(
        @PathVariable Integer id,
        @Valid @RequestBody PacienteDTO pacienteDTO
    ){
        pacienteDTO.setIdPaciente(id);
        PacienteDTO updated = service.update(pacienteDTO, id);
        return ResponseEntity.ok(addLinks(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    private EntityModel<PacienteDTO> addLinks(PacienteDTO paciente){
        EntityModel<PacienteDTO> resource = EntityModel.of(paciente);

        resource.add(
            linkTo(methodOn(PacienteController.class).findById(paciente.getIdPaciente())).withSelfRel()
        );

        resource.add(
            linkTo(methodOn(PacienteController.class).findAll()).withRel("pacientes")
        );

        resource.add(linkTo(methodOn(PacienteController.class).update(paciente.getIdPaciente(), paciente)).withRel("actualizar"));

        resource.add(linkTo(methodOn(PacienteController.class).delete(paciente.getIdPaciente())).withRel("eliminar"));

        return resource;
    }
}
