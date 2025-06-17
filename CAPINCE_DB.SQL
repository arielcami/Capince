DROP DATABASE IF EXISTS capince_db_1;

CREATE DATABASE capince_db_1;

USE capince_db_1;

/* Tablas utility sin FK */
CREATE TABLE distrito (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(35) NOT NULL,
    estado BIT(1) DEFAULT b'1',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE tipo_documento (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(25) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE sexo (
    id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE rol (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE tipo_producto (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO distrito (nombre, estado) VALUES 
('Surco', b'1'),('Chorrillos', b'1'),('Ancón', b'1'),('Ate', b'1'),('Barranco', b'1'),('Breña', b'1'),('Carabayllo', b'1'),('Cercado de Lima', b'1'),('Comas', b'1'),
('El Agustino', b'1'),('Independencia', b'1'),('Jesús María', b'1'),('La Molina', b'1'),('La Victoria', b'1'),('Lince', b'1'),('Los Olivos', b'1'),('Lurigancho', b'1'),
('Lurín', b'1'),('Magdalena del Mar', b'1'),('Miraflores', b'1'),('Pachacámac', b'1'),('Pucusana', b'1'),('Pueblo Libre', b'1'),('Puente Piedra', b'1'),('Punta Hermosa', b'1'),
('Punta Negra', b'1'),('Rímac', b'1'),('San Bartolo', b'1'),('San Borja', b'1'),('San Isidro', b'1'),('San Juan de Lurigancho', b'1'),('San Juan de Miraflores', b'1'),('San Luis', b'1'),
('San Martín de Porres', b'1'),('San Miguel', b'1'),('Santa Anita', b'1'),('Santa María del Mar', b'1'),('Santa Rosa', b'1'),('Santiago de Surco', b'1'),('Villa El Salvador', b'1'),
('Villa María del Triunfo', b'1');

INSERT INTO tipo_documento (nombre) VALUES ('DNI'),('Carnet de Extranjería'),('Pasaporte'),('Partida de Nacimiento');

INSERT INTO sexo (nombre) VALUES ('Masculino'),('Femenino'),('No especificado');

INSERT INTO rol (nombre) VALUES ('ADMIN'),('GERENTE'),('MESERO'),('DELIVERY'),('RECEPCION');

INSERT INTO tipo_producto (nombre) VALUES ('Entrada'),('Segundo'),('Bebida'),('Postre'),('Guarnición');

/* Tablas con FK */
CREATE TABLE empleado (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellido_paterno VARCHAR(30) NOT NULL,
    apellido_materno VARCHAR(30) NOT NULL,
    telefono VARCHAR(19) NOT NULL,
    tipo_documento INT UNSIGNED NOT NULL,
    documento VARCHAR(12) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    distrito INT UNSIGNED NOT NULL,
    clave VARCHAR(80) NOT NULL,
    rol INT UNSIGNED NOT NULL,
    sexo TINYINT UNSIGNED NOT NULL,
    estado BIT(1) DEFAULT b'1',
    creado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY rol (rol),
    KEY sexo (sexo),
    KEY distrito (distrito),
    KEY tipo_documento (tipo_documento),
    CONSTRAINT empleado_ibfk_1 FOREIGN KEY (rol) REFERENCES rol (id),
    CONSTRAINT empleado_ibfk_2 FOREIGN KEY (sexo) REFERENCES sexo (id),
    CONSTRAINT empleado_ibfk_3 FOREIGN KEY (distrito) REFERENCES distrito (id),
    CONSTRAINT empleado_ibfk_4 FOREIGN KEY (tipo_documento) REFERENCES tipo_documento (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE delivery (
    id INT UNSIGNED NOT NULL,
    unidad VARCHAR(25) NOT NULL,
    placa VARCHAR(8) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY placa (placa),
    CONSTRAINT fk_delivery_empleado FOREIGN KEY (id) REFERENCES empleado (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE cliente (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellido_paterno VARCHAR(30) NOT NULL,
    apellido_materno VARCHAR(30) NOT NULL,
    telefono VARCHAR(16) NOT NULL,
    tipo_documento INT UNSIGNED NOT NULL,
    documento VARCHAR(12) NOT NULL,
    direccion VARCHAR(80) NOT NULL,
    distrito INT UNSIGNED NOT NULL,
    sexo TINYINT UNSIGNED NOT NULL,
    estado BIT(1) DEFAULT b'1',
    creado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY documento (documento),
    KEY distrito (distrito),
    KEY tipo_documento (tipo_documento),
    KEY sexo (sexo),
    CONSTRAINT cliente_ibfk_1 FOREIGN KEY (distrito) REFERENCES distrito (id),
    CONSTRAINT cliente_ibfk_2 FOREIGN KEY (tipo_documento) REFERENCES tipo_documento (id),
    CONSTRAINT cliente_ibfk_3 FOREIGN KEY (sexo) REFERENCES sexo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE producto (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    tipo_producto INT UNSIGNED NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock SMALLINT UNSIGNED NOT NULL,
    estado BIT(1) DEFAULT b'1',
    creado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY nombre (nombre),
    KEY tipo_producto (tipo_producto),
    CONSTRAINT producto_ibfk_1 FOREIGN KEY (tipo_producto) REFERENCES tipo_producto (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE pedido (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    cliente_id INT UNSIGNED NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    empleado_id INT UNSIGNED DEFAULT NULL,
    estado_pedido TINYINT UNSIGNED NOT NULL,
    direccion_entrega VARCHAR(200) DEFAULT NULL,
    fecha_pedido TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    creado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    delivery_id INT UNSIGNED DEFAULT NULL,
    PRIMARY KEY (id),
    KEY cliente_id (cliente_id),
    KEY empleado_id (empleado_id),
    KEY fk_pedidos_delivery (delivery_id),
    CONSTRAINT fk_pedidos_delivery FOREIGN KEY (delivery_id) REFERENCES delivery (id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT pedidos_ibfk_1 FOREIGN KEY (cliente_id) REFERENCES cliente (id),
    CONSTRAINT pedidos_ibfk_2 FOREIGN KEY (empleado_id) REFERENCES empleado (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE detalle_pedido (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    pedido_id INT UNSIGNED NOT NULL,
    producto_id INT UNSIGNED NOT NULL,
    cantidad TINYINT UNSIGNED NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    estado BIT(1) DEFAULT b'1',
    creado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY pedido_id (pedido_id),
    KEY producto_id (producto_id),
    CONSTRAINT detalle_pedido_ibfk_1 FOREIGN KEY (pedido_id) REFERENCES pedido (id),
    CONSTRAINT detalle_pedido_ibfk_2 FOREIGN KEY (producto_id) REFERENCES producto (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;