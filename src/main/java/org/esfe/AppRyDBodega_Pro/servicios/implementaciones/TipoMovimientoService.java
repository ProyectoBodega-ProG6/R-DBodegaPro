package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.modelos.TipoMovimiento;
import org.esfe.AppRyDBodega_Pro.repositorios.ITipoMovimientoRepository;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.ITipoMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public  class TipoMovimientoService implements ITipoMovimientoService {

    @Autowired
    private ITipoMovimientoRepository tipoMovimientoRepository;

    @Override
    public Page<TipoMovimiento> buscarTodosPaginados(Pageable pageable) {
        return tipoMovimientoRepository.findAll(pageable);
    }

    @Override
    public List<TipoMovimiento> obtenerTodos() {
        return tipoMovimientoRepository.findAll();
    }

    @Override
    public Optional<TipoMovimiento> buscarPorId(Integer id) {
        return tipoMovimientoRepository.findById(id);
    }

    @Override
    public TipoMovimiento createOrEditOne(TipoMovimiento tipoMovimiento) {
        return tipoMovimientoRepository.save(tipoMovimiento);
    }

    @Override
    public void eliminarPorId(Integer id) {
        tipoMovimientoRepository.deleteById(id);
    }

    @Override
    public Page<TipoMovimiento> findByNombreContainingIgnoreCaseOrderByIdAsc(String nombre, Pageable pageable) {
        return tipoMovimientoRepository.findByNombreContainingIgnoreCaseOrderByIdAsc(nombre, pageable);
    }
}