package com.practica.springboot.controller;

import com.practica.springboot.dto.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuarios(){
        return List.of(new Usuario());
    }

    @GetMapping("/usuarios/{id}")
    public Usuario obtenerUsuarioXId(@PathVariable Long id){
        System.out.println("Id recibido: " + id);
        return new Usuario();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){
        System.out.println("Usuario recibido: " + usuario);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizar){
        System.out.println("id recibido: " + id);
        System.out.println("Usuario recibido: " + usuarioActualizar);
        return ResponseEntity.ok(usuarioActualizar);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id){
        System.out.println("id recibido: " + id);
        return ResponseEntity.noContent().build();
    }
}
