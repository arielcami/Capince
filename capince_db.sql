-- Disable foreign key checks to allow tables to be dropped and created in any order
SET FOREIGN_KEY_CHECKS = 0;

-- Drop database if it exists and create a new one
DROP DATABASE IF EXISTS capince_db;
CREATE DATABASE capince_db;

-- Use the newly created database
USE capince_db;

-- Create tables in the correct order to satisfy foreign key dependencies

-- Table: distrito
CREATE TABLE `distrito` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(35) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: rol
CREATE TABLE `rol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: sexo
CREATE TABLE `sexo` (
  `id` smallint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: tipo_documento
CREATE TABLE `tipo_documento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: tipo_producto
CREATE TABLE `tipo_producto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insert initial data into lookup tables
INSERT INTO `rol`(`id`,`nombre`) values (1,'ADMIN'),(2,'GERENTE'),(3,'MESERO'),(4,'RECEPCION');
INSERT INTO `sexo`(`id`,`nombre`) values (1,'Masculino'),(2,'Femenino'),(3,'No especificado');
INSERT INTO `tipo_documento`(`id`,`nombre`) values (1,'DNI'),(2,'Carnet de Extranjería'),(3,'Pasaporte'),(4,'Partida de Nacimiento');
INSERT INTO `tipo_producto`(`id`,`nombre`) values (1,'Entrada'),(2,'Segundo'),(3,'Bebida'),(4,'Postre'),(5,'Guarnición');
INSERT INTO `distrito`(`id`,`nombre`) values (1,'Surco'),(2,'Chorrillos'),(3,'Ancón'),(4,'Ate'),(5,'Barranco'),(6,'Breña'),(7,'Carabayllo'),(8,'Cercado de Lima'),(9,'Comas'),(10,'El Agustino'),(11,'Independencia'),(12,'Jesús María'),(13,'La Molina'),(14,'La Victoria'),(15,'Lince'),(16,'Los Olivos'),(17,'Lurigancho'),(18,'Lurín'),(19,'Magdalena del Mar'),(20,'Miraflores'),(21,'Pachacámac'),(22,'Pucusana'),(23,'Pueblo Libre'),(24,'Puente Piedra'),(25,'Punta Hermosa'),(26,'Punta Negra'),(27,'Rímac'),(28,'San Bartolo'),(29,'San Borja'),(30,'San Isidro'),(31,'San Juan de Lurigancho'),(32,'San Juan de Miraflores'),(33,'San Luis'),(34,'San Martín de Porres'),(35,'San Miguel'),(36,'Santa Anita'),(37,'Santa María del Mar'),(38,'Santa Rosa'),(39,'Santiago de Surco'),(40,'Villa El Salvador'),(41,'Villa María del Triunfo');

-- Table: empleado (depends on sexo, tipo_documento, distrito, rol)
CREATE TABLE `empleado` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellido_materno` varchar(30) NOT NULL,
  `apellido_paterno` varchar(30) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `documento` varchar(12) NOT NULL,
  `estado` bit(1) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `telefono` varchar(19) NOT NULL,
  `actualizado_en` datetime(6) DEFAULT NULL,
  `creado_en` datetime(6) DEFAULT NULL,
  `password` varchar(80) NOT NULL,
  `username` varchar(50) NOT NULL,
  `sexo` smallint NOT NULL,
  `tipo_documento` bigint NOT NULL,
  `distrito` bigint NOT NULL,
  `rol` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjw23wnrlt6uf731isj23r1hpe` (`documento`),
  KEY `FKak77hwhr02jpyhwdlcphd2opm` (`sexo`),
  KEY `FKe5t7gexu3vgsd2bes06kr5pk9` (`tipo_documento`),
  KEY `FK5e3p1wh11xe0qbbvocum8eyb` (`distrito`),
  KEY `FKb77ax8kfrdsotg17r968qga15` (`rol`),
  CONSTRAINT `FK5e3p1wh11xe0qbbvocum8eyb` FOREIGN KEY (`distrito`) REFERENCES `distrito` (`id`),
  CONSTRAINT `FKak77hwhr02jpyhwdlcphd2opm` FOREIGN KEY (`sexo`) REFERENCES `sexo` (`id`),
  CONSTRAINT `FKb77ax8kfrdsotg17r968qga15` FOREIGN KEY (`rol`) REFERENCES `rol` (`id`),
  CONSTRAINT `FKe5t7gexu3vgsd2bes06kr5pk9` FOREIGN KEY (`tipo_documento`) REFERENCES `tipo_documento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: cliente (depends on sexo, tipo_documento, distrito)
