package com.solu_web.gestor_citas_medicas.controllers;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solu_web.gestor_citas_medicas.dtos.RolDTO;
import com.solu_web.gestor_citas_medicas.services.IRolService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/roles")
//Agregamos CORS para angular
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class RolController {
    
    private final IRolService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<RolDTO>>> findAll(){
        List<EntityModel<RolDTO>> roles = service.findAll().stream().map(this::addLinks).toList();

        CollectionModel<EntityModel<RolDTO>> collection = CollectionModel.of(roles);

        collection.add(
            linkTo(methodOn(RolController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RolDTO>> findById(@PathVariable Integer id){
        RolDTO rol = service.findById(id);
        return ResponseEntity.ok(addLinks(rol));
    }

    @PostMapping
    public ResponseEntity<EntityModel<RolDTO>> save(@Valid @RequestBody RolDTO rolDTO){
        RolDTO saved = service.save(rolDTO);

        return ResponseEntity.created(linkTo(methodOn(RolController.class).findById(saved.getIdRol())).toUri()).body(addLinks(saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<RolDTO>> update(
        @PathVariable Integer id,
        @Valid @RequestBody RolDTO rolDTO
    ){
        rolDTO.setIdRol(id);
        RolDTO update = service.update(rolDTO, id);
        return ResponseEntity.ok(addLinks(update));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    private EntityModel<RolDTO> addLinks(RolDTO rol){
        EntityModel<RolDTO> resource = EntityModel.of(rol);

        resource.add(
            linkTo(methodOn(RolController.class).findById(rol.getIdRol())).withSelfRel()
        );

        resource.add(
            linkTo(methodOn(RolController.class).findAll()).withRel("roles")
        );
        return resource;
    }
}
