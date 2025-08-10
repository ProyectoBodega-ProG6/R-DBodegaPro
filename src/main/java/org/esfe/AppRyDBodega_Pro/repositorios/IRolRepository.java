package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepository extends JpaRepository<Rol, Integer> {

    Page<Rol> findAll(Pageable pageable);


    Page<Rol> findAllByOrderByNombreRolAsc(Pageable  pageable);
}