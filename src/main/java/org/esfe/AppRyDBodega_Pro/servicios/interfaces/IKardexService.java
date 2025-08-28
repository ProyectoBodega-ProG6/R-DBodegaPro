package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.dto.KardexDTO;
import java.util.List;

public interface IKardexService {
    List<KardexDTO> obtenerKardex();
}