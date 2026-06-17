package com.solu_web.gestor_citas_medicas.repositories;


import com.solu_web.gestor_citas_medicas.models.Paciente;

public interface IPacienteRepo extends IGenericRepo<Paciente,Integer>{

    boolean existsByDni(String dni);
    boolean existsByDniAndIdPacienteNot(String dni, Integer idPaciente);

    boolean existsByUsuario_IdUsuario(Integer idUsuario);
    boolean existsByUsuario_IdUsuarioAndIdPacienteNot(Integer idUsuario, Integer idPaciente);
}
