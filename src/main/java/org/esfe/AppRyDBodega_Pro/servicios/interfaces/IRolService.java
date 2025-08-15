package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.modelos.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IRolService {

    Page<Rol> buscarTodosPaginados(Pageable pageable);
    List<Rol> obtenerTodos();
    Optional<Rol> buscarPorId(Integer id);
    Rol createOrEditOne(Rol rol);
    void eliminarPorId (Integer id);
    Page<Rol> findByNombreRolContainingIgnoreCaseOrderByIdAsc(String nombreRol, Pageable pageable);
}
