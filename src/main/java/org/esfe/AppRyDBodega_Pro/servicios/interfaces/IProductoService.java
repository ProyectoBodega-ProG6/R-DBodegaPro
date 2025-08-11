package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.modelos.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

public interface IProductoService {

    Page<Producto> buscarTodosPaginados(Pageable pageable);
    List<Producto>obtenerTodos();
    Optional<Producto>buscarPorId(Integer id);
    Producto createOrEditOne(Producto producto);
    void eliminarPorId (Integer id);

    Producto buscarPorNombre(String nombre);
    List<Producto> buscarPorNombreParcial(String nombre);
    List<Producto> buscarPorCategoria(Categoria categoria);
    List<Producto> buscarPorProveedor(Proveedor proveedor);
    boolean existePorNombre(String nombre);

    List<Producto> listarOrdenados(Sort sort);
    Page<Producto> listarPaginados(Pageable pageable);

}