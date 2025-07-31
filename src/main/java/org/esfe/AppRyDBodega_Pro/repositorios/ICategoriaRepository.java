package org.esfe.AppRyDBodega_Pro.repositorios;

import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {
}
