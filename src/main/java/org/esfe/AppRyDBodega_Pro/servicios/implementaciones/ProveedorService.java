package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.modelos.Proveedor;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IProveedorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class ProveedorService implements IProveedorService {
    @Override
    public Page<Proveedor> buscarTodosPaginados(Pageable pageable) {
        return null;
    }

    @Override
    public List<Proveedor> obtenerTodos() {
        return List.of();
    }

    @Override
    public Optional<Proveedor> buscarPorId(Integer id) {
        return Optional.empty();
    }

    @Override
    public Proveedor createOrEditOne(Proveedor proveedor) {
        return null;
    }

    @Override
    public void eliminarPorId(Integer id) {

    }

    @Override
    public Page<Proveedor> findByNombreContainingIgnoreCaseAndNombreEmpresaContainingIgnoreCaseOrderByIdAsc(String nombre, String nombreEmpresa, Pageable pageable) {
        return null;
    }
}
