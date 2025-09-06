package org.esfe.AppRyDBodega_Pro.controladores;

import org.esfe.AppRyDBodega_Pro.modelos.MovimientoEntradaSalida;
import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.modelos.TipoMovimiento;
import org.esfe.AppRyDBodega_Pro.modelos.Usuario;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IMovimientoEntradaSalidaService;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IProductoService;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.ITipoMovimientoService;
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
@RequestMapping ("/movimientos")
public class MovimientoEntradaSalidaController {
    @Autowired
    private IMovimientoEntradaSalidaService movimientoService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ITipoMovimientoService tipoMovimientoService;

    @Autowired
    private IUsuarioService usuarioService;

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
    public String create(Model model) {
        if (!model.containsAttribute("movimiento")) {
            MovimientoEntradaSalida movimiento = new MovimientoEntradaSalida();
            model.addAttribute("movimiento", movimiento);
        }
        model.addAttribute("productos", productoService.obtenerTodos());
        model.addAttribute("tiposMovimiento", tipoMovimientoService.obtenerTodos());
        model.addAttribute("usuarios", usuarioService.obtenerTodos()); // pasa los usuarios al formulario
        return "movimiento/create";
    }

    @PostMapping("/save")
    public String save(MovimientoEntradaSalida movimiento,
                       BindingResult result,
                       Model model,
                       RedirectAttributes attributes) {

        // Validar producto
        Producto producto = productoService.buscarPorId(movimiento.getProducto().getId()).orElse(null);
        if (producto == null) {
            attributes.addFlashAttribute("error", "Producto no válido");
            return "redirect:/movimientos/create";
        }
        movimiento.setProducto(producto);


        // Validar tipo de movimiento
        TipoMovimiento tipoMovimiento = tipoMovimientoService.buscarPorId(movimiento.getTipoMovimiento().getId()).orElse(null);
        if (tipoMovimiento == null) {
            attributes.addFlashAttribute("error", "Tipo de movimiento no válido");
            return "redirect:/movimientos/create";
        }
        movimiento.setTipoMovimiento(tipoMovimiento);

        // Validar que cantidad no sea nula
        if (movimiento.getCantidad() == null) {
            attributes.addFlashAttribute("error", "La cantidad no puede ser nula");
            return "redirect:/movimientos/create";
        }


// Validar precio solo si es Entrada
        if (tipoMovimiento.getTipo() != null && tipoMovimiento.getTipo() == 1) { // 1 = Entrada
            if (movimiento.getPrecio() == null || movimiento.getPrecio().doubleValue() <= 0) {
                attributes.addFlashAttribute("error", "Debe ingresar un precio válido para la entrada");
                return "redirect:/movimientos/create";
            }
        }

        // Validar usuario
        Usuario usuario = usuarioService.buscarPorId(
                movimiento.getUsuario().getId()).orElse(null);
        if (usuario == null) {
            attributes.addFlashAttribute("error", "Usuario no válido");
            return "redirect:/movimientos/create";
        }
        movimiento.setUsuario(usuario);

        if (result.hasErrors()) {
            model.addAttribute("productos", productoService.obtenerTodos());
            model.addAttribute("tiposMovimiento", tipoMovimientoService.obtenerTodos());
            model.addAttribute("usuarios", usuarioService.obtenerTodos());
            return "movimiento/create";
        }
// Intentar guardar y capturar errores de stock
        try {
            movimientoService.createOrEditOne(movimiento);
            attributes.addFlashAttribute("msg", "Registro guardado correctamente");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/movimientos/create";
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
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes attributes) {

        MovimientoEntradaSalida movimiento = movimientoService.buscarPorId(id).orElse(null);
        if (movimiento == null) {
            attributes.addFlashAttribute("error", "Movimiento no encontrado");
            return "redirect:/movimientos";
        }

        if (!model.containsAttribute("movimiento")) {
            model.addAttribute("movimiento", movimiento);
        }

        model.addAttribute("productos", productoService.obtenerTodos());
        model.addAttribute("tiposMovimiento", tipoMovimientoService.obtenerTodos());
        model.addAttribute("usuarios", usuarioService.obtenerTodos());

        return "movimiento/edit"; // vista de edición
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         MovimientoEntradaSalida movimientoForm,
                         BindingResult result,
                         Model model,
                         RedirectAttributes attributes) {

        // Cargar el movimiento existente
        MovimientoEntradaSalida movimiento = movimientoService.buscarPorId(id)
                .orElse(null);
        if (movimiento == null) {
            attributes.addFlashAttribute("error", "Movimiento no encontrado");
            return "redirect:/movimientos";
        }

        // Validar y asignar producto
        Producto producto = productoService.buscarPorId(movimientoForm.getProducto().getId())
                .orElse(null);
        if (producto == null) {
            attributes.addFlashAttribute("error", "Producto no válido");
            return "redirect:/movimientos/edit/" + id;
        }
        movimiento.setProducto(producto);

        // Validar y asignar tipo de movimiento
        TipoMovimiento tipoMovimiento = tipoMovimientoService.buscarPorId(movimientoForm.getTipoMovimiento().getId())
                .orElse(null);
        if (tipoMovimiento == null) {
            attributes.addFlashAttribute("error", "Tipo de movimiento no válido");
            return "redirect:/movimientos/edit/" + id;
        }
        movimiento.setTipoMovimiento(tipoMovimiento);

        // Validar cantidad
        if (movimientoForm.getCantidad() == null) {
            attributes.addFlashAttribute("error", "La cantidad no puede ser nula");
            return "redirect:/movimientos/edit/" + id;
        }
        movimiento.setCantidad(movimientoForm.getCantidad());

        // Validar precio si es entrada
        if (tipoMovimiento.getTipo() != null && tipoMovimiento.getTipo() == 1) {
            if (movimientoForm.getPrecio() == null || movimientoForm.getPrecio().doubleValue() <= 0) {
                attributes.addFlashAttribute("error", "Debe ingresar un precio válido para la entrada");
                return "redirect:/movimientos/edit/" + id;
            }
            movimiento.setPrecio(movimientoForm.getPrecio());
        } else {
            movimiento.setPrecio(null); // opcional: limpiar precio si es salida
        }

        // Validar y asignar usuario
        Usuario usuario = usuarioService.buscarPorId(movimientoForm.getUsuario().getId())
                .orElse(null);
        if (usuario == null) {
            attributes.addFlashAttribute("error", "Usuario no válido");
            return "redirect:/movimientos/edit/" + id;
        }
        movimiento.setUsuario(usuario);

        // Manejar errores de validación de Spring
        if (result.hasErrors()) {
            model.addAttribute("productos", productoService.obtenerTodos());
            model.addAttribute("tiposMovimiento", tipoMovimientoService.obtenerTodos());
            model.addAttribute("usuarios", usuarioService.obtenerTodos());
            return "movimiento/edit";
        }

        try {
            movimientoService.createOrEditOne(movimiento);
            attributes.addFlashAttribute("msg", "Registro actualizado correctamente");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/movimientos/edit/" + id;
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
