package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.TipoMovimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITipoMovimientoRepository extends JpaRepository<TipoMovimiento, Integer> {
    Page<TipoMovimiento> findByNombreContainingIgnoreCaseOrderByIdAsc(String nombre, Pageable pageable);
    Optional<TipoMovimiento> findByNombre(String nombre);
}
