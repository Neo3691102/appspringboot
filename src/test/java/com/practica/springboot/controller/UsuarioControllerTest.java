package com.practica.springboot.controller;

import com.practica.springboot.dto.Usuarios;
import com.practica.springboot.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void obtenerUsuarioXIdCuandoNOExiste(){
        long idUsuario = 1;

        //Configuramos el comportamiento del mock
        when(usuarioservice.obtenerUsuarioPorId(idUsuario)).thenReturn(Optional.empty());

        //Ejecutamos el metodo del controlador
        ResponseEntity<Usuarios> usuarioResponseEntity = usuarioController.obtenerUsuarioXId(idUsuario);
        Usuarios usuarioActual = usuarioResponseEntity.getBody();

        //Validamos el resultado
        assertEquals(404, usuarioResponseEntity.getStatusCode().value());
        assertTrue(Objects.isNull(usuarioActual));
    }

    @Test
    void crearUsuario() throws Exception {
        Usuarios usuarioExpected = crearUsuarios(1).get(0);

        //Configuramos el comportamiento del mock
        when(usuarioservice.crearUsuario(usuarioExpected)).thenReturn(usuarioExpected);

        //Ejecutamos el metodo del controlador
        ResponseEntity<Usuarios> usuariosResponseEntity = usuarioController.crearUsuario(usuarioExpected);
        Usuarios usuarioActual = usuariosResponseEntity.getBody();

        //validamos el resultado
        assertEquals(201, usuariosResponseEntity.getStatusCode().value());
        assertTrue(Objects.isNull(usuarioActual));
    }

    @Test
    void actualizarUsuario() {
        int idUsuario = 5;
        String nombreActualizado = "Beatriz";
        int edadActualizada = 25;

        Usuarios usuarioAntiguo = new Usuarios();
        usuarioAntiguo.setIdUsuario(idUsuario);
        usuarioAntiguo.setNombre("Pedro");
        usuarioAntiguo.setEdad(28);

        Usuarios usuarioActualizado = new Usuarios();
        usuarioActualizado.setNombre(nombreActualizado);
        usuarioActualizado.setEdad(edadActualizada);

        //Comfiguramos el comportamiento del mock
        when(usuarioservice.obtenerUsuarioPorId((long) idUsuario)).thenReturn(Optional.of(usuarioAntiguo));
        doNothing().when(usuarioservice).actualizarUsuario(usuarioActualizado);

        //Ejecutamos el metodo del controlador
        ResponseEntity<Usuarios> usuarioResponseEntity = usuarioController.actualizarUsuario((long) idUsuario, usuarioActualizado);
        Usuarios usuarioActual = usuarioResponseEntity.getBody();

        //validamos el resultado
        assertEquals(200, usuarioResponseEntity.getStatusCode().value());
        assertNotNull(usuarioActual);
        assertEquals(idUsuario, usuarioActual.getIdUsuario());
    }

    @Test
    void actualizarUsuarioCuandoNoexiste(){
        long idUsuario = 5;
        String nombreActualizado = "Beatriz";
        int edadActualizada = 25;

        Usuarios usuarioActualizado = new Usuarios();
        usuarioActualizado.setNombre(nombreActualizado);
        usuarioActualizado.setEdad(edadActualizada);

        //Configuramos el comportamiento del mock
        when(usuarioservice.obtenerUsuarioPorId(idUsuario)).thenReturn(Optional.empty());

        //Ejecutamos el metodo del controlador
        ResponseEntity<Usuarios> usuariosResponseEntity = usuarioController.actualizarUsuario(idUsuario, usuarioActualizado);
        Usuarios usuarioActual = usuariosResponseEntity.getBody();

        //Validamos el resultado
        assertEquals(404, usuariosResponseEntity.getStatusCode().value());
        assertNull(usuarioActual);
        verify(usuarioservice, never()).actualizarUsuario(usuarioActualizado);
        //tambien se puede usar times en lugar de never para verificar que el metodo nunca se mandoa llamar
    }

    @Test
    void eliminarUsuario() {
        long idUsuario = 1;

        //Configuramos el comportamiento del mock
        doNothing().when(usuarioservice).eliminarUsuario(idUsuario);

        //Ejecutamos el metodo del controlador
        ResponseEntity<Void> responseEntity = usuarioController.eliminarUsuario(idUsuario);

        //Validamos el resultado
        assertEquals(204, responseEntity.getStatusCode().value());
        verify(usuarioservice, times(1)).eliminarUsuario(idUsuario);
        //en lugar de times tambien se puede usar atLeastOnce() o atLeast(1)
    }

    @Test
    void crearUsuariocuandoSeaMenorDe18() throws Exception {
        Usuarios usuario = new Usuarios();
        usuario.setIdUsuario(1);
        usuario.setNombre("Nombre");
        usuario.setEdad(10);

        //Configuramos el comportamiento del mock
        doThrow(Exception.class).when(usuarioservice).crearUsuario(usuario);

        //Ejecutamos el metodo del controlador
        ResponseEntity<Usuarios> responseEntity = usuarioController.crearUsuario(usuario);
        Usuarios usuarioActual = responseEntity.getBody();

        assertEquals(400, responseEntity.getStatusCode().value());
        assertNull(usuarioActual);
    }

//    @Test
//    void crearUsuarioConDoAnswer() throws Exception {
//        Usuarios usuarioExpected = crearUsuarios(1).get(0);
//
//        //Configuramos el comportamiento del mock
//        when(usuarioservice.crearUsuario(usuarioExpected)).thenReturn(usuarioExpected);
//        doAnswer(parametro -> {
//            return parametro;
//        }).when(usuarioservice).crearUsuario(usuarioExpected);
//
//        //Ejecutamos el metodo del controlador
//        ResponseEntity<Usuarios> responseEntity = usuarioController.crearUsuario(usuarioExpected);
//        Usuarios usuarioActual = responseEntity.getBody();
//
//        assertEquals(201, responseEntity.getStatusCode().value());
//        assertTrue(Objects.isNull(usuarioActual));
//    }

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