CREATE TABLE `cliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellido_materno` varchar(30) NOT NULL,
  `apellido_paterno` varchar(30) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `documento` varchar(12) NOT NULL,
  `estado` bit(1) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `telefono` varchar(19) NOT NULL,
  `actualizado_en` datetime(6) DEFAULT NULL,
  `creado_en` datetime(6) DEFAULT NULL,
  `sexo` smallint NOT NULL,
  `tipo_documento` bigint NOT NULL,
  `distrito` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKg18g15ijpoftfxm5v87awpbym` (`documento`),
  KEY `FKba9boar4x2mfjqn475d5ja6eb` (`sexo`),
  KEY `FKc5342kcdcdj8dp84arxub8aqv` (`tipo_documento`),
  KEY `FKciflq63gfc54eu6t8818786ka` (`distrito`),
  CONSTRAINT `FKba9boar4x2mfjqn475d5ja6eb` FOREIGN KEY (`sexo`) REFERENCES `sexo` (`id`),
  CONSTRAINT `FKc5342kcdcdj8dp84arxub8aqv` FOREIGN KEY (`tipo_documento`) REFERENCES `tipo_documento` (`id`),
  CONSTRAINT `FKciflq63gfc54eu6t8818786ka` FOREIGN KEY (`distrito`) REFERENCES `distrito` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: producto (depends on tipo_producto)
CREATE TABLE `producto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `actualizado_en` datetime(6) DEFAULT NULL,
  `creado_en` datetime(6) DEFAULT NULL,
  `estado` bit(1) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `stock` int NOT NULL,
  `tipo_producto` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9su14n91mtgcg5ehl658v4afx` (`nombre`),
  KEY `FKjaetljra1pe8sic3l1sig3lp8` (`tipo_producto`),
  CONSTRAINT `FKjaetljra1pe8sic3l1sig3lp8` FOREIGN KEY (`tipo_producto`) REFERENCES `tipo_producto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: pedido (depends on cliente, empleado)
