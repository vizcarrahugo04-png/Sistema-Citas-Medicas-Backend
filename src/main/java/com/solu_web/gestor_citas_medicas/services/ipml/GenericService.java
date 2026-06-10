package com.solu_web.gestor_citas_medicas.services.ipml;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.solu_web.gestor_citas_medicas.exceptions.ResourceNotFoundExceptions;
import com.solu_web.gestor_citas_medicas.repositories.IGenericRepo;
import com.solu_web.gestor_citas_medicas.services.IGenericService;

public abstract class GenericService<T,DTO,ID> implements IGenericService<DTO,ID> {
    
    protected abstract IGenericRepo<T,ID> getRepo();
    protected abstract Class<T> getEntityClass();
    protected abstract Class<DTO> getDTOClass();

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public DTO save(DTO dto){
        T entity = modelMapper.map(dto, getEntityClass());
        T save = getRepo().save(entity);
        return modelMapper.map(save, getDTOClass());
    }

    @Override
    public DTO update(DTO dto, ID id){
        if(!getRepo().existsById(id)){
            throw new ResourceNotFoundExceptions("No se encontro el registro con ID: "+id);
        }

        T entity = modelMapper.map(dto, getEntityClass());
        T update = getRepo().save(entity);

        return modelMapper.map(update, getDTOClass());
    }

    @Override
    public List<DTO> findAll(){
        return getRepo().findAll().stream().map(entity -> modelMapper.map(entity, getDTOClass())).toList();
    }

    @Override
    public DTO findById(ID id){
        T entity = getRepo().findById(id).orElseThrow(() -> new ResourceNotFoundExceptions("No se encontro el registro con ID: "+ id));

        return modelMapper.map(entity, getDTOClass());
    }

    @Override
    public void delete(ID id){
        if(!getRepo().existsById(id)){
            throw new ResourceNotFoundExceptions("No se encontro el registro con ID: "+ id);
        }
        getRepo().deleteById(id);
    }

}
