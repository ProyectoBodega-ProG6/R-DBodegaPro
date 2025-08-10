package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.MovimientoEntradaSalida;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovimientoEntradaSalidaRepository extends JpaRepository<MovimientoEntradaSalida, Integer> {

    Page<MovimientoEntradaSalida> findByProductoNombreContainingIgnoreCaseOrderByFechaAsc(String nombre, Pageable pageable);
    Page<MovimientoEntradaSalida> findByTipoMovimientoIdOrderByFechaAsc(Integer idTipoMovimiento, Pageable pageable);
    Page<MovimientoEntradaSalida> findByProductoNombreContainingIgnoreCaseAndTipoMovimientoIdOrderByFechaAsc(String nombre, Integer id, Pageable pageable);
    Page<MovimientoEntradaSalida> findAllByOrderByFechaAsc(Pageable pageable);
    Page<MovimientoEntradaSalida> findByProductoIdOrderByFechaAsc(Integer id, Pageable pageable);
}
