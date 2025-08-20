package org.esfe.AppRyDBodega_Pro.controladores;

import org.esfe.AppRyDBodega_Pro.modelos.Usuario;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @RequestParam(value = "nombreCompleto", required = false) String nombreCompleto,
                        @RequestParam(value = "nombreRol", required = false) String nombreRol,
                        @RequestParam(value = "username", required = false) String username) {

        int currentPage = page.orElse(1) - 1; // PageRequest usa base 0
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Usuario> usuarios;

        // Si hay algún filtro activo
        if ((nombreCompleto != null && !nombreCompleto.isBlank()) ||
                (nombreRol != null && !nombreRol.isBlank()) ||
                (username != null && !username.isBlank())) {

            usuarios = usuarioService.findByNombreCompletoContainingIgnoreCaseOrRolNombreRolContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrderByIdAsc(
                    nombreCompleto != null ? nombreCompleto : "",
                    nombreRol != null ? nombreRol : "",
                    username != null ? username : "",
                    pageable
            );

            model.addAttribute("nombreCompleto", nombreCompleto);
            model.addAttribute("nombreRol", nombreRol);
            model.addAttribute("username", username);

        } else {
            usuarios = usuarioService.buscarTodosPaginados(pageable);
        }

        model.addAttribute("usuarios", usuarios);

        int totalPages = usuarios.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "usuario/index";
    }
}
