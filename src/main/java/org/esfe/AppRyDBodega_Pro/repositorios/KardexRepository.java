package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.dto.KardexDTO;
import org.esfe.AppRyDBodega_Pro.modelos.MovimientoEntradaSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface KardexRepository extends JpaRepository<MovimientoEntradaSalida, Integer> {
    @Query(value = "SELECT new org.esfe.AppRyDBodega_Pro.dto.KardexDTO(" +
            "p.nombre, p.precio_compra, p.precio_venta, p.costo_promedio, " +
            "p.stock_actual, p.stock_minimo, c.nombre, pr.nombre, " +
            "CASE " +
            "WHEN p.stock_actual <= 0 THEN 'AGOTADO' " +
            "WHEN p.stock_actual <= p.stock_minimo THEN 'TERMINANDO' " +
            "ELSE 'BUENO' END, " +
            "m.tipoMovimiento.nombre, m.cantidad, m.precio, m.fecha, m.observaciones) " +
            "FROM MovimientoEntradaSalida m " +
            "JOIN m.producto p " +
            "JOIN p.categoria c " +
            "JOIN p.proveedor pr " +
            "ORDER BY m.fecha ASC")
    List<KardexDTO> obtenerKardex();
}