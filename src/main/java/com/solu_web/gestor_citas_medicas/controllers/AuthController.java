package com.solu_web.gestor_citas_medicas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solu_web.gestor_citas_medicas.dtos.LoginRequestDTO;
import com.solu_web.gestor_citas_medicas.dtos.LoginResponseDTO;
import com.solu_web.gestor_citas_medicas.jwt.JwtUtil;
import com.solu_web.gestor_citas_medicas.models.Usuario;
import com.solu_web.gestor_citas_medicas.repositories.IUsuarioRepo;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUsuarioRepo usuarioRepo;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request){

        Usuario usuario = usuarioRepo.findByCorreo(request.getCorreo()).orElseThrow(()-> new RuntimeException("Correo no encontrado"));


        if(!usuario.getEstado()){
            throw new RuntimeException("Usuario inactivo");
        }


        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
        throw new RuntimeException("Contraseña incorrecta");
        }


        String token = jwtUtil.generarToken(usuario.getCorreo(), usuario.getRol().getNombre());

        return new LoginResponseDTO(token, usuario.getCorreo(),usuario.getRol().getNombre());
    }
}
