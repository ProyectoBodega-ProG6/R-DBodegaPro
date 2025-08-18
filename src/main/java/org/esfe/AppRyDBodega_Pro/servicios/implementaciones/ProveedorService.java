package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.modelos.Proveedor;
import org.esfe.AppRyDBodega_Pro.repositorios.IProveedorRepository;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService implements IProveedorService {

    @Autowired
    private IProveedorRepository proveedorRepository;

    @Override
    public Page<Proveedor> buscarTodosPaginados(Pageable pageable) {
        return proveedorRepository.findAll(pageable);
    }

    @Override
    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    @Override
    public Optional<Proveedor> buscarPorId(Integer id) {
        return proveedorRepository.findById(id);
    }

    @Override
    public Proveedor createOrEditOne(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public void eliminarPorId(Integer id) {
        proveedorRepository.deleteById(id);
    }

    @Override
    public Page<Proveedor> findByNombreContainingIgnoreCaseAndNombreEmpresaContainingIgnoreCaseOrderByIdAsc(String nombre, String nombreEmpresa, Pageable pageable) {
        return proveedorRepository.findByNombreContainingIgnoreCaseAndNombreEmpresaContainingIgnoreCaseOrderByIdAsc(nombre, nombreEmpresa, pageable);
    }
}
