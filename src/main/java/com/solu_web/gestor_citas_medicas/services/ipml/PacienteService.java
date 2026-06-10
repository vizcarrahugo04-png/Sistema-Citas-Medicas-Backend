package com.solu_web.gestor_citas_medicas.services.ipml;

import java.util.List;

import org.springframework.stereotype.Service;

import com.solu_web.gestor_citas_medicas.dtos.PacienteDTO;
import com.solu_web.gestor_citas_medicas.exceptions.ResourceNotFoundExceptions;
import com.solu_web.gestor_citas_medicas.models.Paciente;
import com.solu_web.gestor_citas_medicas.models.Usuario;
import com.solu_web.gestor_citas_medicas.repositories.IPacienteRepo;
import com.solu_web.gestor_citas_medicas.repositories.IUsuarioRepo;
import com.solu_web.gestor_citas_medicas.services.IPacienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService implements IPacienteService {
    
    private final IPacienteRepo pacienteRepo;
    private final IUsuarioRepo usuarioRepo;

    @Override
    public PacienteDTO save (PacienteDTO dto){
        Usuario usuario = usuarioRepo.findById(dto.getIdUsuario()).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontro el usuario con ID: "+dto.getIdUsuario()));

        Paciente paciente = new Paciente();
        paciente.setNombres(dto.getNombres());
        paciente.setApellidos(dto.getApellidos());
        paciente.setDni(dto.getDni());
        paciente.setTelefono(dto.getTelefono());
        paciente.setDireccion(dto.getDireccion());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setSexo(dto.getSexo());
        paciente.setEstado(dto.getEstado());
        paciente.setUsuario(usuario);

        Paciente saved = pacienteRepo.save(paciente);

        return convertToDTO(saved);
    }

    @Override
    public PacienteDTO update(PacienteDTO dto, Integer id){
        Paciente paciente = pacienteRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontro el paciente con ID: "+id));

        Usuario usuario = usuarioRepo.findById(dto.getIdUsuario()).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontro el usuario con ID: "+dto.getIdUsuario()));

        paciente.setNombres(dto.getNombres());
        paciente.setApellidos(dto.getApellidos());
        paciente.setDni(dto.getDni());
        paciente.setTelefono(dto.getTelefono());
        paciente.setDireccion(dto.getDireccion());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setSexo(dto.getSexo());
        paciente.setEstado(dto.getEstado());
        paciente.setUsuario(usuario);

        Paciente updated = pacienteRepo.save(paciente);

        return convertToDTO(updated);
    }

    @Override
    public List<PacienteDTO> findAll(){
        return pacienteRepo.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    public PacienteDTO findById(Integer id){
        Paciente paciente = pacienteRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontró el paciente con ID: "+ id));

        return convertToDTO(paciente);
    }

    @Override
    public void delete(Integer id){
        if(!pacienteRepo.existsById(id)){
            throw new ResourceNotFoundExceptions("No se encontró el paciente con ID: "+id);
        }
        pacienteRepo.deleteById(id);
    }

    private PacienteDTO convertToDTO(Paciente paciente){
        PacienteDTO dto = new PacienteDTO();
        dto.setIdPaciente(paciente.getIdPaciente());
        dto.setNombres(paciente.getNombres());
        dto.setApellidos(paciente.getApellidos());
        dto.setDni(paciente.getDni());
        dto.setTelefono(paciente.getTelefono());
        dto.setDireccion(paciente.getDireccion());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setSexo(paciente.getSexo());
        dto.setEstado(paciente.getEstado());
        dto.setIdUsuario(paciente.getUsuario().getIdUsuario());
        return dto;
    }
    
}
