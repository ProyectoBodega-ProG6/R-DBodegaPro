package org.esfe.AppRyDBodega_Pro.controladores;

import jakarta.validation.Valid;
import org.esfe.AppRyDBodega_Pro.modelos.TipoMovimiento;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.ITipoMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tipoMovimientos")
public class TipoMovmientoController {
    @Autowired
    private ITipoMovimientoService tipoMovimientoService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @RequestParam(value = "nombre", required = false) String nombre) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<TipoMovimiento> tipoMovimientos;
        if (nombre != null && !nombre.isEmpty()) {
            tipoMovimientos = tipoMovimientoService.findByNombreContainingIgnoreCaseOrderByIdAsc(nombre, pageable);
            model.addAttribute("nombre", nombre);
        } else {
            tipoMovimientos = tipoMovimientoService.buscarTodosPaginados(pageable);
        }

        model.addAttribute("tipoMovimientos", tipoMovimientos);

        int totalPages = tipoMovimientos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "tipoMovimiento/index";
    }

    @GetMapping("/create")
    public String create(Model model) {

        if (!model.containsAttribute("tipoMovimiento")) {
            TipoMovimiento tipoMovimiento = new TipoMovimiento();
            tipoMovimiento.setTipo(2); // Inicializa tipo Salida para mostrar alerta
            model.addAttribute("tipoMovimiento", tipoMovimiento);
        }

        return "tipoMovimiento/create";
    }

    @PostMapping("/save")
    public String save(@Valid TipoMovimiento tipoMovimiento,
                       BindingResult result,
                       RedirectAttributes attributes) {

        // Validación: si es Salida, forzar editarCosto = false
        if (tipoMovimiento.getTipo() != null && tipoMovimiento.getTipo() == 2) {
            if (Boolean.TRUE.equals(tipoMovimiento.getEditarCosto())) {
                tipoMovimiento.setEditarCosto(false);
                attributes.addFlashAttribute("alerta", "⚠ No se puede editar el costo en movimientos de SALIDA. Se cambió automáticamente a NO.");
            }
        }

        if (result.hasErrors()) {
            attributes.addFlashAttribute("error", "⚠ Error: verifique la información.");
            return "redirect:/tipoMovimientos/create"; // redirige al formulario
        }

        tipoMovimientoService.createOrEditOne(tipoMovimiento);
        attributes.addFlashAttribute("msg", "✅ Registro ingresado exitosamente");

        return "redirect:/tipoMovimientos"; // redirige al index
    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        TipoMovimiento tipoMovimiento = tipoMovimientoService.buscarPorId(id).orElse(null);
        model.addAttribute("tipoMovimiento", tipoMovimiento);
        return "tipoMovimiento/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        TipoMovimiento tipoMovimiento = tipoMovimientoService.buscarPorId(id).orElse(null);
        model.addAttribute("tipoMovimiento", tipoMovimiento);
        return "tipoMovimiento/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid TipoMovimiento tipoMovimiento,
                         BindingResult result,
                         Model model,
                         RedirectAttributes attributes) {

        // Validación: si es Salida, forzar editarCosto = false
        if (tipoMovimiento.getTipo() != null && tipoMovimiento.getTipo() == 2) {
            tipoMovimiento.setEditarCosto(false);
            // Mensaje para mostrar en la misma página
            model.addAttribute("alerta", "⚠ No se puede editar el costo en movimientos de SALIDA seleccione el tipo AJUSTE ESPECIAL para modificar el costo");
        }


        if (result.hasErrors()) {
            model.addAttribute("tipoMovimiento", tipoMovimiento);
            attributes.addFlashAttribute("error", "⚠ Error: verifique la información.");
            return "tipoMovimiento/edit";
        }

        tipoMovimiento.setId(id);
        tipoMovimientoService.createOrEditOne(tipoMovimiento);
        attributes.addFlashAttribute("msg", "✅ Registro actualizado exitosamente");
        return "redirect:/tipoMovimientos";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        TipoMovimiento tipoMovimiento = tipoMovimientoService.buscarPorId(id).orElse(null);
        model.addAttribute("tipoMovimiento", tipoMovimiento);
        return "tipoMovimiento/delete";
    }

    @PostMapping("/delete")
    public String delete(TipoMovimiento tipoMovimiento, RedirectAttributes attributes) {
        try {
            tipoMovimientoService.eliminarPorId(tipoMovimiento.getId());
            attributes.addFlashAttribute("msg", "✅ Registro eliminado exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "⚠ Error de eliminación");
        }
        return "redirect:/tipoMovimientos";
    }
}
