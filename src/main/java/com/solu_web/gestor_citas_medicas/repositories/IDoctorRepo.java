package com.solu_web.gestor_citas_medicas.repositories;

import com.solu_web.gestor_citas_medicas.models.Doctor;

public interface IDoctorRepo extends IGenericRepo<Doctor, Integer> {
    
    boolean existsByCmp(String cmp);
    boolean existsByCmpAndIdDoctorNot(String cmp, Integer idDoctor);

    boolean existsByCorreoProfesional(String correoProfesional);
    boolean existsByCorreoProfesionalAndIdDoctorNot(String correoProfesional, Integer idDoctor);

    boolean existsByUsuario_IdUsuario(Integer idUsuario);
boolean existsByUsuario_IdUsuarioAndIdDoctorNot(Integer idUsuario, Integer idDoctor);

}
