package com.solu_web.gestor_citas_medicas.services.ipml;

import com.solu_web.gestor_citas_medicas.dtos.HorarioDoctorDTO;
import com.solu_web.gestor_citas_medicas.exceptions.ResourceNotFoundExceptions;
import com.solu_web.gestor_citas_medicas.models.Doctor;
import com.solu_web.gestor_citas_medicas.models.HorarioDoctor;
import com.solu_web.gestor_citas_medicas.repositories.IDoctorRepo;
import com.solu_web.gestor_citas_medicas.repositories.IHorarioDoctorRepo;
import com.solu_web.gestor_citas_medicas.services.IHorarioDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioDoctorService implements IHorarioDoctorService {

    private final IHorarioDoctorRepo horarioDoctorRepo;
    private final IDoctorRepo doctorRepo;

    @Override
    public HorarioDoctorDTO save(HorarioDoctorDTO dto) {

        Doctor doctor = doctorRepo.findById(dto.getIdDoctor())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el doctor con ID: " + dto.getIdDoctor()
                ));

        HorarioDoctor horario = new HorarioDoctor();
        horario.setDia(dto.getDia());
        horario.setHoraInicio(dto.getHoraInicio());
        horario.setHoraFin(dto.getHoraFin());
        horario.setCuposDisponibles(dto.getCuposDisponibles());
        horario.setEstado(dto.getEstado());
        horario.setDoctor(doctor);

        HorarioDoctor saved = horarioDoctorRepo.save(horario);

        return convertToDTO(saved);
    }

    @Override
    public HorarioDoctorDTO update(HorarioDoctorDTO dto, Integer id) {

        HorarioDoctor horario = horarioDoctorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el horario con ID: " + id
                ));

        Doctor doctor = doctorRepo.findById(dto.getIdDoctor())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el doctor con ID: " + dto.getIdDoctor()
                ));

        horario.setDia(dto.getDia());
        horario.setHoraInicio(dto.getHoraInicio());
        horario.setHoraFin(dto.getHoraFin());
        horario.setCuposDisponibles(dto.getCuposDisponibles());
        horario.setEstado(dto.getEstado());
        horario.setDoctor(doctor);

        HorarioDoctor updated = horarioDoctorRepo.save(horario);

        return convertToDTO(updated);
    }

    @Override
    public List<HorarioDoctorDTO> findAll() {
        return horarioDoctorRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public HorarioDoctorDTO findById(Integer id) {
        HorarioDoctor horario = horarioDoctorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el horario con ID: " + id
                ));

        return convertToDTO(horario);
    }

    @Override
    public void delete(Integer id) {
        if (!horarioDoctorRepo.existsById(id)) {
            throw new ResourceNotFoundExceptions(
                    "No se encontró el horario con ID: " + id
            );
        }

        horarioDoctorRepo.deleteById(id);
    }

    private HorarioDoctorDTO convertToDTO(HorarioDoctor horario) {
        HorarioDoctorDTO dto = new HorarioDoctorDTO();

        dto.setIdHorario(horario.getIdHorario());
        dto.setDia(horario.getDia());
        dto.setHoraInicio(horario.getHoraInicio());
        dto.setHoraFin(horario.getHoraFin());
        dto.setCuposDisponibles(horario.getCuposDisponibles());
        dto.setEstado(horario.getEstado());
        dto.setIdDoctor(horario.getDoctor().getIdDoctor());

        return dto;
    }
}