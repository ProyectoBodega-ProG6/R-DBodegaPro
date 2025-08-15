package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.modelos.Rol;
import org.esfe.AppRyDBodega_Pro.repositorios.IRolRepository;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public class RolService implements IRolService {

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public Page<Rol> buscarTodosPaginados(Pageable pageable) {
        return rolRepository.findAll(pageable);
    }

    @Override
    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> buscarPorId(Integer id) {
        return rolRepository.findById(id);
    }

    @Override
    public Rol createOrEditOne(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void eliminarPorId(Integer id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Page<Rol> findByNombreRolContainingIgnoreCaseOrderByIdAsc(String nombreRol, Pageable pageable) {
        return rolRepository.findByNombreRolContainingIgnoreCaseOrderByIdAsc(nombreRol, pageable);
    }
}