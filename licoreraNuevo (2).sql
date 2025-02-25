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
INSERT INTO `producto` VALUES ('05cadb85-b0cd-42de-b425-9318073ff333',3,'smirnoff_red.png','Vodka Smirnoff Red Label 700ml',62900,'2'),('0d43b6b0-1800-431b-b547-710ba8f97926',10,'java.io.FileNotFoundException: C:\\Users\\Admin\\Documents\\NetBeansProjects\\licorera\\src\\main\\resources\\static\\archivos\\Choclito Familiar 210gr \\CHOCLITO-GR-FAMILIAR-230-GR-300x300.png (The system cannot find the path specified)','Choclito Familiar 210gr ',10000,'4'),('1944422d-eba0-469f-bcbe-4e13ea14db6c',10,'keineken_6.png','Six pack Heineken',22900,'2'),('19e13e8b-b984-4739-b48a-9296061c25df',10,'guaro_azul_375.png','Aguardiente Antioqueño sin azucar media',31300,'2'),('19f668d3-2d42-4fff-90d0-60c40ee96cb9',5,'ron_medellin_3_a_os_750.png','Ron Medellin Añejo 3 años 1Lt',63700,'2'),('1b77da4f-2f6c-451b-b3f3-910b9db93c80',10,'lucky_daikiri.jpg','Lucky Strike Daiquiri',11000,'4'),('1d6bbb25-c420-493a-ac95-a852ae698f58',10,'ron_caldas_media.png','Ron Caldas media',33900,'2'),('1dbf530f-1348-4de8-a1dc-306557c245a8',5,'ron_caldas_5_a_os_750.png','Ron Caldas extra viejo 5 años',63100,'2'),('1ee48e7e-20f7-4789-99b1-ccfa8987356d',5,'electrolic_maracuya.png','Suero Electrolit Maracuya 625ml',12000,'3'),('24379224-bf29-4d7d-9c42-885853786dd0',2,'ron_medellin_3_a_os_2_lirtros.png','Ron Medellin añejo 3 años 2Lt',132200,'2'),('28d4d2ba-a15c-4c50-b7de-26b008c23b6f',10,'lucky_gin.jpg','Lucky Strike Gin',11000,'4'),('2f7949d5-6e9e-4a65-a770-c3e0063938a6',2,'ron_medellin_8_a_os_750.png','Ron Medellin 8 años 750ml',73200,'2'),('417ae72f-bc99-44bf-93d3-768576d3d29f',5,'ron_bacardi_750.png','Ron Bacardi Mojito 750ml',52800,'2'),('4dbd0a13-a662-4eee-a22a-e1a60a312ca2',10,'DORITO-GR-FAMILIAR-213-GR-300x300.png','Doritos familiar 185gr',10000,'4'),('58ba6ad8-723f-45ed-8714-21c3ec3341a2',10,'club.png','Six pack Club Colombia',20000,'2'),('5bb0385e-fcbd-4b0a-bdc4-cdf5627b1f28',5,'electro_limon.png','Suero Electrolit Limon 625ml',12000,'3'),('628585ca-dd0a-434d-872c-c2d34915ada5',5,'ron_medellin_3_a_os_750.png','Ron Medellin Añejo 3 años 750ml',53800,'2'),('6610cd50-2c62-413d-9c6b-01babca2f07c',5,'ron_medellin_dorado.png','Ron Medellin dorado 750ml',50750,'2'),('6aafcd0d-b06f-49a0-9ea0-3f4f25ebb6fe',5,'poker.png','Six pack poker',19100,'2'),('6c3d6a13-c130-4044-a071-21e3f174721e',5,'guaro_verde_750.png','Aguardiente Antioqueño verde 750 ml',47300,'2'),('6ec769ba-5d72-4ae4-8dee-58dff3e37c0e',5,'guaro_azul_750.png','Aguardiente Antioqueño sin azucar 750ml',53300,'2'),('6f0ab9be-bd11-4af3-b648-f1184e7af72b',3,'jose_cuervo_750.png','Tequila Jose Cuervo 750ml',88800,'2'),('78db8f5f-3f92-43f7-ae58-172c1a69f958',10,'gator_rojo.jpg','Gatorade Tropical 500ml',6000,'3'),('7a678836-97c1-49a2-bd87-65d3e0ff30c5',2,'budweiser_24.png','Budweiser x24',54200,'2'),('844c1b89-0908-4aa2-9ea1-8e14127341e1',3,'bacardi_limon_750.png','Ron Bacardi Limon 750ml',51800,'2'),('89fd15f7-563d-40c5-8ad2-c0260b2309ce',2,'guaro_azul_2_litros.png','Aguardiente Antioqueño sin azucar 2Lt',140000,'2'),('8a1e995d-e76f-4cb2-8243-7db1ea3c97bc',5,'electro_fresa_kiwi.png','Suero Electrolit Fresa Kiwi 625ml',12000,'3'),('8a634674-1e5b-46e6-9d99-a3162101f10e',10,'DTodito-Familiar-BBQ-150-gr-300x300.png','De todito familiar BBQ 165gr',10000,'4'),('8da46e74-bea1-4bff-9401-45647abfee0f',5,'ron_medellin_5_a_os_750.png','Ron Medellin 5 años 750ml',52000,'2'),('8df152a4-4421-4ced-8ba7-d8077ea87cd6',5,'ron_caldas_750.png','Ron Caldas tradicional 750ml',56400,'2'),('94118ee1-4e0f-4ff2-8767-ad0b8814e516',2,'absolut_750.png','Vodka Absolut Blue 700ml',86900,'2'),('a65e0714-571f-42f5-b329-07161ae8b24c',10,'aguila.png','Six pack aguila',18200,'2'),('a8230099-1c95-4748-9540-c6493f6dc71e',3,'smirnoff_lata_red.png','Smirnoff Lata roja x4',32600,'2'),('a9b92bb0-57aa-4024-8e35-86937c2eb71d',3,'media_baileys.png','Crema de Whisky Baileys original media',54200,'2'),('acd310ea-750f-49b9-af43-22ed9e4a3463',3,'jose_cuervo_silver_750.png','Tequila Jose Cuervo Silver 750ml',88950,'2'),('b30181fa-cf70-497c-8fca-1ca319e28d7d',3,'media_vodka_350_absolut.png','Vodka Absolut Blue media',33400,'2'),('bcb7cdbb-4b56-45d9-80bc-8581e8ce2fad',3,'smirnoff_lulo_750.png','Vodka Smirnoff Lulo 750ml',52600,'2'),('c0476251-3d79-42d2-82fa-8307b2ce497f',5,'media_smirnoff.png','Vodka Smirnoff Red Label media',59000,'2'),('c0f70862-d335-4ca6-bc00-1e8da4a9dc8a',10,'media_medellin_3_a_os.png','Ron Medellin Añejo 3 años media',33790,'2'),('cbb542f5-1849-4391-a2d3-486d947b83b2',5,'club_negra_6.png','Six pack Club Negra',23000,'2'),('d0202e26-77d4-40d7-a360-36da2b8893da',10,'1.PAPAS_-300x300.png','Papas pollo 105gr',4000,'4'),('d3cdb908-c58c-4720-a893-c19f49768366',3,'smirnoff_lulo_lata_verde.png','Smirnoff Lata verde x4',38000,'2'),('d47287d1-df7e-41d0-8eae-8aa7fd662abf',5,'guaro_trad_litro.png','Aguardiente Antioqueño Rojo 1000ml',59900,'2'),('e1d5e3e4-71f2-4a6a-97f6-795af5aed521',10,'malboro_sandia.jpg','Malboro Sandia media',10000,'4'),('e70eeed9-81ef-4ff3-befe-206a2f7235bf',4,'jose_cuervo_media.png','Media Tequila Jose Cuervo reposado especial',57900,'2'),('e71e61dc-97e6-4c69-9a3a-709a490077f3',3,'guaro_trad_750.png','Aguardiente Antioqueño Rojo 750 ml',51290,'2'),('e9ffd05f-ca75-4feb-931e-080241dfe4a8',10,'breta_a.jpg','Bretaña 300ml',4200,'3'),('f08655dc-77d0-4235-9e01-c6ed7cf2157d',30,'Agua_cielo.jpg','Botella Agua cielo personal',4000,'3'),('fa1918ca-eee3-46ba-83b9-e3c3c9b6b1ad',5,'electro_uva.png','Suero Electrolit Uva 625ml',12000,'3'),('fab3ac88-3264-4ca6-a0d8-c6acbb694a71',5,'electrolic_mora_zul.png','Suero Electrolit mora azul 625ml',12000,'3'),('ff97f3aa-8287-4c21-8715-94662edc771c',5,'electro_naranja_mandarina.png','Suero Electrolit Naranja Mandarina 625ml',12000,'3');
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

-- Dump completed on 2025-02-25 18:09:24
