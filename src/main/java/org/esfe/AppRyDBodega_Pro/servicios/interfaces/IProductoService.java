package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface IProductoService extends JpaRepository<Producto, Integer> {

    Page<Producto> buscarTodosPaginados(Pageable pageable);
    List<Producto>obtenerTodos();
    Optional<Producto>buscarPorId(Integer id);
    Producto createOrEditOne(Producto producto);
    void eliminarPorId (Integer id);
    Page<Producto> findByNombreContainingIgnoreCaseAndCategoriaNombreContainingIgnoreCaseAndProveedorNombreContainingIgnoreCaseOrderByIdAsc(String nombre, String categoriaNombre, String proveedorNombre, Pageable pageable);
}