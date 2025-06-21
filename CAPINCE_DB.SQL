DROP DATABASE IF EXISTS capince_db;
CREATE DATABASE capince_db;
USE capince_db;

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
    llevar TINYINT(1) DEFAULT '1',
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
    comentario VARCHAR(70) DEFAULT NULL,
    estado BIT(1) DEFAULT b'1',
    creado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY pedido_id (pedido_id),
    KEY producto_id (producto_id),
    CONSTRAINT detalle_pedido_ibfk_1 FOREIGN KEY (pedido_id) REFERENCES pedido (id),
    CONSTRAINT detalle_pedido_ibfk_2 FOREIGN KEY (producto_id) REFERENCES producto (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/* --------------------------------------------------------------------------------------*/


/* ================ DATA DE MUESTRA ====================== */
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

-- Todas la claves son 123456
INSERT INTO empleado (nombre, apellido_paterno, apellido_materno, telefono, tipo_documento, documento, direccion, distrito, clave, rol, sexo, estado) VALUES 
('Admin', 'Admin', 'Admin', '999999999', 1, '49086269', '000000000000', 1, '$2a$10$KVSE7ItU3xJzNplMky5kx.LBzmHn1O1Pz7y9VwLrEwHucoZ3P8x2S', 1, 3, b'1'), 
('Mariela', 'Solorzano', 'Vargas', '987654321', 1, '41015448', 'Jirón Las Gaviotas 122', 2, '$2a$10$KVSE7ItU3xJzNplMky5kx.LBzmHn1O1Pz7y9VwLrEwHucoZ3P8x2S', 2, 2, b'1'), 
('Andrea Lucía', 'Mamani', 'Quispe', '976543210', 1, '87654321', 'Jirón Las Gaviotas 355', 2, '$2a$10$KVSE7ItU3xJzNplMky5kx.LBzmHn1O1Pz7y9VwLrEwHucoZ3P8x2S', 3, 2, b'1'), 
('Carlos Eduardo', 'Fernández', 'Ríos', '965432109', 1, '23456789', 'Av. Arequipa 789', 1, '$2a$10$KVSE7ItU3xJzNplMky5kx.LBzmHn1O1Pz7y9VwLrEwHucoZ3P8x2S', 4, 1, b'1'), 
('Ana Gabriela', 'Torres', 'Medina', '954321098', 1, '34567890', 'Calle La Paz 321', 1, '$2a$10$KVSE7ItU3xJzNplMky5kx.LBzmHn1O1Pz7y9VwLrEwHucoZ3P8x2S', 3, 2, b'1'), 
('Luis Enrique', 'Martínez', 'Gómez', '943210987', 1, '45678901', 'Av. Brasil 654', 2, '$2a$10$KVSE7ItU3xJzNplMky5kx.LBzmHn1O1Pz7y9VwLrEwHucoZ3P8x2S', 3, 1, b'1'), 
('Sofía Milagros', 'Ruiz', 'Flores', '932109876', 1, '56789012', 'Calle Los Pinos 987', 1, '$2a$10$KVSE7ItU3xJzNplMky5kx.LBzmHn1O1Pz7y9VwLrEwHucoZ3P8x2S', 3, 2, b'1'), 
('Pedro Pablo', 'Contreras', 'Delgado', '951124055', 1, '48452001', 'Jirón Las Gaviotas 88', 2, '$2a$10$KVSE7ItU3xJzNplMky5kx.LBzmHn1O1Pz7y9VwLrEwHucoZ3P8x2S', 4, 1, b'1'), 
('Takumi Ryosuke', 'Fujiwara', 'Sato', '462150109', 1, '50122144', 'Jirón Las Gaviotas 606', 1, '$2a$10$KVSE7ItU3xJzNplMky5kx.LBzmHn1O1Pz7y9VwLrEwHucoZ3P8x2S', 4, 1, b'1'), 
('Jorge Juan', 'Negrete', 'Alvarado', '659501248', 1, '95162840', 'Calle La Joya 2332', 2, '$2a$10$KVSE7ItU3xJzNplMky5kx.LBzmHn1O1Pz7y9VwLrEwHucoZ3P8x2S', 3, 1, b'1');

INSERT INTO delivery (id, unidad, placa) VALUES 
(4, 'Mototaxi', '2102-JD'), 
(8, 'Moto', '3400-AE'), 
(9, 'Mototaxi', 'TRU3-N0');

INSERT INTO producto (nombre, tipo_producto, precio, stock, estado) VALUES  
('Papa a la Huancaína', 1, 3.00, 25, b'1'), ('Causa de Pollo', 1, 3.00, 18, b'1'), ('Causa de Atún', 1, 3.00, 22, b'1'), ('Ocopa Arequipeña', 1, 3.00, 16, b'1'), 
('Yuquitas Fritas', 1, 3.00, 20, b'1'), ('Palta Rellena', 1, 3.00, 14, b'1'), ('Ensalada Rusa', 1, 3.00, 19, b'1'), ('Sopa Criolla', 1, 3.00, 13, b'1'), 
('Chicharrón de Pota', 1, 3.00, 21, b'1'), ('Ceviche', 1, 3.00, 27, b'1'), ('Ensalada Criolla', 1, 3.00, 23, b'1'), ('Gelatina', 1, 3.00, 30, b'1'), 
('Lomo Saltado', 2, 9.00, 20, b'1'), ('Ají de Gallina', 2, 9.00, 15, b'1'), ('Arroz con Pollo', 2, 9.00, 28, b'1'), ('Cau Cau', 2, 9.00, 12, b'1'), 
('Seco con Frejoles', 2, 9.00, 18, b'1'), ('Tacu Tacu con Lomo', 2, 9.00, 22, b'1'), ('Chicharrón de Pescado', 2, 9.00, 17, b'1'), ('Milanesa de pollo', 2, 9.00, 25, b'1'), 
('Arroz Chaufa', 2, 9.00, 29, b'1'), ('Ceviche Mixto', 2, 9.00, 19, b'1'), ('Arroz Tapado', 2, 9.00, 24, b'1'), ('Chupe de Pescado', 2, 9.00, 14, b'1'), 
('Inca Kola 500ml', 3, 2.80, 30, b'1'), ('Inca Kola 1.5L', 3, 4.00, 18, b'1'), ('Chicha Morada', 3, 3.50, 26, b'1'), ('Maracuyá', 3, 3.00, 20, b'1'), 
('Limonada', 3, 3.00, 17, b'1'), ('Coca-Cola Zero 500ml', 3, 3.00, 13, b'1'), ('Coca-Cola Zero 1.5L', 3, 6.00, 16, b'1'), ('Jugo de Naranja', 3, 3.50, 11, b'1'), 
('Agua con gas 500ml', 3, 2.00, 27, b'1'), ('Chicha de Jora', 3, 3.50, 15, b'1'), ('Té de Hierba Luisa', 3, 3.00, '10', b'1'), ('Jugo de Sandía', 3, 3.50, 12, b'1'), 
('Jugo de Carambola', 3, 3.00, 21, b'1'), ('Jugo de Manzana', 3, 3.50, 24, b'1'), ('Helado tricolor', 4, 4.00, 23, b'1'), ('Torta tres leches', 4, 4.50, 11, b'1'), 
('Flan de Vainilla', 4, 3.50, 14, b'1'), ('Helado de vainilla', 4, 2.50, 20, b'1'), ('Arroz con Leche', 4, 3.00, 16, b'1'), ('Crema Volteada', 4, 3.50, 19, b'1'), 
('Café Molido', 1, 2.50, 100, b'1'), ('Papas al hilo', 1, 3.00, 23, b'1');