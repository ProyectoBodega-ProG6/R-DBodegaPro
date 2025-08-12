package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.TipoMovimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITipoMovimientoRepository extends JpaRepository<TipoMovimiento, Integer> {
    Page<TipoMovimiento> findAllByOrderByIdAsc(Pageable pageable);
    Page<TipoMovimiento> findByNombreContainingIgnoreCaseOrderByIdAsc(String nombre, Pageable pageable);
}
