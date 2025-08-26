package org.esfe.AppRyDBodega_Pro.controladores;

import jakarta.validation.Valid;
import org.esfe.AppRyDBodega_Pro.modelos.Rol;
import org.esfe.AppRyDBodega_Pro.modelos.Usuario;
import org.esfe.AppRyDBodega_Pro.servicios.implementaciones.RolService;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;


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
            // 🔑 Encriptar la contraseña antes de guardar
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            usuarioService.createOrEditOne(usuario);
            attributes.addFlashAttribute("msg", "Registro ingresado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
            return "usuario/create"; // importante para mostrar errores
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        Optional<Usuario> optionalUsuario = usuarioService.buscarPorId(id);
        if(optionalUsuario.isEmpty()) {
            attributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/usuarios";
        }

        Usuario usuario = optionalUsuario.get();

        // Evitar null en rol para que Thymeleaf no falle
        if(usuario.getRol() == null) usuario.setRol(new Rol());

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.obtenerTodos());
        return "usuario/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("usuario") Usuario usuario,
                         BindingResult result,
                         RedirectAttributes attributes,
                         Model model) {

        // Evitar validación de password en blanco
        if(usuario.getPassword() != null && usuario.getPassword().isBlank()) {
            usuario.setPassword(null);
        }

        if(result.hasErrors()) {
            model.addAttribute("roles", rolService.obtenerTodos());
            return "usuario/edit";
        }

        try {
            usuario.setId(id);

            // 🔑 Encriptar la contraseña antes de actualizar
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            // Mantener contraseña si el campo viene vacío
            if(usuario.getPassword() == null) {
                Usuario usuarioExistente = usuarioService.buscarPorId(id).orElse(null);
                if(usuarioExistente != null) {
                    usuario.setPassword(usuarioExistente.getPassword());
                }
            }

            // Asignar el objeto Rol según el ID recibido
            if(usuario.getRol() != null && usuario.getRol().getId() != null) {
                usuario.setRol(rolService.buscarPorId(usuario.getRol().getId()).orElse(null));
            }

            usuarioService.createOrEditOne(usuario);
            attributes.addFlashAttribute("msg", "Registro actualizado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al actualizar el registro.");
            return "redirect:/usuarios";
        }

        return "redirect:/usuarios";
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
        return "redirect:/usuarios";
    }
}
