package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.MovimientoEntradaSalida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovimientoEntradaSalidaRepository extends JpaRepository<MovimientoEntradaSalida, Integer> {

    Page<MovimientoEntradaSalida> findByProductoNombreContainingIgnoreCaseOrTipoMovimientoIdOrderByIdAsc(
            String nombreProducto,
            Integer idTipoMovimiento,
            Pageable pageable);

    Page<MovimientoEntradaSalida> findAllByOrderByIdAsc(Pageable pageable);

}
