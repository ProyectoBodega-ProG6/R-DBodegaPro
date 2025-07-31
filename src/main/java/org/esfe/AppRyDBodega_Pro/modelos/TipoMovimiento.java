package org.esfe.AppRyDBodega_Pro.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tipoMovimiento")
public class TipoMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Column(name = "editar_costo", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    @NotNull(message = "El valor de editar_costo es obligatorio")
    private Boolean editarCosto;

    @Column(name = "tipo", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    @NotNull(message = "El valor de tipo es obligatorio")
    private Boolean tipo;

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

    public Boolean getEditarCosto() {
        return editarCosto;
    }

    public void setEditarCosto(Boolean editarCosto) {
        this.editarCosto = editarCosto;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public TipoMovimiento(Integer id, String nombre, Boolean editarCosto, Boolean tipo) {
        this.id = id;
        this.nombre = nombre;
        this.editarCosto = editarCosto;
        this.tipo = tipo;
    }

    public TipoMovimiento() {
    }
}
