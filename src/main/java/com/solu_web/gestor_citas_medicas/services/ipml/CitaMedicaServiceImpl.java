package com.solu_web.gestor_citas_medicas.services.ipml;

import com.solu_web.gestor_citas_medicas.dtos.CitaMedicaDTO;
import com.solu_web.gestor_citas_medicas.exceptions.ResourceNotFoundExceptions;
import com.solu_web.gestor_citas_medicas.models.*;
import com.solu_web.gestor_citas_medicas.repositories.*;
import com.solu_web.gestor_citas_medicas.services.ICitaMedicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaMedicaServiceImpl implements ICitaMedicaService {

private final ICitaMedicaRepo citaRepo;
private final IPacienteRepo pacienteRepo;
private final IDoctorRepo doctorRepo;
private final IHorarioDoctorRepo horarioRepo;
private final IConsultorioRepo consultorioRepo;

@Override
public CitaMedicaDTO save(CitaMedicaDTO dto) {

        Paciente paciente = pacienteRepo.findById(dto.getIdPaciente())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el paciente con ID: " + dto.getIdPaciente()));

        Doctor doctor = doctorRepo.findById(dto.getIdDoctor())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el doctor con ID: " + dto.getIdDoctor()));

        HorarioDoctor horario = horarioRepo.findById(dto.getIdHorario())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el horario con ID: " + dto.getIdHorario()));

        Consultorio consultorio = consultorioRepo.findById(dto.getIdConsultorio())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el consultorio con ID: " + dto.getIdConsultorio()));

        CitaMedica cita = new CitaMedica();

        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setMotivo(dto.getMotivo());
        cita.setEstado(dto.getEstado());

        cita.setPaciente(paciente);
        cita.setDoctor(doctor);
        cita.setHorarioDoctor(horario);
        cita.setConsultorio(consultorio);

        CitaMedica saved = citaRepo.save(cita);

        return convertToDTO(saved);
    }

    @Override
    public CitaMedicaDTO update(CitaMedicaDTO dto, Integer id) {

        CitaMedica cita = citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró la cita con ID: " + id));

        Paciente paciente = pacienteRepo.findById(dto.getIdPaciente())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el paciente con ID: " + dto.getIdPaciente()));

        Doctor doctor = doctorRepo.findById(dto.getIdDoctor())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el doctor con ID: " + dto.getIdDoctor()));

        HorarioDoctor horario = horarioRepo.findById(dto.getIdHorario())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el horario con ID: " + dto.getIdHorario()));

        Consultorio consultorio = consultorioRepo.findById(dto.getIdConsultorio())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el consultorio con ID: " + dto.getIdConsultorio()));

        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setMotivo(dto.getMotivo());
        cita.setEstado(dto.getEstado());

        cita.setPaciente(paciente);
        cita.setDoctor(doctor);
        cita.setHorarioDoctor(horario);
        cita.setConsultorio(consultorio);

        CitaMedica updated = citaRepo.save(cita);

        return convertToDTO(updated);
    }

    @Override
    public List<CitaMedicaDTO> findAll() {

        return citaRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public CitaMedicaDTO findById(Integer id) {

        CitaMedica cita = citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró la cita con ID: " + id));

        return convertToDTO(cita);
    }

    @Override
    public void delete(Integer id) {

        if (!citaRepo.existsById(id)) {
            throw new ResourceNotFoundExceptions(
                    "No se encontró la cita con ID: " + id);
        }

        citaRepo.deleteById(id);
    }

    private CitaMedicaDTO convertToDTO(CitaMedica cita) {

        CitaMedicaDTO dto = new CitaMedicaDTO();

        dto.setIdCita(cita.getIdCita());
        dto.setFecha(cita.getFecha());
        dto.setHora(cita.getHora());
        dto.setMotivo(cita.getMotivo());
        dto.setEstado(cita.getEstado());

        dto.setIdPaciente(cita.getPaciente().getIdPaciente());
        dto.setIdDoctor(cita.getDoctor().getIdDoctor());
        dto.setIdHorario(cita.getHorarioDoctor().getIdHorario());
        dto.setIdConsultorio(cita.getConsultorio().getIdConsultorio());

        return dto;
    }
}