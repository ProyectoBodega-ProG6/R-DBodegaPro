package org.esfe.AppRyDBodega_Pro.controladores;

import org.springframework.ui.Model;
import org.esfe.AppRyDBodega_Pro.modelos.Categoria;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @RequestParam(value = "nombre", required = false) String nombre) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Categoria> categorias;
        if (nombre != null && !nombre.isBlank()) {
            categorias = categoriaService.findByNombreContainingIgnoreCaseOrderByIdAsc(nombre, pageable);
            model.addAttribute("nombre", nombre);
        } else {
            categorias = categoriaService.buscarTodosPaginados(pageable);
        }

        model.addAttribute("categorias", categorias);

        int totalPages = categorias.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "categoria/index";
    }

    @GetMapping("/create")
    public String create(Categoria categoria) {
        return "categoria/create";
    }


    @PostMapping("/save")
    public String save(Categoria categoria,
                       BindingResult result,
                       Model model,
                       RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute(categoria);
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
            return "categoria/create";
        }

        try {
            categoriaService.createOrEditOne(categoria);
            attributes.addFlashAttribute("msg", "Registro ingresado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
        }

        return "redirect:/categorias";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Categoria categoria = categoriaService.buscarPorId(id).orElse(null);
        model.addAttribute("categoria", categoria);
        return "categoria/edit";
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         Categoria categoria,
                         BindingResult result,
                         Model model,
                         RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute(categoria);
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
            return "categoria/edit";
        }

        try {
            categoria.setId(id);
            categoriaService.createOrEditOne(categoria);
            attributes.addFlashAttribute("msg", "Registro actualizado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error: verifique la información ingresada.");
        }

        return "redirect:/categorias";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Categoria categoria = categoriaService.buscarPorId(id).orElse(null);
        model.addAttribute("categoria", categoria);
        return "categoria/details";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Categoria categoria = categoriaService.buscarPorId(id).orElse(null);
        model.addAttribute("categoria", categoria);
        return "categoria/delete";
    }

    @PostMapping("/delete")
    public String delete(Categoria categoria, RedirectAttributes attributes) {
        try {
            categoriaService.eliminarPorId(categoria.getId());
            attributes.addFlashAttribute("msg", "Registro eliminado exitosamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error de eliminación.");
        }
        return "redirect:/categorias";
    }
}
