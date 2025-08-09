package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsernameIgnoreCase(String username);
    List<Usuario> findByRolNombreRol(String nombreRol);
    Page<Usuario> findByNombreCompletoContainingIgnoreCase(String nombreCompleto, Pageable pageable);
    Page<Usuario> findByRolNombreRolContainingIgnoreCase(String nombreRol, Pageable pageable);
    List<Usuario> findByRolIdOrderByNombreCompletoAsc(Integer idRol);
    Page<Usuario> findAll(Pageable pageable);

}
