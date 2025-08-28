package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.dto.KardexDTO;
import org.esfe.AppRyDBodega_Pro.repositorios.KardexRepository;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KardexServiceImpl implements IKardexService {
    @Autowired
    private KardexRepository kardexRepository;

    @Override
    public List<KardexDTO> obtenerKardex() {
        return kardexRepository.obtenerKardex();
    }
}