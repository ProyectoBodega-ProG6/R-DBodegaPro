package org.esfe.AppRyDBodega_Pro.controladores;

import jakarta.validation.Valid;
import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.ICategoriaService;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IProductoService;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
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

                // Si es edición, eliminar imagen anterior
                if (producto.getId() != null && producto.getId() > 0) {
                    Producto productos = productoService.buscarPorId(producto.getId()).get();
                    if (producto != null && producto.getImagen_url() != null) {
                        Path filePathDelete = uploadPath.resolve(producto.getImagen_url());
                        Files.deleteIfExists(filePathDelete);
                    }
                }

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

    @PostMapping("/delete")
    public String delete(Producto producto, RedirectAttributes attributes) {
        Producto producto1 = productoService.buscarPorId(producto.getId()).get();
        if (producto1 != null && producto1.getImagen_url() != null) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePathDelete = uploadPath.resolve(producto1.getImagen_url());
                Files.deleteIfExists(filePathDelete);
            } catch (Exception e) {
                attributes.addFlashAttribute("error", "Error al procesar la imagen: " + e.getMessage());
                return "redirect:/productos";
            }
        }
        productoService.eliminarPorId(producto.getId());
        attributes.addFlashAttribute("msg", "Producto eliminado correctamente");
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
}
