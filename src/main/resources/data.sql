--YA ESTAN CREADAS TODAS LAS TABLAS EN LA BASE DE DATOS PORQUE SE CREARON POR MEDIO DE @ENTITY
--YA ESTAN CREADOS EL ROL Y USUARIO DE ACCESO DEL CODIGO QUE ESTA COMENTADO, YA ESTAN EN LA BASE DE DATOS DE MYSQL

use RyDBodega_Pro;

---- Rol ADMIN
INSERT IGNORE INTO roles (nombre_rol, descripcion)
VALUES ('Administrador', 'Administrador del sistema');

-- Usuario de acceso
INSERT IGNORE INTO usuarios (
    nombre_completo,
    correo_electronico,
    direccion,
    telefono,
    username,
    password,
    id_rol,
    status
) VALUES (
    'Andre ',
    'andrea@gmail.com',
    'San Salvador',
    '7765-0000',
    'AdminR',
    '$2b$12$80B/unZoYYSez3kW8BURIuskUvkeTOfR80B7DE7rV5wopB0o2Tefi',
    1,
    1
);



--NOTA: Para crear más registros habilitar 2 configuraciones comentadas
--que funcionan para insertar datos a las tablas en la base de datos
--que estan en el archivo application.properties
