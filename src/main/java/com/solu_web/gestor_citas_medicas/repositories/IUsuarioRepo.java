package com.solu_web.gestor_citas_medicas.repositories;

import com.solu_web.gestor_citas_medicas.models.Usuario;

public interface IUsuarioRepo extends IGenericRepo<Usuario, Integer> {
    
    boolean existsByCorreo(String correo);
    boolean existsByCorreoAndIdUsuarioNot(String correo, Integer idUsuario);
}
