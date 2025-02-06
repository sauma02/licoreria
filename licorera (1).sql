CREATE DATABASE  IF NOT EXISTS `licorera` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `licorera`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: licorera
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carrito`
--

DROP TABLE IF EXISTS `carrito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrito` (
  `producto_id` varchar(255) NOT NULL,
  `cantidad` int DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  PRIMARY KEY (`producto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrito`
--

LOCK TABLES `carrito` WRITE;
/*!40000 ALTER TABLE `carrito` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` varchar(255) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES ('1','licor1'),('2','alcohol'),('3','noAlcohol'),('4','otros');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria_producto`
--

DROP TABLE IF EXISTS `categoria_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria_producto` (
  `categoria_id` varchar(255) NOT NULL,
  `producto_id` varchar(255) NOT NULL,
  UNIQUE KEY `UK4kqt6tcb7lr40w0aih8vsmkl0` (`producto_id`),
  KEY `FKbnwin3ixyqbm9o7nrhlu1erid` (`categoria_id`),
  CONSTRAINT `FKbnwin3ixyqbm9o7nrhlu1erid` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`),
  CONSTRAINT `FKgvmdtll6l6oyhcxnclgju8uio` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_producto`
--

LOCK TABLES `categoria_producto` WRITE;
/*!40000 ALTER TABLE `categoria_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoria_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenes`
--

DROP TABLE IF EXISTS `ordenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordenes` (
  `id` int NOT NULL,
  `contacto` varchar(255) DEFAULT NULL,
  `detalles` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `carrito_producto_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKffkpna8ok75kdx5xt31w8l591` (`carrito_producto_id`),
  CONSTRAINT `FKaf7bjdyhac9k463a8wwdvruan` FOREIGN KEY (`carrito_producto_id`) REFERENCES `carrito` (`producto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordenes`
--

LOCK TABLES `ordenes` WRITE;
/*!40000 ALTER TABLE `ordenes` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenes_seq`
--

DROP TABLE IF EXISTS `ordenes_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordenes_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordenes_seq`
--

