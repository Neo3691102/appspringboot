package com.practica.springboot.controller;

import com.practica.springboot.dto.Usuarios;
import com.practica.springboot.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    UsuarioService usuarioservice;

    @InjectMocks
    UsuarioController usuarioController;

    @Test
    void obtenerUsuarios() {
        int usuarios = 5;
        List<Usuarios> usuariosListExpected = crearUsuarios(usuarios);

        //Configuramos el comportamiento del mock
        when(usuarioservice.obtenerUsuarios()).thenReturn(usuariosListExpected);

        //ejecutamos el metodo del controlador
        List<Usuarios> usuariosListActual = usuarioController.obtenerUsuarios();

        //Validamos el resultado
        assertEquals(usuarios, usuariosListActual.size());
        assertEquals(usuariosListExpected, usuariosListActual);
    }

    @Test
    void obtenerUsuarioXId() {
        long idUsuario = 1;
        Optional<Usuarios> usuarioExpected = Optional.of(crearUsuarios(1).get(0));

        //Connfiguramos el comportamiento del mock
        when(usuarioservice.obtenerUsuarioPorId(idUsuario)).thenReturn(usuarioExpected);

        //Ejecutamos el metodo del controlador
        ResponseEntity<Usuarios> usuariosResponseEntity = usuarioController.obtenerUsuarioXId(idUsuario);
        Usuarios usuarioActual = usuariosResponseEntity.getBody();

        //validamos el resultado
        assertEquals(200, usuariosResponseEntity.getStatusCode().value());
        assertNotNull(usuarioActual);
        assertEquals("Nombre1", usuarioActual.getNombre());
    }

    @Test
    void crearUsuario() {
    }

    @Test
    void actualizarUsuario() {
    }

    @Test
    void eliminarUsuario() {
    }

    private List<Usuarios> crearUsuarios(int elementos){
        return IntStream.range(1, elementos+1)
                .mapToObj(i -> {
                    Usuarios usuario = new Usuarios();
                    usuario.setIdUsuario(i);
                    usuario.setNombre("Nombre" + i);
                    usuario.setEdad(15 + i);
                    return usuario;
                }).collect(Collectors.toList());

    }
}