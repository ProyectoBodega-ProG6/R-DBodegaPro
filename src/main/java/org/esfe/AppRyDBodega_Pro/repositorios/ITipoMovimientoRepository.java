package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.TipoMovimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITipoMovimientoRepository extends JpaRepository<TipoMovimiento, Integer> {

    List<TipoMovimiento> findAll(Sort sort);
    List<TipoMovimiento> findAllByOrderByNombreAsc();

    Page<TipoMovimiento> findAll(Pageable pageable);

    Page<TipoMovimiento> findAllByOrderByNombreAsc(Pageable pageable);


}
