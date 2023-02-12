package com.minicursoadsfg.minicursoadsfg.dominio.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AutorModelo;

@Repository
public interface AutorRepositorio extends JpaRepository<AutorModelo, Integer> {

}
