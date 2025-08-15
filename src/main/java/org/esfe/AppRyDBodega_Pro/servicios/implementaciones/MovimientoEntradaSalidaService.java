package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.modelos.MovimientoEntradaSalida;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IMovimientoEntradaSalidaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MovimientoEntradaSalidaService implements IMovimientoEntradaSalidaService {
    @Override
    public Page<MovimientoEntradaSalida> buscarTodosPaginados(Pageable pageable) {
        return null;
    }

    @Override
    public List<MovimientoEntradaSalida> obtenerTodos() {
        return List.of();
    }

    @Override
    public Optional<MovimientoEntradaSalida> buscarPorId(Integer id) {
        return Optional.empty();
    }

    @Override
    public MovimientoEntradaSalida createOrEditOne(MovimientoEntradaSalida movimientoEntradaSalida) {
        return null;
    }

    @Override
    public void eliminarPorId(Integer id) {

    }

    @Override
    public Page<MovimientoEntradaSalida> findByProductoNombreContainingIgnoreCaseAndTipoMovimientoNombreOrderByIdAsc(String nombreProducto, String nombre, Pageable pageable) {
        return null;
    }

    @Override
    public List<MovimientoEntradaSalida> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin) {
        return List.of();
    }
}
