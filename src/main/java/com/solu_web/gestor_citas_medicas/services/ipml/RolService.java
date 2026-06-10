package com.solu_web.gestor_citas_medicas.services.ipml;

import org.springframework.stereotype.Service;

import com.solu_web.gestor_citas_medicas.dtos.RolDTO;
import com.solu_web.gestor_citas_medicas.models.Rol;
import com.solu_web.gestor_citas_medicas.repositories.IGenericRepo;
import com.solu_web.gestor_citas_medicas.repositories.IRolRepo;
import com.solu_web.gestor_citas_medicas.services.IRolService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService extends GenericService<Rol,RolDTO, Integer> implements IRolService {
    
    private final IRolRepo repo;

    @Override
    protected IGenericRepo<Rol,Integer> getRepo(){
        return repo;
    }

    @Override
    protected Class<Rol> getEntityClass(){
        return Rol.class;
    }

    @Override
    protected Class<RolDTO> getDTOClass(){
        return RolDTO.class;
    }
}
