package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService extends JpaRepository<Categoria, Integer> {

    Page<Categoria> buscarTodosPaginados(Pageable pageable);

    List<Categoria> obtenerTodos();

    Optional<Categoria> buscarPorId(Integer id);

    Categoria createOrEditOne(Categoria categoria);

    void eliminarPorId(Integer id);

    Page<Categoria> findByNombreContainingIgnoreCaseOrderByIdAsc(String nombre, Pageable pageable);

}