CREATE TABLE `pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `actualizado_en` datetime(6) DEFAULT NULL,
  `creado_en` datetime(6) DEFAULT NULL,
  `direccion_entrega` varchar(200) DEFAULT NULL,
  `estado` tinyint NOT NULL,
  `fecha_pedido` datetime(6) NOT NULL,
  `llevar` bit(1) DEFAULT NULL,
  `total` decimal(10,2) NOT NULL,
  `cliente_id` bigint NOT NULL,
  `empleado_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK30s8j2ktpay6of18lbyqn3632` (`cliente_id`),
  KEY `FK1nibrtel55qwnf6rwabwsqkyi` (`empleado_id`),
  CONSTRAINT `FK1nibrtel55qwnf6rwabwsqkyi` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `FK30s8j2ktpay6of18lbyqn3632` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: detalle_pedido (depends on pedido, producto)
CREATE TABLE `detalle_pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `actualizado_en` datetime(6) DEFAULT NULL,
  `cantidad` int NOT NULL,
  `comentario` varchar(70) DEFAULT NULL,
  `creado_en` datetime(6) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  `pedido_id` bigint NOT NULL,
  `producto_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgqvba9e7dildyw45u0usdj1k2` (`pedido_id`),
  KEY `FK2yc3nts8mdyqf6dw6ndosk67a` (`producto_id`),
  CONSTRAINT `FK2yc3nts8mdyqf6dw6ndosk67a` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`),
  CONSTRAINT `FKgqvba9e7dildyw45u0usdj1k2` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `empleado`(`id`,`apellido_materno`,`apellido_paterno`,`direccion`,`documento`,`estado`,`nombre`,`telefono`,`actualizado_en`,`creado_en`,`password`,`username`,`sexo`,`tipo_documento`,`distrito`,`rol`) values (1,'Admin','Admin','000000000000','49086269','','Admin','999999999',NULL,NULL,'$2a$10$Qm6avULBNYYK.xEQFFs0ZuZ4KG9Lc8YldMXftowIKmPXnN.1c5u1a','admin',3,1,1,1),(2,'Vargas','Solorzano','Jirón Las Gaviotas 122','41015448','','Mariela','987654321',NULL,NULL,'$2a$10$Qm6avULBNYYK.xEQFFs0ZuZ4KG9Lc8YldMXftowIKmPXnN.1c5u1a','msolorzano',2,1,2,2),(3,'Quispe','Mamani','Jirón Las Gaviotas 355','87654321','','Andrea Lucía','976543210',NULL,NULL,'$2a$10$Qm6avULBNYYK.xEQFFs0ZuZ4KG9Lc8YldMXftowIKmPXnN.1c5u1a','amamani',2,1,2,3),(4,'Ríos','Fernández','Av. Arequipa 789','23456789','','Carlos Eduardo','965432109',NULL,NULL,'$2a$10$Qm6avULBNYYK.xEQFFs0ZuZ4KG9Lc8YldMXftowIKmPXnN.1c5u1a','cfernandez',1,1,1,4),(5,'Medina','Torres','Calle La Paz 321','34567890','','Ana Gabriela','954321098',NULL,NULL,'$2a$10$Qm6avULBNYYK.xEQFFs0ZuZ4KG9Lc8YldMXftowIKmPXnN.1c5u1a','atorres',2,1,1,3),(6,'Gómez','Martínez','Av. Brasil 654','45678901','','Luis Enrique','943210987',NULL,NULL,'$2a$10$Qm6avULBNYYK.xEQFFs0ZuZ4KG9Lc8YldMXftowIKmPXnN.1c5u1a','lmartinez',1,1,2,3),(7,'Flores','Ruiz','Calle Los Pinos 987','56789012','','Sofía Milagros','932109876',NULL,NULL,'$2a$10$Qm6avULBNYYK.xEQFFs0ZuZ4KG9Lc8YldMXftowIKmPXnN.1c5u1a','sruiz',2,1,1,3),(8,'Delgado','Contreras','Jirón Las Gaviotas 88','48452001','','Pedro Pablo','951124055',NULL,NULL,'$2a$10$Qm6avULBNYYK.xEQFFs0ZuZ4KG9Lc8YldMXftowIKmPXnN.1c5u1a','pcontreras',1,1,2,4),(9,'Sato','Fujiwara','Jirón Las Gaviotas 606','50122144','','Takumi Ryosuke','462150109',NULL,NULL,'$2a$10$Qm6avULBNYYK.xEQFFs0ZuZ4KG9Lc8YldMXftowIKmPXnN.1c5u1a','tfujiwara',1,1,1,4),(10,'Alvarado','Negrete','Calle La Joya 2332','95162840','','Jorge Juan','659501248',NULL,NULL,'$2a$10$Qm6avULBNYYK.xEQFFs0ZuZ4KG9Lc8YldMXftowIKmPXnN.1c5u1a','jnegrete',1,1,2,3);
INSERT INTO `producto`(`id`,`actualizado_en`,`creado_en`,`estado`,`nombre`,`precio`,`stock`,`tipo_producto`) values (1,NULL,NULL,'','Papa a la Huancaína',3.00,25,1),(2,NULL,NULL,'','Causa de Pollo',3.00,18,1),(3,NULL,NULL,'','Causa de Atún',3.00,22,1),(4,NULL,NULL,'','Ocopa Arequipeña',3.00,16,1),(5,NULL,NULL,'','Yuquitas Fritas',3.00,20,1),(6,NULL,NULL,'','Palta Rellena',3.00,14,1),(7,NULL,NULL,'','Ensalada Rusa',3.00,19,1),(8,NULL,NULL,'','Sopa Criolla',3.00,13,1),(9,NULL,NULL,'','Chicharrón de Pota',3.00,21,1),(10,NULL,NULL,'','Ceviche',3.00,27,1),(11,NULL,NULL,'','Ensalada Criolla',3.00,23,1),(12,NULL,NULL,'','Gelatina',3.00,30,1),(13,NULL,NULL,'','Lomo Saltado',9.00,20,2),(14,NULL,NULL,'','Ají de Gallina',9.00,15,2),(15,NULL,NULL,'','Arroz con Pollo',9.00,28,2),(16,NULL,NULL,'','Cau Cau',9.00,12,2),(17,NULL,NULL,'','Seco con Frejoles',9.00,18,2),(18,NULL,NULL,'','Tacu Tacu con Lomo',9.00,22,2),(19,NULL,NULL,'','Chicharrón de Pescado',9.00,17,2),(20,NULL,NULL,'','Milanesa de pollo',9.00,25,2),(21,NULL,NULL,'','Arroz Chaufa',9.00,29,2),(22,NULL,NULL,'','Ceviche Mixto',9.00,19,2),(23,NULL,NULL,'','Arroz Tapado',9.00,24,2),(24,NULL,NULL,'','Chupe de Pescado',9.00,14,2),(25,NULL,NULL,'','Inca Kola 500ml',2.80,30,3),(26,NULL,NULL,'','Inca Kola 1.5L',4.00,18,3),(27,NULL,NULL,'','Chicha Morada',3.50,26,3),(28,NULL,NULL,'','Maracuyá',3.00,20,3),(29,NULL,NULL,'','Limonada',3.00,17,3),(30,NULL,NULL,'','Coca-Cola Zero 500ml',3.00,13,3),(31,NULL,NULL,'','Coca-Cola Zero 1.5L',6.00,16,3),(32,NULL,NULL,'','Jugo de Naranja',3.50,11,3),(33,NULL,NULL,'','Agua con gas 500ml',2.00,27,3),(34,NULL,NULL,'','Chicha de Jora',3.50,15,3),(35,NULL,NULL,'','Té de Hierba Luisa',3.00,10,3),(36,NULL,NULL,'','Jugo de Sandía',3.50,12,3),(37,NULL,NULL,'','Jugo de Carambola',3.00,21,3),(38,NULL,NULL,'','Jugo de Manzana',3.50,24,3),(39,NULL,NULL,'','Helado tricolor',4.00,23,4),(40,NULL,NULL,'','Torta tres leches',4.50,11,4),(41,NULL,NULL,'','Flan de Vainilla',3.50,14,4),(42,NULL,NULL,'','Helado de vainilla',2.50,20,4),(43,NULL,NULL,'','Arroz con Leche',3.00,16,4),(44,NULL,NULL,'','Crema Volteada',3.50,19,4),(45,NULL,NULL,'','Café Molido',2.50,100,1),(46,NULL,NULL,'','Papas al hilo',3.00,23,1),(47,'2025-07-09 18:59:29.481120','2025-07-09 18:59:15.068826','','Ensalada tradicional',3.00,21,5);
INSERT INTO `cliente`(`id`,`apellido_materno`,`apellido_paterno`,`direccion`,`documento`,`estado`,`nombre`,`telefono`,`actualizado_en`,`creado_en`,`sexo`,`tipo_documento`,`distrito`) values (1,'Peña','Zambrano','Av. Los colores oscuros 233','60211548','','Tony Alberto','615487318','2025-07-09 18:59:48.218469','2025-07-09 18:59:48.218469',1,1,29),(2,'Vásquez','Henríquez','Av. Defensores del Morro 220','31621588','','Ávaro Yadimir','534155487','2025-07-09 19:08:40.140537','2025-07-09 19:08:40.140537',1,1,1);
