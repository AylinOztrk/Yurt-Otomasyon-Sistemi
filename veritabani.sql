-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: yurtotomasyonu
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `izinler`
--

DROP TABLE IF EXISTS `izinler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `izinler` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ogrenci_id` int DEFAULT NULL,
  `baslangic_tarihi` date DEFAULT NULL,
  `bitis_tarihi` date DEFAULT NULL,
  `aciklama` text,
  `durum` varchar(20) DEFAULT 'BEKLEMEDE',
  PRIMARY KEY (`id`),
  KEY `ogrenci_id` (`ogrenci_id`),
  CONSTRAINT `izinler_ibfk_1` FOREIGN KEY (`ogrenci_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `izinler`
--

LOCK TABLES `izinler` WRITE;
/*!40000 ALTER TABLE `izinler` DISABLE KEYS */;
INSERT INTO `izinler` VALUES (1,4,'2025-12-15','2025-12-20','Memlekete dönüş','BEKLEMEDE');
/*!40000 ALTER TABLE `izinler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `odalar`
--

DROP TABLE IF EXISTS `odalar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `odalar` (
  `oda_no` varchar(10) NOT NULL,
  `kapasite` int DEFAULT NULL,
  `doluluk` int DEFAULT '0',
  PRIMARY KEY (`oda_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `odalar`
--

LOCK TABLES `odalar` WRITE;
/*!40000 ALTER TABLE `odalar` DISABLE KEYS */;
INSERT INTO `odalar` VALUES ('101',3,3),('102',2,1),('103',3,1);
/*!40000 ALTER TABLE `odalar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` enum('OGRENCI','PERSONEL') NOT NULL,
  `ad` varchar(50) DEFAULT NULL,
  `soyad` varchar(50) DEFAULT NULL,
  `tc_no` varchar(11) DEFAULT NULL,
  `telefon` varchar(15) DEFAULT NULL,
  `adres` text,
  `oda_no` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `tc_no` (`tc_no`),
  KEY `oda_no` (`oda_no`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`oda_no`) REFERENCES `odalar` (`oda_no`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'mudur','1234','PERSONEL','Ayşe','Yönetici','99999999999',NULL,NULL,NULL),(2,'11111111111','1234','OGRENCI','Aylin','Öztürk','111111111','05551112233','Bursa','101'),(3,'22222222222','1234','OGRENCI','Ayşegül','Avcı','22222222222','05442223344','Ankara','101'),(4,'10567943267','1234','OGRENCI','Mavlyuda','Almazova','10567943267','05647623847','İstanbul','101'),(5,'10765493267','1234','OGRENCI','Şimal','Bahar','10765493267','05436874652',NULL,'103'),(6,'14678236498','1234','OGRENCI','Deniz','Yıldız','14678236498','05419756413',NULL,'102');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-15 20:57:46
