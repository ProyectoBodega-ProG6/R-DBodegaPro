package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.esfe.AppRyDBodega_Pro.modelos.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {

    Producto findByNombre(String nombre);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByCategoria(Categoria categoria);
    List<Producto> findByProveedor(Proveedor proveedor);
    boolean existsByNombre(String nombre);
    List<Producto> findAll(Sort sort);
    Page<Producto> findAll(Pageable pageable);

}
