package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.esfe.AppRyDBodega_Pro.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {

    Page<Categoria> buscarTodosPaginados(Pageable pageable);

    List<Categoria> obtenerTodos();

    Optional<Categoria> buscarPorId(Integer id);

    Categoria createOrEditOne(Categoria categoria);

    void eliminarPorId(Integer id);

    List<Categoria> findByNombreIgnoreCase(String nombre);

    Page<Categoria> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    List<Categoria> findAllByOrderByNombreAsc();

    Page<Categoria> findAllByOrderByNombreAsc(Pageable pageable);

}
