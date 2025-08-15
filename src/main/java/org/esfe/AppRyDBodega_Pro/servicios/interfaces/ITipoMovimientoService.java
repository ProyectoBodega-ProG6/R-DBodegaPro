package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.TipoMovimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ITipoMovimientoService{

    Page<TipoMovimiento> buscarTodosPaginados(Pageable pageable);
    List<TipoMovimiento> obtenerTodos();
    Optional<TipoMovimiento> buscarPorId(Integer id);
    TipoMovimiento createOrEditOne(TipoMovimiento tipoMovimiento);
    void eliminarPorId(Integer id);
    Page<TipoMovimiento> findByNombreContainingIgnoreCaseOrderByIdAsc(String nombre, Pageable pageable);
}