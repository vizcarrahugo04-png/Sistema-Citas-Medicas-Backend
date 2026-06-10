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

import com.solu_web.gestor_citas_medicas.dtos.ConsultorioDTO;
import com.solu_web.gestor_citas_medicas.services.IConsultorioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/consultorios")
@RequiredArgsConstructor
public class ConsultorioController {
    
    private final IConsultorioService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ConsultorioDTO>>> findAll(){
        List<EntityModel<ConsultorioDTO>> consultorios = service.findAll().stream().map(this::addLinks).toList();

        CollectionModel<EntityModel<ConsultorioDTO>> collection = CollectionModel.of(consultorios);

        collection.add(
            linkTo(methodOn(ConsultorioController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ConsultorioDTO>> findById(@PathVariable Integer id){
        ConsultorioDTO consultorio = service.findById(id);
        return ResponseEntity.ok(addLinks(consultorio));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ConsultorioDTO>> save(@Valid @RequestBody ConsultorioDTO ConsultorioDTO){
        ConsultorioDTO saved = service.save(ConsultorioDTO);

        return ResponseEntity.created(linkTo(methodOn(ConsultorioController.class).findById(saved.getIdConsultorio())).toUri()).body(addLinks(saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ConsultorioDTO>> update(
        @PathVariable Integer id,
        @Valid @RequestBody ConsultorioDTO consultorioDTO
    ){
        consultorioDTO.setIdConsultorio(id);
        ConsultorioDTO update = service.update(consultorioDTO, id);
        return ResponseEntity.ok(addLinks(update));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    private EntityModel<ConsultorioDTO> addLinks(ConsultorioDTO consultorio){
        EntityModel<ConsultorioDTO> resource = EntityModel.of(consultorio);

        resource.add(
            linkTo(methodOn(ConsultorioController.class).findById(consultorio.getIdConsultorio())).withSelfRel()
        );

        resource.add(
            linkTo(methodOn(ConsultorioController.class).findAll()).withRel("consultorios")
        );
        return resource;
    }

}
