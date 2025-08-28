package org.esfe.AppRyDBodega_Pro.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class KardexDTO {

    // Datos del producto
    private String nombreProducto;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private BigDecimal costoPromedio;
    private Integer stockActual;
    private Integer stockMinimo;
    private String categoria;
    private String proveedor;
    private String estadoStock;

    // Datos del movimiento
    private String tipoMovimiento;
    private Integer cantidad;
    private BigDecimal precioMovimiento;
    private LocalDateTime fecha;
    private String observaciones;

    public KardexDTO() {}

    public KardexDTO(String nombreProducto, BigDecimal precioCompra, BigDecimal precioVenta,
                     BigDecimal costoPromedio, Integer stockActual, Integer stockMinimo,
                     String categoria, String proveedor, String estadoStock,
                     String tipoMovimiento, Integer cantidad, BigDecimal precioMovimiento,
                     LocalDateTime fecha, String observaciones) {
        this.nombreProducto = nombreProducto;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.costoPromedio = costoPromedio;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.estadoStock = estadoStock;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.precioMovimiento = precioMovimiento;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    // --- Getters y Setters ---
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public BigDecimal getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(BigDecimal precioCompra) { this.precioCompra = precioCompra; }

    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }

    public BigDecimal getCostoPromedio() { return costoPromedio; }
    public void setCostoPromedio(BigDecimal costoPromedio) { this.costoPromedio = costoPromedio; }

    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    public String getEstadoStock() { return estadoStock; }
    public void setEstadoStock(String estadoStock) { this.estadoStock = estadoStock; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioMovimiento() { return precioMovimiento; }
    public void setPrecioMovimiento(BigDecimal precioMovimiento) { this.precioMovimiento = precioMovimiento; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}