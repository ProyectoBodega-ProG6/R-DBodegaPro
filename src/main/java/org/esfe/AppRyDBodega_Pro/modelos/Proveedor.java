package org.esfe.AppRyDBodega_Pro.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "proveedores", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 100, message = "El nombre de la empresa no debe exceder los 100 caracteres")
    @Column(name = "nombre_empresa", nullable = false, length = 100)
    private String nombreEmpresa;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no debe exceder los 20 caracteres")
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar un correo electrónico válido")
    @Size(max = 100, message = "El correo electrónico no debe exceder los 100 caracteres")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no debe exceder los 255 caracteres")
    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;


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

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Proveedor() {
    }


    public Proveedor(Integer id, String nombre, String nombreEmpresa, String telefono, String email, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.nombreEmpresa = nombreEmpresa;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }


    @Override
    public String toString() {
        return "Proveedor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nombreEmpresa='" + nombreEmpresa + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}