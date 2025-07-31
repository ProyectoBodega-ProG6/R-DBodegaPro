package org.esfe.AppRyDBodega_Pro.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

//@Entity
@Table(name = "productos")

public class Producto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 255)
    @Column(length = 255)
    private String descripcion;

    @NotNull(message = "El precio de compra es obligatorio")
    @DecimalMin("0.00")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "precio_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio_compra;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin("0.00")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio_venta;

    @NotNull(message = "El costo promedio es obligatorio")
    @DecimalMin("0.00")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "costo_promedio", nullable = false, precision = 10, scale = 2)
    private BigDecimal costo_promedio;

    @NotNull(message = "El stock actual es obligatorio")
    @Min(0)
    @Column(name = "stock_actual", nullable = false)
    private Integer stock_actual;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(0)
    @Column(name = "stock_minimo", nullable = false)
    private Integer stock_minimo;

    @Size(max = 255)
    @Column(name = "imagen_url", length = 255)
    private String imagen_url;

    @NotNull(message = "Debe seleccionar una categoría")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategoria", nullable = false,
            foreignKey = @ForeignKey(name = "fk_productos_categoria"))
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProveedor",
            foreignKey = @ForeignKey(name = "fk_productos_proveedor"))
    private Proveedor proveedor;


    public Producto() {
    }

    public Producto(Integer id, String nombre, String descripcion, BigDecimal precio_compra, BigDecimal precio_venta,
                    BigDecimal costo_promedio, Integer stock_actual, Integer stock_minimo,
                    String imagen_url, Categoria categoria, Proveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio_compra = precio_compra;
        this.precio_venta = precio_venta;
        this.costo_promedio = costo_promedio;
        this.stock_actual = stock_actual;
        this.stock_minimo = stock_minimo;
        this.imagen_url = imagen_url;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(BigDecimal precio_compra) {
        this.precio_compra = precio_compra;
    }

    public BigDecimal getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(BigDecimal precio_venta) {
        this.precio_venta = precio_venta;
    }

    public BigDecimal getCosto_promedio() {
        return costo_promedio;
    }

    public void setCosto_promedio(BigDecimal costo_promedio) {
        this.costo_promedio = costo_promedio;
    }

    public Integer getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(Integer stock_actual) {
        this.stock_actual = stock_actual;
    }

    public Integer getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(Integer stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio_compra=" + precio_compra +
                ", precio_venta=" + precio_venta +
                ", costo_promedio=" + costo_promedio +
                ", stock_actual=" + stock_actual +
                ", stock_minimo=" + stock_minimo +
                ", imagen_url='" + imagen_url + '\'' +
                ", categoria=" + (categoria != null ? categoria.getId() : null) +
                ", proveedor=" + (proveedor != null ? proveedor.getId() : null) +
                '}';
    }


}