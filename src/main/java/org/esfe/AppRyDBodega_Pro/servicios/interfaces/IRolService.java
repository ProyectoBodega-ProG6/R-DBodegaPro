package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.modelos.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRolService {

    Page<Producto> buscarTodosPaginados(Pageable pageable);
    List<Producto> obtenerTodos();
    Optional<Producto> buscarPorId(Integer id);
    Producto createOrEditOne(Producto producto);
    void eliminarPorId (Integer id);
    Page<Rol> listarPaginadoOrdenado(Pageable pageable);
    Page<Rol> buscarPorNombreRol(String nombreRol, Pageable pageable);
}
