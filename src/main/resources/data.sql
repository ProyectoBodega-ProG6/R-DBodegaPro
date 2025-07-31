-- BASE DE DATOS RyDBodega_pro PARA MYSQL
CREATE DATABASE IF NOT EXISTS RyDBodega_pro;
USE RyDBodega_pro;

-- Tabla: roles
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
) ENGINE = InnoDB;


-- Tabla: usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    idRol INT NOT NULL,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    telefono VARCHAR(15),
    direccion VARCHAR(255),
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    FOREIGN KEY (idRol) REFERENCES roles(id) ON DELETE RESTRICT
) ENGINE = InnoDB;

--Tabla categorías
CREATE TABLE categorias (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
descripcion VARCHAR(255)
) ENGINE=InnoDB;


--Tabla proveedores
CREATE TABLE proveedores (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
nombre_empresa VARCHAR(100) NOT NULL,
telefono VARCHAR(20) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
direccion VARCHAR(255) NOT NULL
) ENGINE=InnoDB;


--tabla productos
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio_compra DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    precio_venta DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    costo_promedio DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    stock_actual INT DEFAULT 0 NOT NULL,
    stock_minimo INT DEFAULT 0 NOT NULL,
    imagen_url VARCHAR(255),
    idCategoria INT NOT NULL,
    idProveedor INT,
    FOREIGN KEY (idCategoria) REFERENCES categorias(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (idProveedor) REFERENCES proveedores(id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;


-- Tabla: tipoMovimiento
CREATE TABLE tipoMovimiento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    editar_costo TINYINT(1) NOT NULL DEFAULT 0,
    tipo TINYINT(1) NOT NULL DEFAULT 0
) ENGINE = InnoDB;

-- Tabla: movimientosEntradaSalida
CREATE TABLE movimientosEntradaSalida (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idProducto INT NOT NULL,
    idUsuario INT NOT NULL,
    idTipoMovimiento INT NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observaciones TEXT,
    FOREIGN KEY (idProducto) REFERENCES productos(id),
    FOREIGN KEY (idUsuario) REFERENCES usuarios(id),
    FOREIGN KEY (idTipoMovimiento) REFERENCES tipoMovimiento(id)
) ENGINE = InnoDB;

-- Rol ADMIN
INSERT INTO roles (nombre_rol, descripcion)
VALUES ('ADMIN', 'Administrador del sistema');

-- Usuario
INSERT INTO usuarios (
    nombre_completo,
    username,
    password,
    idRol,
    telefono,
    direccion,
    correo_electronico
)
VALUES (
    'Marvin Barrera',
    'AdminBodega',
    '827ccb0eea8a706c4c34a16891f84e7b',
    1,
    '7563-4678',
    'esfe ágape',
    'adminbodega@gmail.com'
);
