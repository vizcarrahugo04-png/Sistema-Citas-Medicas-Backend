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
import com.solu_web.gestor_citas_medicas.dtos.UsuarioDTO;
import com.solu_web.gestor_citas_medicas.services.IUsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final IUsuarioService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UsuarioDTO>>> findAll(){
        List<EntityModel<UsuarioDTO>> usuarios = service.findAll().stream().map(this::addLinks).toList();

        CollectionModel<EntityModel<UsuarioDTO>> collection = CollectionModel.of(usuarios);

        collection.add(
            linkTo(methodOn(UsuarioController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collection);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> findById(@PathVariable Integer id){
        UsuarioDTO usuario = service.findById(id);
        return ResponseEntity.ok(addLinks(usuario));
    }

    @PostMapping
    public ResponseEntity<EntityModel<UsuarioDTO>> save(@Valid @RequestBody UsuarioDTO usuarioDTO){
        UsuarioDTO saved = service.save(usuarioDTO);

        return ResponseEntity.created(linkTo(methodOn(UsuarioController.class).findById(saved.getIdUsuario())).toUri()).body(addLinks(saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> update(
        @PathVariable Integer id,
        @Valid @RequestBody UsuarioDTO usuarioDTO
    ){
        usuarioDTO.setIdUsuario(id);
        UsuarioDTO updated = service.update(usuarioDTO, id);
        return ResponseEntity.ok(addLinks(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    private EntityModel<UsuarioDTO> addLinks(UsuarioDTO usuario){
        EntityModel<UsuarioDTO> resource = EntityModel.of(usuario);

        resource.add(
            linkTo(methodOn(UsuarioController.class).findById(usuario.getIdUsuario())).withSelfRel()
        );

        resource.add(
            linkTo(methodOn(UsuarioController.class).findAll()).withRel("usuarios")
        );

        resource.add(linkTo(methodOn(UsuarioController.class).update(usuario.getIdUsuario(), usuario)).withRel("actualizar"));

        resource.add(linkTo(methodOn(UsuarioController.class).delete(usuario.getIdUsuario())).withRel("eliminar"));

        return resource;
    }
}