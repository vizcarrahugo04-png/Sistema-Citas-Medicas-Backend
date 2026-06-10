package com.solu_web.gestor_citas_medicas.controllers;

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

import com.solu_web.gestor_citas_medicas.dtos.EspecialidadDTO;
import com.solu_web.gestor_citas_medicas.services.IEspecialidadService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {
    
    private final IEspecialidadService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EspecialidadDTO>>> findAll(){
        List<EntityModel<EspecialidadDTO>> especialidades = service.findAll().stream().map(this::addLinks).toList();

        CollectionModel<EntityModel<EspecialidadDTO>> collection = CollectionModel.of(especialidades);

        collection.add(
            linkTo(methodOn(EspecialidadController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EspecialidadDTO>> findById(@PathVariable Integer id){
        EspecialidadDTO especialidad = service.findById(id);
        return ResponseEntity.ok(addLinks(especialidad));
    }

    @PostMapping
    public ResponseEntity<EntityModel<EspecialidadDTO>> save(@Valid @RequestBody EspecialidadDTO especialidadDTO){
        EspecialidadDTO saved = service.save(especialidadDTO);

        return ResponseEntity.created(linkTo(methodOn(EspecialidadController.class).findById(saved.getIdEspecialidad())).toUri()).body(addLinks(saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EspecialidadDTO>> update(
        @PathVariable Integer id,
        @Valid @RequestBody EspecialidadDTO especialidadDTO
    ){
        especialidadDTO.setIdEspecialidad(id);
        EspecialidadDTO update = service.update(especialidadDTO, id);
        return ResponseEntity.ok(addLinks(update));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    private EntityModel<EspecialidadDTO> addLinks(EspecialidadDTO especialidad){
        EntityModel<EspecialidadDTO> resource = EntityModel.of(especialidad);

        resource.add(
            linkTo(methodOn(EspecialidadController.class).findById(especialidad.getIdEspecialidad())).withSelfRel()
        );

        resource.add(
            linkTo(methodOn(EspecialidadController.class).findAll()).withRel("roles")
        );
        return resource;
    }

}
