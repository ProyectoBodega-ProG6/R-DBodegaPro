package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.modelos.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProveedorService {

    Page<Proveedor> buscarTodosPaginados(Pageable pageable);

    List<Proveedor> obtenerTodos();

    Optional<Proveedor> buscarPorId(Integer id);

    Proveedor createOrEditOne(Proveedor proveedor);

    void eliminarPorId(Integer id);

    Page<Proveedor> findAllByOrderByNombreAsc(Pageable pageable);

    Page<Proveedor> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre, Pageable pageable);

    Page<Proveedor> findByNombreEmpresaContainingIgnoreCaseOrderByNombreEmpresaAsc(String nombreEmpresa, Pageable pageable);


}
