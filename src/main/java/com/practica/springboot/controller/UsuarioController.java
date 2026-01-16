package com.practica.springboot.controller;

import com.practica.springboot.dto.Usuarios;
import com.practica.springboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuarios> obtenerUsuarios(){
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> obtenerUsuarioXId(@PathVariable Long id){
        Optional<Usuarios> optionalUsuario = usuarioService.obtenerUsuarioPorId(id);
        if(optionalUsuario.isPresent()){
            return ResponseEntity.ok(optionalUsuario.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuarios> crearUsuario(@RequestBody Usuarios usuario) throws URISyntaxException {
        Usuarios usuariocreado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.created(new URI("http://localhost/usuarios")).build();
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> actualizarUsuario(@PathVariable Long id, @RequestBody Usuarios usuariosActualizar){
        Optional<Usuarios> usuarioOptional = usuarioService.obtenerUsuarioPorId(id);
        if(usuarioOptional.isPresent()){
            usuariosActualizar.setIdUsuario(usuarioOptional.get().getIdUsuario());
            usuarioService.actualizarUsuario(usuariosActualizar);
            return ResponseEntity.ok(usuariosActualizar);
        }else{
            return ResponseEntity.notFound().build(); //responde con error 404
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
