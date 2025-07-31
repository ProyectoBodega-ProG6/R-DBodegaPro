package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface IProductoService {
    Page<Producto> buscarTodosPaginados(Pageable pageable);
    List<Producto> obtenerTodos();
    Optional<Producto> buscarPorId(Integer id);
    Producto crearOEditttar(Producto producto);
    void eliminarPorId(Integer id);

}
