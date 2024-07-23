CREATE DATABASE  IF NOT EXISTS `bollettemanagerdb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `bollettemanagerdb`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: bollettemanagerdb
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bollette`
--

DROP TABLE IF EXISTS `bollette`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bollette` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Casa` varchar(45) NOT NULL,
  `Utenza` varchar(45) NOT NULL,
  `Scadenza` date NOT NULL,
  `Importo` double DEFAULT NULL,
  `Pagata` date DEFAULT NULL,
  `QuoteCoinquilini` varchar(155) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bollette`
--

LOCK TABLES `bollette` WRITE;
/*!40000 ALTER TABLE `bollette` DISABLE KEYS */;
INSERT INTO `bollette` VALUES (40,'casa pisa','Acqua','2022-01-12',157.87,'2022-01-10','52.62,0,105.25'),(42,'casa pisa','Luce','2022-01-12',67.45,NULL,'22.48,22.48,22.48'),(43,'casa pisa','Internet','2022-01-26',34.5,'2022-01-21','11.5,11.5,11.5'),(44,'casa pisa','Gas','2022-01-22',127.32,'2022-01-12','42.44,42..44,42.44'),(45,'casa pisa','Tari','2021-12-31',245.12,NULL,'81.70,81.7,81.7'),(48,'casa pisa','Acqua','2022-04-20',68.95,'2022-04-13','22.98,22.98,22.98'),(50,'via bonanno','Acqua','2022-01-11',163.87,'2022-01-02','81.93,81.93'),(51,'via bonanno','Tari','2021-12-31',253,NULL,'126.50,126.50'),(52,'via bonanno','Acqua','2022-02-16',124,NULL,'62,62'),(53,'casa pisa','Internet','2022-02-26',34.5,NULL,'22.98,22.98,22.98'),(54,'casa pisa','Acqua','2022-06-15',56,NULL,'18.6,18.6,18.6'),(55,'casa pisa','Acqua','2020-11-06',216,'2021-12-16','144,36,36');
/*!40000 ALTER TABLE `bollette` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabella_case`
--

DROP TABLE IF EXISTS `tabella_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tabella_case` (
  `Nome` varchar(45) NOT NULL,
  `Inquilini` text,
  PRIMARY KEY (`Nome`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabella_case`
--

LOCK TABLES `tabella_case` WRITE;
/*!40000 ALTER TABLE `tabella_case` DISABLE KEYS */;
INSERT INTO `tabella_case` VALUES ('casa pisa','Giuseppe,Walter,Francesco'),('via bonanno','Luca,Giovanni');
/*!40000 ALTER TABLE `tabella_case` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-27 17:18:03
