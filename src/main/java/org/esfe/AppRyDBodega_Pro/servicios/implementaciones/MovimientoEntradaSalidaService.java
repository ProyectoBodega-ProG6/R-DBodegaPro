package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.modelos.MovimientoEntradaSalida;
import org.esfe.AppRyDBodega_Pro.repositorios.ICategoriaRepository;
import org.esfe.AppRyDBodega_Pro.repositorios.IMovimientoEntradaSalidaRepository;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IMovimientoEntradaSalidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MovimientoEntradaSalidaService implements IMovimientoEntradaSalidaService {

    @Autowired
    private IMovimientoEntradaSalidaRepository movimientoEntradaSalidaRepository;

    @Override
    public Page<MovimientoEntradaSalida> buscarTodosPaginados(Pageable pageable) {
        return movimientoEntradaSalidaRepository.findAll(pageable);
    }

    @Override
    public List<MovimientoEntradaSalida> obtenerTodos() {
        return movimientoEntradaSalidaRepository.findAll();
    }

    @Override
    public Optional<MovimientoEntradaSalida> buscarPorId(Integer id) {
        return movimientoEntradaSalidaRepository.findById(id);
    }

    @Override
    public MovimientoEntradaSalida createOrEditOne(MovimientoEntradaSalida movimientoEntradaSalida) {
        return movimientoEntradaSalidaRepository.save(movimientoEntradaSalida);
    }

    @Override
    public void eliminarPorId(Integer id) {
        movimientoEntradaSalidaRepository.deleteById(id);
    }

    @Override
    public Page<MovimientoEntradaSalida> findByProductoNombreContainingIgnoreCaseAndTipoMovimientoNombreOrderByIdAsc(String nombreProducto, String nombre, Pageable pageable) {
        return movimientoEntradaSalidaRepository.findByProductoNombreContainingIgnoreCaseAndTipoMovimientoNombreOrderByIdAsc(nombreProducto, nombre, pageable);
    }

    @Override
    public List<MovimientoEntradaSalida> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin) {
        return movimientoEntradaSalidaRepository.findByFechaBetween(inicio, fin);
    }
}
