package org.esfe.AppRyDBodega_Pro.servicios.interfaces;

import org.esfe.AppRyDBodega_Pro.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Page<Usuario> buscarTodosPaginados(Pageable pageable);

    List<Usuario> obtenerTodos();

    Optional<Usuario> buscarPorId(Integer id);

    Usuario createOrEditOne(Usuario usuario);

    void eliminarPorId(Integer id);

    Page<Usuario> findByNombreCompletoContainingIgnoreCaseOrRolNombreRolContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrderByIdAsc(
            String nombreCompleto,
            String nombreRol,
            String username,
            Pageable pageable
    );

}
