package org.esfe.AppRyDBodega_Pro.modelos;

//import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "correo_electronico")
        })
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es requerido")
    @Size(max=100, message = "El nombre completo no puede superar los 100 caracteres")
    @Column(name="nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @NotBlank(message = "El nombre de usuario es requerido")
    @Size(max = 50, message = "El nombre de usuario no puede tener más de 50 caracteres")
    @Column(name="username", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Size(max = 15, message = "El teléfono no puede superar los 15 caracteres")
    @Pattern(regexp = "^[0-9\\-+()]*$", message = "El teléfono contiene caracteres inválidos")
    @Column(name = "telefono", length = 15)
    private String telefono;

    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    @Column(name = "direccion", length = 255)
    private String direccion;

    @NotBlank(message = "El correo electronico es requerido")
    @Email(message = "El correo electrónico no es válido")
    @Size(max = 100, message = "El correo electrónico no puede tener más de 100 caracteres")
    @Column(name = "correo_electronico", nullable = false, unique = true, length = 100)
    private String correo_electronico;

    @NotBlank(message = "La fecha es requerida")
    @CreationTimestamp
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRol", nullable = false, foreignKey = @ForeignKey(name = "fk_usuario_rol"))
    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Usuario(Integer id, String nombreCompleto, String username, String password, String telefono, String direccion,
                   String correo_electronico, LocalDateTime fechaRegistro, Rol rol) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo_electronico = correo_electronico;
        this.fechaRegistro = fechaRegistro;
        this.rol = rol;
    }

    public Usuario() {
    }
}
