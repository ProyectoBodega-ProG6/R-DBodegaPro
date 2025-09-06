package org.esfe.AppRyDBodega_Pro.controladores;

import jakarta.validation.Valid;
import org.esfe.AppRyDBodega_Pro.modelos.Rol;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IRolService;
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
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private IRolService rolService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @RequestParam(value = "nombre", required = false) String nombre) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Rol> rol;
        if (nombre != null && !nombre.isEmpty()) {
            rol = rolService.findByNombreRolContainingIgnoreCaseOrderByIdAsc(nombre, pageable);
            model.addAttribute("nombre", nombre);
        } else {
            rol = rolService.buscarTodosPaginados(pageable);
        }

        model.addAttribute("roles", rol);

        int totalPages = rol.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        long totalRoles = rolService.count();
        model.addAttribute("canCreate", totalRoles < 2);

        model.addAttribute("disableDelete", true); // Siempre true si no se permite eliminar

        return "rol/index";
    }

    @GetMapping("/create")
    public String create(Rol rol, Model model,RedirectAttributes attributes) {
        long totalRoles = rolService.count();
        if (totalRoles >= 3) {
            attributes.addFlashAttribute("error", "No se pueden registrar más de 3 roles.");
            return "redirect:/roles";
        }
        model.addAttribute("canCreate", true);
        return "rol/create";
    }

    @PostMapping("/save")
    public String save(@Valid Rol rol,
                       BindingResult result,
                       Model model,
                       RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute(rol);
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
            return "rol/create";
        }

        long totalRoles = rolService.count();
        if (totalRoles >= 3) {
            attributes.addFlashAttribute("error", "No se pueden registrar más de 3 roles.");
            return "redirect:/roles";
        }

        try {
            rolService.createOrEditOne(rol);
            attributes.addFlashAttribute("msg", "Registro ingresado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
        }

        return "redirect:/roles";
    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Rol rol = rolService.buscarPorId(id).orElse(null);
        model.addAttribute("rol", rol);
        return "rol/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Rol rol = rolService.buscarPorId(id).orElse(null);
        model.addAttribute("rol", rol);
        return "rol/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid Rol rol,
                         BindingResult result,
                         Model model,
                         RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("rol", rol);
            attributes.addFlashAttribute("error", "⚠ Error: verifique la información.");
            return "rol/edit";
        }

        rol.setId(id);
        rolService.createOrEditOne(rol);
        attributes.addFlashAttribute("msg", "✅ Registro actualizado exitosamente");
        return "redirect:/roles";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Rol rol = rolService.buscarPorId(id).orElse(null);
        model.addAttribute("rol", rol);
        return "rol/delete";
    }

    @PostMapping("/delete")
    public String delete(Rol rol, RedirectAttributes attributes) {
        try {
            rolService.eliminarPorId(rol.getId());
            attributes.addFlashAttribute("msg", "✅ Registro eliminado exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "⚠ Error de eliminación");
        }
        return "redirect:/roles";
    }
}