package org.esfe.AppRyDBodega_Pro.controladores;

import org.esfe.AppRyDBodega_Pro.modelos.MovimientoEntradaSalida;
import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.modelos.TipoMovimiento;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IMovimientoEntradaSalidaService;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IProductoService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping ("/movimientos")
public class MovimientoEntradaSalidaController {
    @Autowired
    private IMovimientoEntradaSalidaService movimientoService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ITipoMovimientoService tipoMovimientoService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @RequestParam(value = "nombreProducto", required = false) String nombreProducto,
                        @RequestParam(value = "tipoMovimiento", required = false) String tipoMovimiento) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<MovimientoEntradaSalida> movimientos;

        if ((nombreProducto != null && !nombreProducto.isEmpty()) &&
                (tipoMovimiento != null && !tipoMovimiento.isEmpty())) {
            movimientos = movimientoService.findByProductoNombreContainingIgnoreCaseAndTipoMovimientoNombreOrderByIdAsc(
                    nombreProducto, tipoMovimiento, pageable);
        } else {
            movimientos = movimientoService.buscarTodosPaginados(pageable);
        }

        model.addAttribute("movimientos", movimientos);

        int totalPages = movimientos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // Enviar listas para los combobox de filtros
        model.addAttribute("productos", productoService.obtenerTodos());
        model.addAttribute("tiposMovimiento", tipoMovimientoService.obtenerTodos());

        // Mantener los valores seleccionados en los filtros en el combobox
        model.addAttribute("nombreProducto", nombreProducto);
        model.addAttribute("tipoMovimiento", tipoMovimiento);

        return "movimiento/index";
    }

    @GetMapping("/create")
    public String create(MovimientoEntradaSalida movimiento, Model model) {

        movimiento.setProducto(new Producto());
        movimiento.setTipoMovimiento(new TipoMovimiento());

        model.addAttribute("productos", productoService.obtenerTodos());
        model.addAttribute("tiposMovimiento", tipoMovimientoService.obtenerTodos());
        return "movimiento/create";
    }

    @PostMapping("/save")
    public String save(MovimientoEntradaSalida movimiento,
                       BindingResult result,
                       Model model,
                       RedirectAttributes attributes) {

        Producto producto = productoService.buscarPorNombre(movimiento.getProducto().getNombre());
        TipoMovimiento tipoMovimiento = tipoMovimientoService.buscarPorNombre(movimiento.getTipoMovimiento().getNombre());
        movimiento.setProducto(producto);
        movimiento.setTipoMovimiento(tipoMovimiento);

        if (result.hasErrors() || producto == null || tipoMovimiento == null) {
            model.addAttribute("productos", productoService.obtenerTodos());
            model.addAttribute("tiposMovimiento", tipoMovimientoService.obtenerTodos());
            model.addAttribute("movimiento", movimiento);
            attributes.addFlashAttribute("error", "Error, verifique la información");
            return "movimiento/create";
        }

        try {
            movimientoService.createOrEditOne(movimiento);
            attributes.addFlashAttribute("msg", "Registro ingresado exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error, verifique la información");
        }
        return "redirect:/movimientos";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        MovimientoEntradaSalida movimiento = movimientoService.buscarPorId(id).orElse(null);
        model.addAttribute("movimiento", movimiento);
        return "movimiento/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        MovimientoEntradaSalida movimiento = movimientoService.buscarPorId(id).orElse(null);
        model.addAttribute("movimiento", movimiento);
        model.addAttribute("productos", productoService.obtenerTodos());
        model.addAttribute("tiposMovimiento", tipoMovimientoService.obtenerTodos());
        return "movimiento/edit";
    }

    @PostMapping("/update")
    public String update(MovimientoEntradaSalida movimiento,
                         BindingResult result,
                         Model model,
                         RedirectAttributes attributes) {

        Producto producto = productoService.buscarPorNombre(movimiento.getProducto().getNombre());
        TipoMovimiento tipoMovimiento = tipoMovimientoService.buscarPorNombre(movimiento.getTipoMovimiento().getNombre());
        movimiento.setProducto(producto);
        movimiento.setTipoMovimiento(tipoMovimiento);

        if (result.hasErrors() || producto == null || tipoMovimiento == null) {
            model.addAttribute("productos", productoService.obtenerTodos());
            model.addAttribute("tiposMovimiento", tipoMovimientoService.obtenerTodos());
            model.addAttribute("movimiento", movimiento);
            attributes.addFlashAttribute("error", "Error, verifique la información");
            return "movimiento/edit";
        }

        try {
            movimientoService.createOrEditOne(movimiento);
            attributes.addFlashAttribute("msg", "Registro actualizado exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error, verifique la información");
        }
        return "redirect:/movimientos";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        MovimientoEntradaSalida movimiento = movimientoService.buscarPorId(id).orElse(null);
        model.addAttribute("movimiento", movimiento);
        return "movimiento/delete"; // vista con pregunta: "¿Está seguro de eliminar?"
    }

    @PostMapping("/delete")
    public String delete(MovimientoEntradaSalida movimiento, RedirectAttributes attributes) {
        try {
            movimientoService.eliminarPorId(movimiento.getId());
            attributes.addFlashAttribute("msg", "Registro eliminado exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error de eliminación");
        }
        return "redirect:/movimientos";
    }

    @GetMapping("/stock")
    public String verStock(Model model) {
        List<Producto> productos = productoService.obtenerTodos();
        model.addAttribute("productos", productos);
        return "movimiento/stock";
    }
}
