package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {

    Page<Producto> findByNombreContainingIgnoreCaseAndCategoriaNombreContainingIgnoreCaseAndProveedorNombreContainingIgnoreCaseOrderByIdAsc(
            String nombre,
            String categoriaNombre,
            String proveedorNombre,
            Pageable pageable
    );

    Page<Producto> findAllByOrderByIdAsc(Pageable pageable);
}