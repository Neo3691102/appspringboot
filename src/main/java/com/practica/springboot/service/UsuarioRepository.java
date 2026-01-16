package com.practica.springboot.service;

import com.practica.springboot.dto.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
}
