package com.solu_web.gestor_citas_medicas.services.ipml;

import java.util.List;

import org.springframework.stereotype.Service;

import com.solu_web.gestor_citas_medicas.dtos.DoctorDTO;
import com.solu_web.gestor_citas_medicas.exceptions.ResourceNotFoundExceptions;
import com.solu_web.gestor_citas_medicas.models.Doctor;
import com.solu_web.gestor_citas_medicas.models.Especialidad;
import com.solu_web.gestor_citas_medicas.models.Usuario;
import com.solu_web.gestor_citas_medicas.repositories.IDoctorRepo;
import com.solu_web.gestor_citas_medicas.repositories.IEspecialidadRepo;
import com.solu_web.gestor_citas_medicas.repositories.IUsuarioRepo;
import com.solu_web.gestor_citas_medicas.services.IDoctorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {

    private final IDoctorRepo doctorRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IEspecialidadRepo especialidadRepo;
    
    @Override
    public DoctorDTO save(DoctorDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el usuario con ID: " + dto.getIdUsuario()
                ));

        Especialidad especialidad = especialidadRepo.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró la especialidad con ID: " + dto.getIdEspecialidad()
                ));

        if (doctorRepo.existsByCmp(dto.getCmp())) {
        throw new RuntimeException("El CMP ya está registrado");
        }

        if (doctorRepo.existsByCorreoProfesional(dto.getCorreoProfesional())) {
        throw new RuntimeException("El correo profesional ya está registrado");
        }

        if (doctorRepo.existsByUsuario_IdUsuario(dto.getIdUsuario())) {
        throw new RuntimeException("Ese usuario ya tiene un doctor asignado");
        }

        Doctor doctor = new Doctor();
        doctor.setNombres(dto.getNombres());
        doctor.setApellidos(dto.getApellidos());
        doctor.setCmp(dto.getCmp());
        doctor.setTelefono(dto.getTelefono());
        doctor.setCorreoProfesional(dto.getCorreoProfesional());
        doctor.setEstado(dto.getEstado());
        doctor.setUsuario(usuario);
        doctor.setEspecialidad(especialidad);

        Doctor saved = doctorRepo.save(doctor);
        return convertToDTO(saved);
    }

    @Override
    public DoctorDTO update(DoctorDTO dto, Integer id) {
        Doctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el doctor con ID: " + id
                ));

        Usuario usuario = usuarioRepo.findById(dto.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el usuario con ID: " + dto.getIdUsuario()
                ));

        Especialidad especialidad = especialidadRepo.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró la especialidad con ID: " + dto.getIdEspecialidad()
                ));

        if (doctorRepo.existsByCmpAndIdDoctorNot(dto.getCmp(), id)) {
        throw new RuntimeException("El CMP ya está registrado");
        }

        if (doctorRepo.existsByCorreoProfesionalAndIdDoctorNot(dto.getCorreoProfesional(), id)) {
        throw new RuntimeException("El correo profesional ya está registrado");
        }

        if (doctorRepo.existsByUsuario_IdUsuarioAndIdDoctorNot(dto.getIdUsuario(), id)) {
        throw new RuntimeException("Ese usuario ya tiene un doctor asignado");
        }

        doctor.setNombres(dto.getNombres());
        doctor.setApellidos(dto.getApellidos());
        doctor.setCmp(dto.getCmp());
        doctor.setTelefono(dto.getTelefono());
        doctor.setCorreoProfesional(dto.getCorreoProfesional());
        doctor.setEstado(dto.getEstado());
        doctor.setUsuario(usuario);
        doctor.setEspecialidad(especialidad);

        Doctor updated = doctorRepo.save(doctor);
        return convertToDTO(updated);
    }

    @Override
    public List<DoctorDTO> findAll() {
        return doctorRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public DoctorDTO findById(Integer id) {
        Doctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el doctor con ID: " + id
                ));

        return convertToDTO(doctor);
    }

    @Override
    public void delete(Integer id) {
        if (!doctorRepo.existsById(id)) {
            throw new ResourceNotFoundExceptions(
                    "No se encontró el doctor con ID: " + id
            );
        }

        doctorRepo.deleteById(id);
    }

    private DoctorDTO convertToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();

        dto.setIdDoctor(doctor.getIdDoctor());
        dto.setNombres(doctor.getNombres());
        dto.setApellidos(doctor.getApellidos());
        dto.setCmp(doctor.getCmp());
        dto.setTelefono(doctor.getTelefono());
        dto.setCorreoProfesional(doctor.getCorreoProfesional());
        dto.setEstado(doctor.getEstado());
        dto.setIdUsuario(doctor.getUsuario().getIdUsuario());
        dto.setIdEspecialidad(doctor.getEspecialidad().getIdEspecialidad());

        return dto;
    }
}
