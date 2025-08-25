package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    Page<Usuario> findByNombreCompletoContainingIgnoreCaseAndRolNombreRolContainingIgnoreCaseAndUsernameContainingIgnoreCaseOrderByIdAsc(
            String nombreCompleto,
            String nombreRol,
            String username,
            Pageable pageable);

}
