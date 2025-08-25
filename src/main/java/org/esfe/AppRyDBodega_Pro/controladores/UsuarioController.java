package org.esfe.AppRyDBodega_Pro.controladores;

import jakarta.validation.Valid;
import org.esfe.AppRyDBodega_Pro.modelos.Usuario;
import org.esfe.AppRyDBodega_Pro.servicios.implementaciones.RolService;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    RolService rolService;

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

            usuarios = usuarioService.findByNombreCompletoContainingIgnoreCaseAndRolNombreRolContainingIgnoreCaseAndUsernameContainingIgnoreCaseOrderByIdAsc(
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

        model.addAttribute("roles", rolService.buscarTodosPaginados(pageable));

        return "usuario/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.obtenerTodos());
        return "usuario/create";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Usuario usuario,
                       BindingResult result,
                       RedirectAttributes attributes,
                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", rolService.obtenerTodos());
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
            return "usuario/create";
        }

        try {
            usuarioService.createOrEditOne(usuario);
            attributes.addFlashAttribute("msg", "Registro ingresado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
        }
        usuarioService.createOrEditOne(usuario);
        return "redirect:/usuario/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Usuario usuario  = usuarioService.buscarPorId(id).orElse(null);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.obtenerTodos());
        return "usuario/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid Usuario usuario,
                         BindingResult result,
                         RedirectAttributes attributes,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", rolService.obtenerTodos());
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
            return "usuario/edit";
        }

        try {
            usuario.setId(id);
            usuarioService.createOrEditOne(usuario);
            attributes.addFlashAttribute("msg", "Registro actualizado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
        }

        usuario.setId(id);
        usuarioService.createOrEditOne(usuario);
        return "redirect:/usuario/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("usuarios", usuarioService.obtenerTodos());
        return "usuario/list";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id).orElse(null);
        model.addAttribute("usuario", usuario);
        return "usuario/details";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id).orElse(null);
        model.addAttribute("usuario", usuario);
        return "usuario/delete";
    }

    @PostMapping("/delete")
    public String delete(Usuario usuario, RedirectAttributes attributes) {
        try {
            usuarioService.eliminarPorId(usuario.getId());
            attributes.addFlashAttribute("msg", "Registro eliminado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error de eliminación.");
        }
        return "redirect:/usuarios/list";
    }
}
