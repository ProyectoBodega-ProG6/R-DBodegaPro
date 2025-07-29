package org.esfe.AppRyDBodega_Pro.modelos;

//import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

//@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es requerido")
    private String nombre_completo;

    @NotBlank(message = "El nombre de usuario es requerido")
    private String username;

    @NotBlank(message = "La contraseña es requerida")
    private String password;

    @NotBlank(message = "El telefono es requerido")
    private String telefono;

    @NotBlank(message = "El nombre de usuario es requerido")
    private String direccion;

    @NotBlank(message = "El nombre de usuario es requerido")
    private String correo_electronico;
}
