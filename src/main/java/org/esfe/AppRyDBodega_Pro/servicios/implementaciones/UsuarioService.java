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

    // 🔹 Inyectamos el PasswordEncoder
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

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

        // 🔹 Codificamos la contraseña antes de guardar
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else if (usuario.getId() != null) {
            // mantener la contraseña actual si es edición y password está vacío
            Usuario existing = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (existing != null) {
                usuario.setPassword(existing.getPassword());
            }
        }

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
