package com.solu_web.gestor_citas_medicas.services.ipml;

import org.springframework.stereotype.Service;

import com.solu_web.gestor_citas_medicas.dtos.EspecialidadDTO;
import com.solu_web.gestor_citas_medicas.models.Especialidad;
import com.solu_web.gestor_citas_medicas.repositories.IEspecialidadRepo;
import com.solu_web.gestor_citas_medicas.repositories.IGenericRepo;
import com.solu_web.gestor_citas_medicas.services.IEspecialidadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspecialidadService extends GenericService<Especialidad,EspecialidadDTO,Integer> implements IEspecialidadService {
    
        
    private final IEspecialidadRepo repo;

    @Override
    protected IGenericRepo<Especialidad,Integer> getRepo(){
        return repo;
    }

    @Override
    protected Class<Especialidad> getEntityClass(){
        return Especialidad.class;
    }

    @Override
    protected Class<EspecialidadDTO> getDTOClass(){
        return EspecialidadDTO.class;
    }
}
