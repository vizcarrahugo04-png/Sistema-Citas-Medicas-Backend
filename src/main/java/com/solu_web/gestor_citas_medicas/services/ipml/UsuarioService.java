package com.solu_web.gestor_citas_medicas.services.ipml;

import java.util.List;

import org.springframework.stereotype.Service;

import com.solu_web.gestor_citas_medicas.dtos.UsuarioDTO;
import com.solu_web.gestor_citas_medicas.exceptions.ResourceNotFoundExceptions;
import com.solu_web.gestor_citas_medicas.models.Rol;
import com.solu_web.gestor_citas_medicas.models.Usuario;
import com.solu_web.gestor_citas_medicas.repositories.IRolRepo;
import com.solu_web.gestor_citas_medicas.repositories.IUsuarioRepo;
import com.solu_web.gestor_citas_medicas.services.IUsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepo usuarioRepo;
    private final IRolRepo rolRepo;

    @Override
    public UsuarioDTO save (UsuarioDTO dto){
        Rol rol = rolRepo.findById(dto.getIdRol()).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontro el rol con ID: "+dto.getIdRol()));

        if(usuarioRepo.existsByCorreo(dto.getCorreo())){
        throw new RuntimeException(
                "El correo ya está registrado: " + dto.getCorreo()
        );
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setEstado(dto.getEstado());
        usuario.setRol(rol);

        Usuario saved = usuarioRepo.save(usuario);

        return convertToDTO(saved);
    }

    @Override
    public UsuarioDTO update(UsuarioDTO dto, Integer id){
        Usuario usuario = usuarioRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontro el usuario con ID: "+id));

        Rol rol = rolRepo.findById(dto.getIdRol()).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontro el rol con ID: "+dto.getIdRol()));

        if (usuarioRepo.existsByCorreoAndIdUsuarioNot(dto.getCorreo(), id)) {
        throw new RuntimeException("El correo ya está registrado: " + dto.getCorreo());
        }
        usuario.setIdUsuario(id);
        usuario.setUsername(dto.getUsername());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setEstado(dto.getEstado());
        usuario.setRol(rol);

        Usuario updated = usuarioRepo.save(usuario);

        return convertToDTO(updated);
    }

    @Override
    public List<UsuarioDTO> findAll(){
        return usuarioRepo.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    public UsuarioDTO findById(Integer id){
        Usuario usuario = usuarioRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontró el usuario con ID: "+ id));

        return convertToDTO(usuario);
    }

    @Override
    public void delete(Integer id){
        if(!usuarioRepo.existsById(id)){
            throw new ResourceNotFoundExceptions("No se encontró el usuario con ID: "+id);
        }
        usuarioRepo.deleteById(id);
    }

    private UsuarioDTO convertToDTO(Usuario usuario){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setUsername(usuario.getUsername());
        dto.setCorreo(usuario.getCorreo());
        dto.setPassword(usuario.getPassword());
        dto.setEstado(usuario.getEstado());
        dto.setIdRol(usuario.getRol().getIdRol());
        return dto;
    }
    
}