LOCK TABLES `ordenes_seq` WRITE;
/*!40000 ALTER TABLE `ordenes_seq` DISABLE KEYS */;
INSERT INTO `ordenes_seq` VALUES (1);
/*!40000 ALTER TABLE `ordenes_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id` varchar(255) NOT NULL,
  `cantidad` int DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double NOT NULL,
  `categoria_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKodqr7965ok9rwquj1utiamt0m` (`categoria_id`),
  CONSTRAINT `FKodqr7965ok9rwquj1utiamt0m` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES ('05cadb85-b0cd-42de-b425-9318073ff333',3,'smirnoff_red.png','Vodka Smirnoff Red Label 700ml',100000,'2'),('0cac4e9b-6e04-48ab-8120-a242b297f753',21,'guarorojo.jpg.jpeg','licor2',232323,'3'),('1944422d-eba0-469f-bcbe-4e13ea14db6c',10,'keineken_6.png','Six pack Heineken',32100,'2'),('19e13e8b-b984-4739-b48a-9296061c25df',20,'guaro_azul_375.png','Aguardiente Antioqueño sin azucar media',38300,'2'),('19f668d3-2d42-4fff-90d0-60c40ee96cb9',10,'ron_medellin_3_a_os_litro.png','Ron Medellin Añejo 3 años 1Lt',65700,'1'),('1b77da4f-2f6c-451b-b3f3-910b9db93c80',10,'lucky_daikiri.jpg','Lucky Strike Daiquiri',11000,'4'),('1d6bbb25-c420-493a-ac95-a852ae698f58',20,'ron_caldas_media.png','Ron Caldas media',39200,'2'),('1dbf530f-1348-4de8-a1dc-306557c245a8',5,'ron_caldas_5_a_os_750.png','Ron Caldas extra viejo 5 años',72300,'2'),('1ee48e7e-20f7-4789-99b1-ccfa8987356d',5,'electrolic_maracuya.png','Suero Electrolit Maracuya 625ml',12000,'3'),('24379224-bf29-4d7d-9c42-885853786dd0',5,'ron_medellin_3_a_os_2_lirtros.png','Ron Medellin añejo 3 años 2Lt',132200,'2'),('28d4d2ba-a15c-4c50-b7de-26b008c23b6f',10,'lucky_gin.jpg','Lucky Strike Gin',11000,'4'),('2f7949d5-6e9e-4a65-a770-c3e0063938a6',5,'ron_medellin_8_a_os_750.png','Ron Medellin 8 años 750ml',88500,'2'),('417ae72f-bc99-44bf-93d3-768576d3d29f',5,'ron_bacardi_750.png','Ron Bacardi Mojito 750ml',62800,'2'),('58ba6ad8-723f-45ed-8714-21c3ec3341a2',10,'club.png','Six pack Club Colombia',28200,'2'),('5bb0385e-fcbd-4b0a-bdc4-cdf5627b1f28',5,'electro_limon.png','Suero Electrolit Limon 625ml',12000,'3'),('628585ca-dd0a-434d-872c-c2d34915ada5',10,'ron_medellin_3_a_os_750.png','Ron Medellin Añejo 3 años 750ml',63800,'2'),('6610cd50-2c62-413d-9c6b-01babca2f07c',10,'ron_medellin_dorado.png','Ron Medellin dorado 750ml',58750,'2'),('6aafcd0d-b06f-49a0-9ea0-3f4f25ebb6fe',10,'poker.png','Six pack poker',26700,'2'),('6b5e7e0d-ca92-4413-a576-e45438bc07d6',123123,'guarorojo.jpg.jpeg','kljoi',123123,'4'),('6c3d6a13-c130-4044-a071-21e3f174721e',10,'guaro_verde_750.png','Aguardiente Antioqueño verde 750 ml',52300,'2'),('6ec769ba-5d72-4ae4-8dee-58dff3e37c0e',10,'guaro_azul_750.png','Aguardiente Antioqueño sin azucar 750ml',56300,'2'),('6f0ab9be-bd11-4af3-b648-f1184e7af72b',5,'jose_cuervo_750.png','Tequila Jose Cuervo 750ml',94800,'2'),('73bd3202-01a9-4fbf-a6da-4d263536a6ca',21,'guarorojo.jpg.jpeg','licor2',232323,'3'),('78db8f5f-3f92-43f7-ae58-172c1a69f958',10,'gator_rojo.jpg','Gatorade Tropical 500ml',6000,'3'),('7a678836-97c1-49a2-bd87-65d3e0ff30c5',2,'budweiser_24.png','Budweiser x24',64300,'2'),('844c1b89-0908-4aa2-9ea1-8e14127341e1',5,'bacardi_limon_750.png','Ron Bacardi Limon 750ml',62800,'2'),('89fd15f7-563d-40c5-8ad2-c0260b2309ce',5,'guaro_azul_2_litros.png','Aguardiente Antioqueño sin azucar 2Lt',140000,'2'),('8a1e995d-e76f-4cb2-8243-7db1ea3c97bc',5,'electro_fresa_kiwi.png','Suero Electrolit Fresa Kiwi 625ml',12000,'3'),('8da46e74-bea1-4bff-9401-45647abfee0f',10,'ron_medellin_5_a_os_750.png','Ron Medellin 5 años 750ml',64000,'2'),('8df152a4-4421-4ced-8ba7-d8077ea87cd6',10,'ron_caldas_750.png','Ron Caldas tradicional 750ml',62400,'2'),('94118ee1-4e0f-4ff2-8767-ad0b8814e516',5,'absolut_750.png','Vodka Absolut Blue 700ml',100000,'2'),('a65e0714-571f-42f5-b329-07161ae8b24c',10,'aguila.png','Six pack aguila',24700,'2'),('a8230099-1c95-4748-9540-c6493f6dc71e',6,'smirnoff_lata_red.png','Smirnoff Lata roja x4',30600,'2'),('a9b92bb0-57aa-4024-8e35-86937c2eb71d',5,'media_baileys.png','Crema de Whisky Baileys original media',60100,'2'),('acd310ea-750f-49b9-af43-22ed9e4a3463',5,'jose_cuervo_silver_750.png','Tequila Jose Cuervo Silver 750ml',94800,'2'),('b30181fa-cf70-497c-8fca-1ca319e28d7d',5,'media_vodka_350_absolut.png','Vodka Absolut Blue media',68300,'2'),('b56ad8b5-abb0-4172-9e0f-de08accaf94f',123123,'King_Of_Signs_Logo.PNG.png','asdasdasdasdasdasdasd',1.2312312312312312e17,'1'),('bcb7cdbb-4b56-45d9-80bc-8581e8ce2fad',5,'smirnoff_lulo_750.png','Vodka Smirnoff Lulo 750ml',61300,'2'),('c0476251-3d79-42d2-82fa-8307b2ce497f',10,'media_smirnoff.png','Vodka Smirnoff Red Label media',65000,'1'),('c0f70862-d335-4ca6-bc00-1e8da4a9dc8a',20,'media_medellin_3_a_os.png','Ron Medellin Añejo 3 años media',40200,'2'),('cbb542f5-1849-4391-a2d3-486d947b83b2',10,'club_negra_6.png','Six pack Club Negra',28200,'2'),('d3cdb908-c58c-4720-a893-c19f49768366',6,'smirnoff_lulo_lata_verde.png','Smirnoff Lata verde x4',30600,'2'),('d47287d1-df7e-41d0-8eae-8aa7fd662abf',10,'guaro_trad_litro.png','Aguardiente Antioqueño Rojo 1000ml',64300,'2'),('d6012d7a-24b1-4efe-a62d-19943f0cede8',10,'pero.jpg','Doritos',10500,'4'),('dd5d8c96-e44f-4486-bd0f-2d7f3e8ede1e',123123,'guaroz.jpg.jpeg','kljoi',123123,'4'),('e1d5e3e4-71f2-4a6a-97f6-795af5aed521',10,'malboro_sandia.jpg','Malboro Sandia media',10000,'4'),('e70eeed9-81ef-4ff3-befe-206a2f7235bf',8,'jose_cuervo_media.png','Media Tequila Jose Cuervo reposado especial',61800,'2'),('e71e61dc-97e6-4c69-9a3a-709a490077f3',10,'guaro_trad_750.png','Aguardiente Antioqueño Rojo 750 ml',62300,'2'),('f08655dc-77d0-4235-9e01-c6ed7cf2157d',30,'Agua_cielo.jpg','Botella Agua cielo personal',4000,'3'),('fa1918ca-eee3-46ba-83b9-e3c3c9b6b1ad',5,'electro_uva.png','Suero Electrolit Uva 625ml',12000,'3'),('fab3ac88-3264-4ca6-a0d8-c6acbb694a71',5,'electrolic_mora_zul.png','Suero Electrolit mora azul 625ml',12000,'3'),('ff97f3aa-8287-4c21-8715-94662edc771c',5,'electro_naranja_mandarina.png','Suero Electrolit Naranja Mandarina 625ml',12000,'3');
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` varchar(255) NOT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-06 18:52:05
