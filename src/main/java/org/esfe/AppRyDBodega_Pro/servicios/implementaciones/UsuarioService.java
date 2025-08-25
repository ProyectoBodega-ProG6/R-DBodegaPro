package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.modelos.Usuario;
import org.esfe.AppRyDBodega_Pro.repositorios.IUsuarioRepository;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> buscarTodosPaginados(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario createOrEditOne(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarPorId(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Page<Usuario> findByNombreCompletoContainingIgnoreCaseOrRolNombreRolContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrderByIdAsc(String nombreCompleto, String nombreRol, String username, Pageable pageable) {
        return usuarioRepository.findByNombreCompletoContainingIgnoreCaseOrRolNombreRolContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrderByIdAsc(nombreCompleto, nombreRol, username, pageable);
    }
}
