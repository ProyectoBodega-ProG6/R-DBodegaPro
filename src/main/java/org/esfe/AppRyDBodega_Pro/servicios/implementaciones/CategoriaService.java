package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.esfe.AppRyDBodega_Pro.repositorios.ICategoriaRepository;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Override
    public Page<Categoria> buscarTodosPaginados(Pageable pageable) {

        return categoriaRepository.findAll(pageable);
    }

    @Override
    public List<Categoria> obtenerTodos() {

        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> buscarPorId(Integer id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Categoria createOrEditOne(Categoria categoria) {

        return categoriaRepository.save(categoria);
    }

    @Override
    public void eliminarPorId(Integer id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public Page<Categoria> findByNombreContainingIgnoreCaseOrderByIdAsc(String nombre, Pageable pageable) {
        return categoriaRepository.findByNombreContainingIgnoreCaseOrderByIdAsc(nombre, pageable);
    }
}
