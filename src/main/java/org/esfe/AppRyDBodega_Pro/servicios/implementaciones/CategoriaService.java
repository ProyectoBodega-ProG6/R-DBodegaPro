package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.ICategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class CategoriaService implements ICategoriaService {
    @Override
    public Page<Categoria> buscarTodosPaginados(Pageable pageable) {
        return null;
    }

    @Override
    public List<Categoria> obtenerTodos() {
        return List.of();
    }

    @Override
    public Optional<Categoria> buscarPorId(Integer id) {
        return Optional.empty();
    }

    @Override
    public Categoria createOrEditOne(Categoria categoria) {
        return null;
    }

    @Override
    public void eliminarPorId(Integer id) {

    }

    @Override
    public Page<Categoria> findByNombreContainingIgnoreCaseOrderByIdAsc(String nombre, Pageable pageable) {
        return null;
    }
}
