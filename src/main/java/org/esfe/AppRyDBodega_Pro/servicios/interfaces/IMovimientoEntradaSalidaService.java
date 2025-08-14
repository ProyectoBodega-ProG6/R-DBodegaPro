package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.esfe.AppRyDBodega_Pro.modelos.MovimientoEntradaSalida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMovimientoEntradaSalidaService {
    Page<MovimientoEntradaSalida> buscarTodosPaginados(Pageable pageable);

    List<MovimientoEntradaSalida> obtenerTodos();

    Optional<MovimientoEntradaSalida> buscarPorId(Integer id);

    MovimientoEntradaSalida createOrEditOne(MovimientoEntradaSalida movimientoEntradaSalida);

    void eliminarPorId(Integer id);

    Page<MovimientoEntradaSalida> findByProductoNombreContainingIgnoreCaseAndTipoMovimientoNombreOrderByIdAsc(
            String nombreProducto,
            String nombre,
            Pageable pageable
    );

    List<MovimientoEntradaSalida> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

}
