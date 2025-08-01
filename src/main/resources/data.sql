--YA ESTAN CREADAS TODAS LAS TABLAS EN LA BASE DE DATOS PORQUE SE CREARON POR MEDIO DE @ENTITY
--YA ESTAN CREADOS EL ROL Y USUARIO DE ACCESO DEL CODIGO QUE ESTA COMENTADO, YA ESTAN EN LA BASE DE DATOS DE MYSQL

---- Rol ADMIN
--INSERT IGNORE INTO roles (nombre_rol, descripcion)
--VALUES ('Admin', 'Administrador del sistema');

-- Usuario de acceso
--INSERT IGNORE INTO usuarios (
--    nombre_completo,
--    username,
--    password,
--    telefono,
--    direccion,
--    correo_electronico,
--    id_rol
--) VALUES (
--    'Daniel Lopez',
--    'sysadmin',
--    '827ccb0eea8a706c4c34a16891f84e7b',
--    '7554-3463',
--    'San Salvador',
--    'daniel@gmail.com',
--    1
--);

--NOTA: Para crear más registros habilitar 2 configuraciones comentadas
--que funcionan para insertar datos a las tablas en la base de datos
--que estan en el archivo application.properties
