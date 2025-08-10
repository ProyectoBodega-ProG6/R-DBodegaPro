package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProveedorRepository extends JpaRepository<Proveedor, Integer> {
    Page<Proveedor> findAllByOrderByNombreAsc(Pageable pageable);
    Page<Proveedor> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre, Pageable pageable);
    Page<Proveedor> findByNombreEmpresaContainingIgnoreCaseOrderByNombreEmpresaAsc(String nombreEmpresa, Pageable pageable);

}
