package com.solu_web.gestor_citas_medicas.services.ipml;

import org.springframework.stereotype.Service;

import com.solu_web.gestor_citas_medicas.dtos.ConsultorioDTO;
import com.solu_web.gestor_citas_medicas.models.Consultorio;
import com.solu_web.gestor_citas_medicas.repositories.IConsultorioRepo;
import com.solu_web.gestor_citas_medicas.repositories.IGenericRepo;
import com.solu_web.gestor_citas_medicas.services.IConsultorioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultorioService extends GenericService<Consultorio, ConsultorioDTO, Integer> implements IConsultorioService {
    
    
        
    private final IConsultorioRepo repo;

    @Override
    protected IGenericRepo<Consultorio,Integer> getRepo(){
        return repo;
    }

    @Override
    protected Class<Consultorio> getEntityClass(){
        return Consultorio.class;
    }

    @Override
    protected Class<ConsultorioDTO> getDTOClass(){
        return ConsultorioDTO.class;
    }
}
