package org.esfe.AppRyDBodega_Pro.servicios.implementaciones;

import org.esfe.AppRyDBodega_Pro.modelos.MovimientoEntradaSalida;
import org.esfe.AppRyDBodega_Pro.modelos.Producto;
import org.esfe.AppRyDBodega_Pro.modelos.TipoMovimiento;
import org.esfe.AppRyDBodega_Pro.repositorios.IMovimientoEntradaSalidaRepository;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IMovimientoEntradaSalidaService;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoEntradaSalidaService implements IMovimientoEntradaSalidaService {

    @Autowired
    private IMovimientoEntradaSalidaRepository movimientoEntradaSalidaRepository;

    @Autowired
    private IProductoService productoService;

    @Override
    public Page<MovimientoEntradaSalida> buscarTodosPaginados(Pageable pageable) {
        return movimientoEntradaSalidaRepository.findAll(pageable);
    }

    @Override
    public List<MovimientoEntradaSalida> obtenerTodos() {
        return movimientoEntradaSalidaRepository.findAll();
    }

    @Override
    public Optional<MovimientoEntradaSalida> buscarPorId(Integer id) {
        return movimientoEntradaSalidaRepository.findById(id);
    }

    @Override
    @Transactional
    public MovimientoEntradaSalida createOrEditOne(MovimientoEntradaSalida movimiento) {

        Producto producto = movimiento.getProducto();
        TipoMovimiento tipo = movimiento.getTipoMovimiento();
        Integer cantidad = movimiento.getCantidad();
        BigDecimal precioMovimiento = movimiento.getPrecio();

        // Validación básica: cantidad y producto
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
        if (producto == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }

        // Lógica según tipo de movimiento
        switch (tipo.getTipo()) {
            case 1: // ENTRADA
                // Recalcular costo promedio si editarCosto = true
                if (Boolean.TRUE.equals(tipo.getEditarCosto())) {
                    recalcularCostoPromedio(producto, cantidad, precioMovimiento);
                }
                producto.setStock_actual(producto.getStock_actual() + cantidad);
                // Precio del movimiento = precio ingresado
                movimiento.setPrecio(precioMovimiento);
                break;

            case 2: // SALIDA
                int stockNuevo = producto.getStock_actual() - cantidad;
                if (stockNuevo < 0) {
                    throw new IllegalArgumentException("No hay suficiente stock para realizar la salida");
                }
                producto.setStock_actual(stockNuevo);
                // Precio de salida = costo promedio actual
                movimiento.setPrecio(producto.getCosto_promedio());
                break;

            case 3: // AJUSTE ESPECIAL
                // Convertir cantidad de Integer a int para evitar NullPointerException
                if (movimiento.getCantidad() == null) {
                    throw new IllegalArgumentException("La cantidad no puede ser nula");
                }
                int cantidadInt = movimiento.getCantidad();

                int stockAnterior = producto.getStock_actual();
                int stockAjuste = stockAnterior + cantidadInt;

                if (stockAjuste < 0) {
                    throw new IllegalArgumentException("El ajuste dejaría stock negativo");
                }
                producto.setStock_actual(stockAjuste);

                if (Boolean.TRUE.equals(tipo.getEditarCosto())) {
                    if (cantidadInt > 0) {
                        // Ajuste Entrada → usa el método normal de costo promedio
                        recalcularCostoPromedioPositivoAjustes(producto, stockAnterior, cantidadInt, precioMovimiento);
                    } else {
                        // Ajuste Salida → usa el método especial para ajustes negativos
                        recalcularCostoPromedioNegativoAjustes(producto, stockAnterior, Math.abs(cantidadInt), precioMovimiento);
                    }
                    movimiento.setPrecio(precioMovimiento); // usar el precio editado por el usuario
                } else {
                    // Si no se permite editar costo → usar costo promedio actual
                    movimiento.setPrecio(producto.getCosto_promedio());
                }
                break;

            default:
                throw new IllegalArgumentException("Tipo de movimiento inválido");
        }

        // Guardar producto actualizado
        productoService.createOrEditOne(producto);

        // Guardar movimiento
        return movimientoEntradaSalidaRepository.save(movimiento);
    }

    // Para ajustes positivos (entrada) CASE 1
    private void recalcularCostoPromedio(Producto producto, int cantidad, BigDecimal precioMovimiento) {
        BigDecimal stockActual = BigDecimal.valueOf(producto.getStock_actual());
        BigDecimal costoActual = producto.getCosto_promedio();

        BigDecimal nuevoCosto = (costoActual.multiply(stockActual)
                .add(precioMovimiento.multiply(BigDecimal.valueOf(cantidad))))
                .divide(stockActual.add(BigDecimal.valueOf(cantidad)), 2, RoundingMode.HALF_UP);

        producto.setCosto_promedio(nuevoCosto);
    }


    // Para ajustes positivos (entrada) CASE 3
    private void recalcularCostoPromedioPositivoAjustes(Producto producto, int stockAnterior, int cantidad, BigDecimal precioMovimiento) {
        BigDecimal stockBD = BigDecimal.valueOf(stockAnterior);
        BigDecimal costoActual = producto.getCosto_promedio();

        BigDecimal nuevoCosto = (costoActual.multiply(stockBD)
                .add(precioMovimiento.multiply(BigDecimal.valueOf(cantidad))))
                .divide(stockBD.add(BigDecimal.valueOf(cantidad)), 2, RoundingMode.HALF_UP);

        producto.setCosto_promedio(nuevoCosto);
    }

    // Para ajustes negativos (salida) CASE 3
    private void recalcularCostoPromedioNegativoAjustes(Producto producto, int stockAnterior, int cantidad, BigDecimal precioMovimiento) {
        BigDecimal stockBD = BigDecimal.valueOf(stockAnterior);
        BigDecimal costoActual = producto.getCosto_promedio();

        BigDecimal nuevoCosto = (costoActual.multiply(stockBD)
                .add(precioMovimiento.multiply(BigDecimal.valueOf(cantidad))))
                .divide(stockBD.add(BigDecimal.valueOf(cantidad)), 2, RoundingMode.HALF_UP);

        producto.setCosto_promedio(nuevoCosto);
    }


    @Override
    public Page<MovimientoEntradaSalida> findByProductoNombreContainingIgnoreCaseAndTipoMovimientoNombreOrderByIdAsc(String nombreProducto, String nombre, Pageable pageable) {
        return movimientoEntradaSalidaRepository.findByProductoNombreContainingIgnoreCaseAndTipoMovimientoNombreOrderByIdAsc(nombreProducto, nombre, pageable);
    }

    @Override
    public List<MovimientoEntradaSalida> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin) {
        return movimientoEntradaSalidaRepository.findByFechaBetween(inicio, fin);
    }
}
