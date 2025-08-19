package org.esfe.AppRyDBodega_Pro.controladores;

import org.esfe.AppRyDBodega_Pro.modelos.Proveedor;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IProveedorService;
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
@RequestMapping ("/proveedores")
public class ProveedorController {

    @Autowired
    IProveedorService proveedorService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @RequestParam(value = "nombre", required = false) String nombre,
                        @RequestParam(value = "nombreEmpresa", required = false) String nombreEmpresa) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Proveedor> proveedores;

        if ((nombre != null && !nombre.isBlank()) || (nombreEmpresa != null && !nombreEmpresa.isBlank())) {
            proveedores = proveedorService.findByNombreContainingIgnoreCaseAndNombreEmpresaContainingIgnoreCaseOrderByIdAsc(
                    nombre != null ? nombre : "",
                    nombreEmpresa != null ? nombreEmpresa : "",
                    pageable
            );

            model.addAttribute("nombre", nombre);
            model.addAttribute("nombreEmpresa", nombreEmpresa);
        } else {
            proveedores = proveedorService.buscarTodosPaginados(pageable);
        }

        model.addAttribute("proveedores", proveedores);

        int totalPages = proveedores.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "proveedor/index";
    }

    @GetMapping("/create")
    public String create(Proveedor proveedor) {
        return "proveedor/create";
    }

    @PostMapping("/save")
    public String save(Proveedor proveedor,
                       BindingResult result,
                       Model model,
                       RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute(proveedor);
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
            return "proveedor/create";
        }

        try {
            proveedorService.createOrEditOne(proveedor);
            attributes.addFlashAttribute("msg", "Registro ingresado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
        }

        return "redirect:/proveedores";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Proveedor proveedor = proveedorService.buscarPorId(id).orElse(null);
        model.addAttribute("proveedor", proveedor);
        return "proveedor/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         Proveedor proveedor,
                         BindingResult result,
                         Model model,
                         RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute(proveedor);
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
            return "categoria/edit";
        }

        try {
            proveedor.setId(id);
            proveedorService.createOrEditOne(proveedor);
            attributes.addFlashAttribute("msg", "Registro actualizado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
        }

        return "redirect:/prooveedores";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Proveedor proveedor = proveedorService.buscarPorId(id).orElse(null);
        model.addAttribute("proveedor", proveedor);
        return "proveedor/details";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Proveedor proveedor = proveedorService.buscarPorId(id).orElse(null);
        model.addAttribute("proveedor", proveedor);
        return "proveedor/delete";
    }

    @PostMapping("/delete")
    public String delete(Proveedor proveedor, RedirectAttributes attributes) {
        try {
            proveedorService.eliminarPorId(proveedor.getId());
            attributes.addFlashAttribute("msg", "Registro eliminado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error de eliminación.");
        }
        return "redirect:/proveedores";
    }


}
