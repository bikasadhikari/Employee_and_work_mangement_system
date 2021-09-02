-- MySQL dump 10.13  Distrib 8.0.26, for Linux (x86_64)
--
-- Host: localhost    Database: employeeDB
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fName` varchar(45) DEFAULT NULL,
  `lName` varchar(45) DEFAULT NULL,
  `job_title` varchar(45) DEFAULT NULL,
  `dept` varchar(45) DEFAULT NULL,
  `dob` varchar(45) DEFAULT NULL,
  `phone` bigint DEFAULT NULL,
  `user_Id` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `salary` bigint DEFAULT NULL,
  `date_of_join` varchar(45) DEFAULT NULL,
  `shift` varchar(45) DEFAULT '1',
  `inproject` int DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `employees_chk_1` CHECK ((`salary` >= 0)),
  CONSTRAINT `employees_chk_2` CHECK ((`salary` >= 0)),
  CONSTRAINT `employees_chk_3` CHECK ((`shift` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=armscii8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (5,'Mohit','Kumawat','Testing','Testing','15/05/2000',9898989898,'moh','hit',100800,'Sat Aug 21 14:26:27 IST 2021','1',0),(6,'Ankit','Kumar','Project Manager','Software Development','05/10/1998',1234567890,'ankit','123',90000,'Sun Aug 29 15:41:28 IST 2021','1',0),(7,'Ajay','Vaishnav','Tester','Testing','01/09/1999',9748591254,'aju','123',5000,'Tue Aug 31 00:55:08 IST 2021','1',0),(9,'Akshay','B','Security Analyst','Analyst','12/3/2001',1234567890,'ak','123',34067,'Tue Aug 31 01:05:21 IST 2021','1',0),(10,'Chaithra','H T','Tester','Testing','05/04/2000',9611253585,'chaithu','chai',10000000,'Wed Sep 01 18:22:51 IST 2021','2',0),(12,'Bikas','Adhikari','Software Developer','Development','06/05/2000',6362645318,'bikas','123',50000,'Thu Sep 02 11:40:24 IST 2021','1',0);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-02 11:47:12
