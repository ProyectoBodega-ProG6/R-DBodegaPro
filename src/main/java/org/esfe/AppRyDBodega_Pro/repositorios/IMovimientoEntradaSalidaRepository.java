package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.MovimientoEntradaSalida;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovimientoEntradaSalidaRepository extends JpaRepository<MovimientoEntradaSalida, Integer> {
}
