package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.modelos.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRolService extends JpaRepository<Rol, Integer> {

    Page<Producto> buscarTodosPaginados(Pageable pageable);
    List<Producto> obtenerTodos();
    Optional<Producto> buscarPorId(Integer id);
    Producto createOrEditOne(Rol rol);
    void eliminarPorId (Integer id);
    Page<Rol> findByNombreRolContainingIgnoreCaseOrderByIdAsc(String nombreRol, Pageable pageable);
}
