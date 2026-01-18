package com.practica.springboot.service;

import com.practica.springboot.dto.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuarios crearUsuario(Usuarios usuario) throws Exception {
        if(usuario.getEdad() >= 18){
            return usuarioRepository.save(usuario);
        }

        throw new Exception("No se permiten usuarios menores de 18 años");
    }

    public Optional<Usuarios> obtenerUsuarioPorId(Long idUsuario){
        return usuarioRepository.findById(idUsuario);
    }

    public List<Usuarios> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    public void actualizarUsuario(Usuarios usuario){
        usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }
}
