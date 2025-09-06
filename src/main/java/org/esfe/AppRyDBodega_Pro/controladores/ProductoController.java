package org.esfe.AppRyDBodega_Pro.controladores;

import jakarta.validation.Valid;
import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.ICategoriaService;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IProductoService;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IProveedorService proveedorService;


    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @RequestParam(value = "nombre", required = false) String nombre,
                        @RequestParam(value = "categoriaNombre", required = false) String categoriaNombre,
                        @RequestParam(value = "proveedorNombre", required = false) String proveedorNombre) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Producto> productos = productoService.findByNombreContainingIgnoreCaseAndCategoriaNombreContainingIgnoreCaseAndProveedorNombreContainingIgnoreCaseOrderByIdAsc(
                nombre != null ? nombre : "",
                categoriaNombre != null ? categoriaNombre : "",
                proveedorNombre != null ? proveedorNombre : "",
                pageable
        );
        model.addAttribute("productos", productos);
        model.addAttribute("nombre", nombre);
        model.addAttribute("categoriaNombre", categoriaNombre);
        model.addAttribute("proveedorNombre", proveedorNombre);

        // Listas para combobox
        model.addAttribute("categorias", categoriaService.obtenerTodos());
        model.addAttribute("proveedores", proveedorService.obtenerTodos());

        model.addAttribute("nombre", nombre);
        model.addAttribute("categoriaNombre", categoriaNombre);
        model.addAttribute("proveedorNombre", proveedorNombre);

        int totalPages = productos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "producto/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.obtenerTodos());
        model.addAttribute("proveedores", proveedorService.obtenerTodos());
        return "producto/create";
    }

    @PostMapping("/save")
    public String save(@Valid Producto producto,
                       BindingResult result,
                       @RequestParam("fileImagen") MultipartFile fileImagen,
                       Model model,
                       RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("producto", producto);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "producto/create";
        }

        if (fileImagen != null && !fileImagen.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = fileImagen.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(fileImagen.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                producto.setImagen_url(fileName);

            } catch (Exception e) {
                attributes.addFlashAttribute("error", "Error al procesar la imagen: " + e.getMessage());
                return "redirect:/productos";
            }
        }

        productoService.createOrEditOne(producto);
        attributes.addFlashAttribute("msg", "Producto creado/actualizado correctamente");
        return "redirect:/productos";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id).get();
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.obtenerTodos());
        model.addAttribute("proveedores", proveedorService.obtenerTodos());
        return "producto/edit";
    }

    @PostMapping("/update")
    public String update(@Valid Producto producto,
                         BindingResult result,
                         @RequestParam("fileImagen") MultipartFile fileImagen,
                         Model model,
                         RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("producto", producto);
            return "producto/edit";
        }

        // Verificar si existe el producto
        Optional<Producto> productoExistente = productoService.buscarPorId(producto.getId());
        if (!productoExistente.isPresent()) {
            attributes.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/productos";
        }

        Producto prod = productoExistente.get();

        // Actualizar campos
        prod.setNombre(producto.getNombre());
        prod.setDescripcion(producto.getDescripcion());
        prod.setCategoria(producto.getCategoria());
        prod.setProveedor(producto.getProveedor());
        prod.setPrecio_compra(producto.getPrecio_compra());
        prod.setPrecio_venta(producto.getPrecio_venta());
        prod.setCosto_promedio(producto.getCosto_promedio());
        prod.setStock_actual(producto.getStock_actual());
        prod.setStock_minimo(producto.getStock_minimo());

        // Subida de imagen nueva (si hay)
        if (fileImagen != null && !fileImagen.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

                // Eliminar imagen anterior
                if (prod.getImagen_url() != null) {
                    Path filePathDelete = uploadPath.resolve(prod.getImagen_url());
                    Files.deleteIfExists(filePathDelete);
                }

                String fileName = fileImagen.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(fileImagen.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                prod.setImagen_url(fileName);

            } catch (Exception e) {
                attributes.addFlashAttribute("error", "Error al procesar la imagen: " + e.getMessage());
                return "redirect:/productos";
            }
        }

        productoService.createOrEditOne(prod); // actualiza producto existente
        attributes.addFlashAttribute("msg", "Producto actualizado correctamente");
        return "redirect:/productos";
    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id).get();
        model.addAttribute("producto", producto);
        return "producto/details";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id).get();
        model.addAttribute("producto", producto);
        return "producto/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        Optional<Producto> productoOpt = productoService.buscarPorId(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();

            try {
                // Primero eliminar en la BD
                productoService.eliminarPorId(id);

                // Si se eliminó en BD, entonces eliminar imagen si existe
                if (producto.getImagen_url() != null) {
                    try {
                        Path uploadPath = Paths.get(UPLOAD_DIR);
                        Path filePathDelete = uploadPath.resolve(producto.getImagen_url());
                        Files.deleteIfExists(filePathDelete);
                    } catch (IOException e) {
                        attributes.addFlashAttribute("error", "El producto se eliminó, pero ocurrió un error al borrar la imagen: " + e.getMessage());
                        return "redirect:/productos";
                    }
                }

                attributes.addFlashAttribute("msg", "Producto eliminado correctamente");

            } catch (DataIntegrityViolationException e) {
                // Si no se elimina en BD, la imagen se conserva
                attributes.addFlashAttribute("error", "No se puede eliminar el producto porque tiene movimientos registrados");
            }

        } else {
            attributes.addFlashAttribute("error", "Producto no encontrado");
        }

        return "redirect:/productos";
    }





    @GetMapping("/imagen/{id}")
    @ResponseBody
    public byte[] mostrarImagen(@PathVariable Integer id) {
        try {
            Producto producto = productoService.buscarPorId(id).get();
            String fileName = producto.getImagen_url();

            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            if (!Files.exists(filePath)) {
                throw new IOException("Archivo no encontrado: " + fileName);
            }

            return Files.readAllBytes(filePath);

        } catch (IOException e) {
            return new byte[0];
        }
    }

    @GetMapping("/catalogo")
    public String catalogo(Model model) {
        List<Producto> productos = productoService.obtenerTodos(); // O usa paginación si prefieres
        model.addAttribute("productos", productos);
        return "producto/catalogo";
    }

    @GetMapping("/catalogo/detalle/{id}")
    public String detalleCatalogo(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id: " + id));
        model.addAttribute("producto", producto);
        return "producto/catalogo-detalle";
    }



}
