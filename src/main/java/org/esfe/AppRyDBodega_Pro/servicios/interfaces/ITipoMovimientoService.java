package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.modelos.TipoMovimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ITipoMovimientoService {

    Page<Producto> buscarTodosPaginados(Pageable pageable);
    List<Producto> obtenerTodos();
    Optional<Producto> buscarPorId(Integer id);
    Producto createOrEditOne(Producto producto);
    void eliminarPorId(Integer id);
    Page<TipoMovimiento> listarPaginadoOrdenado(Pageable pageable);
    Page<TipoMovimiento> buscarPorNombre(String nombre, Pageable pageable);
}