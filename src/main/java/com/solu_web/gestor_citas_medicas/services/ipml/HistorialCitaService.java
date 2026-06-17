package com.solu_web.gestor_citas_medicas.services.ipml;

import com.solu_web.gestor_citas_medicas.dtos.HistorialCitaDTO;
import com.solu_web.gestor_citas_medicas.exceptions.ResourceNotFoundExceptions;
import com.solu_web.gestor_citas_medicas.models.CitaMedica;
import com.solu_web.gestor_citas_medicas.models.HistorialCita;
import com.solu_web.gestor_citas_medicas.repositories.ICitaMedicaRepo;
import com.solu_web.gestor_citas_medicas.repositories.IHistorialCitaRepo;
import com.solu_web.gestor_citas_medicas.services.IHistorialCitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistorialCitaService implements IHistorialCitaService {

    private final IHistorialCitaRepo historialRepo;
    private final ICitaMedicaRepo citaRepo;

    @Override
    public HistorialCitaDTO save(HistorialCitaDTO dto) {

        CitaMedica cita = citaRepo.findById(dto.getIdCita())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró la cita con ID: " + dto.getIdCita()
                ));

        HistorialCita historial = new HistorialCita();
        historial.setFechaCambio(dto.getFechaCambio());
        historial.setEstadoAnterior(dto.getEstadoAnterior());
        historial.setEstadoNuevo(dto.getEstadoNuevo());
        historial.setObservacion(dto.getObservacion());
        historial.setCitaMedica(cita);

        HistorialCita saved = historialRepo.save(historial);

        return convertToDTO(saved);
    }

    @Override
    public HistorialCitaDTO update(HistorialCitaDTO dto, Integer id) {

        HistorialCita historial = historialRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el historial con ID: " + id
                ));

        CitaMedica cita = citaRepo.findById(dto.getIdCita())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró la cita con ID: " + dto.getIdCita()
                ));

        historial.setFechaCambio(dto.getFechaCambio());
        historial.setEstadoAnterior(dto.getEstadoAnterior());
        historial.setEstadoNuevo(dto.getEstadoNuevo());
        historial.setObservacion(dto.getObservacion());
        historial.setCitaMedica(cita);

        HistorialCita updated = historialRepo.save(historial);

        return convertToDTO(updated);
    }

    @Override
    public List<HistorialCitaDTO> findAll() {
        return historialRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public HistorialCitaDTO findById(Integer id) {
        HistorialCita historial = historialRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "No se encontró el historial con ID: " + id
                ));

        return convertToDTO(historial);
    }

    @Override
    public void delete(Integer id) {
        if (!historialRepo.existsById(id)) {
            throw new ResourceNotFoundExceptions(
                    "No se encontró el historial con ID: " + id
            );
        }

        historialRepo.deleteById(id);
    }

    private HistorialCitaDTO convertToDTO(HistorialCita historial) {
        HistorialCitaDTO dto = new HistorialCitaDTO();

        dto.setIdHistorial(historial.getIdHistorial());
        dto.setFechaCambio(historial.getFechaCambio());
        dto.setEstadoAnterior(historial.getEstadoAnterior());
        dto.setEstadoNuevo(historial.getEstadoNuevo());
        dto.setObservacion(historial.getObservacion());
        dto.setIdCita(historial.getCitaMedica().getIdCita());

        return dto;
    }
}