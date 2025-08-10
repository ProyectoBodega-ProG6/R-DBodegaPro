package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByNombreIgnoreCase(String nombre);
    Page<Categoria> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    List<Categoria> findAllByOrderByNombreAsc();
    Page<Categoria> findAllByOrderByNombreAsc(Pageable pageable);
}
