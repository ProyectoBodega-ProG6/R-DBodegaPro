package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
}
