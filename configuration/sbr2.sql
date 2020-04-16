-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: moviecenter
-- ------------------------------------------------------
-- Server version	5.7.9-log

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
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orgnr` varchar(45) DEFAULT NULL,
  `hsaid` varchar(45) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `authority_type_cd` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hsaid_UNIQUE` (`hsaid`),
  UNIQUE KEY `orgnr_UNIQUE` (`orgnr`),
  KEY `rbc_authoritytype_fk_idx` (`authority_type_cd`),
  CONSTRAINT `authority_authoritytype_fk` FOREIGN KEY (`authority_type_cd`) REFERENCES `authority_type` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `authority_registry_fk` FOREIGN KEY (`id`) REFERENCES `registry` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,'12345678','ABC123','Test','REG'),(2,'111111','ASD124','Test2','REG'),(3,'XXX124',NULL,NULL,NULL),(7,'XXX125',NULL,NULL,NULL),(9,'XXX126',NULL,NULL,NULL),(10,'XXX127',NULL,NULL,NULL),(11,'XXX128',NULL,NULL,NULL),(12,'XXX129',NULL,NULL,NULL),(13,'XXX130',NULL,NULL,NULL),(14,'XXX131',NULL,NULL,NULL),(15,'XXX132',NULL,NULL,NULL),(16,'XXX133',NULL,NULL,NULL);
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority_type`
--

DROP TABLE IF EXISTS `authority_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority_type` (
  `code` varchar(3) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority_type`
--

LOCK TABLES `authority_type` WRITE;
/*!40000 ALTER TABLE `authority_type` DISABLE KEYS */;
INSERT INTO `authority_type` VALUES ('KOM','Kommun'),('PRI','Privat'),('REG','Landsting/region'),('STA','Staten');
/*!40000 ALTER TABLE `authority_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `biobank`
--

DROP TABLE IF EXISTS `biobank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biobank` (
  `id` int(11) NOT NULL,
  `biobank_type_cd` varchar(3) DEFAULT NULL,
  `purpose_id` int(11) DEFAULT NULL,
  `responsible` varchar(100) DEFAULT NULL,
  `regnr` varchar(45) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `established_dt` date DEFAULT NULL,
  `note` varchar(250) DEFAULT NULL,
  `historical_refid` varchar(45) DEFAULT NULL,
  `ebiobank` tinyint(1) DEFAULT NULL,
  `hsaid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `regnr_UNIQUE` (`regnr`),
  UNIQUE KEY `hsaid_UNIQUE` (`hsaid`),
  KEY `biobank_purpose_fk_idx` (`purpose_id`),
  CONSTRAINT `biobank_purpose_fk` FOREIGN KEY (`purpose_id`) REFERENCES `purpose` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `biobank_registry_fk` FOREIGN KEY (`id`) REFERENCES `registry` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biobank`
--

LOCK TABLES `biobank` WRITE;
/*!40000 ALTER TABLE `biobank` DISABLE KEYS */;
INSERT INTO `biobank` VALUES (3,'TP1',2,'Kalle Karlsson','111','Test','2011-01-03','test','0',0,NULL);
/*!40000 ALTER TABLE `biobank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `biobank_type`
--

DROP TABLE IF EXISTS `biobank_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biobank_type` (
  `code` varchar(3) CHARACTER SET utf8 NOT NULL,
  `name` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biobank_type`
--

LOCK TABLES `biobank_type` WRITE;
/*!40000 ALTER TABLE `biobank_type` DISABLE KEYS */;
INSERT INTO `biobank_type` VALUES ('TP1','Patologi/Cytologi'),('TP2','Klinisk kemi'),('TP3','Gynekologi/obstetrik'),('TP4','IVF och liknande verksamhet'),('TP5','Bakteriologi/virologi'),('TP6','Transfusion'),('TP7','Transplantation'),('TP8','Annan biobank'),('TP9','Kombination av biobanker');
/*!40000 ALTER TABLE `biobank_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `postaladdress` varchar(100) DEFAULT NULL,
  `postalcode` varchar(7) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `hsaid` varchar(45) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,'Tolvan','Tolvansson','Dussinvägen 12','11212','STOCKHOLM','121212','asg567','tolv@tolv.se');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_role`
--

DROP TABLE IF EXISTS `contact_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_role` (
  `code` varchar(3) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_role`
--

LOCK TABLES `contact_role` WRITE;
/*!40000 ALTER TABLE `contact_role` DISABLE KEYS */;
INSERT INTO `contact_role` VALUES ('CON','Kontakt');
/*!40000 ALTER TABLE `contact_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purpose`
--

DROP TABLE IF EXISTS `purpose`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purpose` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vob` tinyint(1) DEFAULT '0',
  `other` tinyint(1) DEFAULT '0',
  `quality` tinyint(1) DEFAULT '0',
  `education` tinyint(1) DEFAULT '0',
  `research` tinyint(1) DEFAULT '0',
  `clinical_testing` tinyint(1) DEFAULT '0',
  `development` tinyint(1) DEFAULT '0',
  `comparable` tinyint(1) DEFAULT '0',
  `comparable_desc` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purpose`
--

LOCK TABLES `purpose` WRITE;
/*!40000 ALTER TABLE `purpose` DISABLE KEYS */;
INSERT INTO `purpose` VALUES (1,1,0,0,0,0,0,0,0,''),(2,0,0,0,0,0,0,0,1,'annan verksamhet');
/*!40000 ALTER TABLE `purpose` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rbc`
--

DROP TABLE IF EXISTS `rbc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rbc` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `hsaid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rbc`
--

LOCK TABLES `rbc` WRITE;
/*!40000 ALTER TABLE `rbc` DISABLE KEYS */;
INSERT INTO `rbc` VALUES (1,'Norra',NULL),(2,'Stockholm',NULL),(3,'Sydöstra',NULL),(4,'Södra',NULL),(5,'Uppsala-Örebro',NULL),(6,'Västra Götaland',NULL);
/*!40000 ALTER TABLE `rbc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registry`
--

DROP TABLE IF EXISTS `registry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_type_cd` varchar(3) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `rbc_id` int(11) DEFAULT NULL,
  `published` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `registry_registry_fk_idx` (`parent_id`),
  KEY `registry_rbc_fk_idx` (`rbc_id`),
  CONSTRAINT `registry_rbc_fk` FOREIGN KEY (`rbc_id`) REFERENCES `rbc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `registry_registry_fk` FOREIGN KEY (`parent_id`) REFERENCES `registry` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registry`
--

LOCK TABLES `registry` WRITE;
/*!40000 ALTER TABLE `registry` DISABLE KEYS */;
INSERT INTO `registry` VALUES (1,'AUT',NULL,'Testhuvudman',1,0),(2,'AUT',NULL,'Test2',2,0),(3,'AUT',NULL,'Testbiobank',NULL,0),(4,'AUT',NULL,NULL,NULL,0),(5,'AUT',NULL,NULL,NULL,0),(6,'AUT',NULL,NULL,NULL,0),(7,'AUT',NULL,NULL,NULL,0),(8,'AUT',NULL,NULL,NULL,0),(9,'AUT',NULL,NULL,NULL,0),(10,'AUT',NULL,NULL,NULL,0),(11,'AUT',NULL,NULL,NULL,0),(12,'AUT',NULL,NULL,NULL,0),(13,'AUT',NULL,NULL,NULL,0),(14,'AUT',NULL,NULL,NULL,0),(15,'AUT',NULL,NULL,NULL,0),(16,'AUT',NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `registry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registry_contact`
--

DROP TABLE IF EXISTS `registry_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registry_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_id` int(11) DEFAULT NULL,
  `registry_id` int(11) DEFAULT NULL,
  `role_cd` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `registry_conjtact_registry_fk_idx` (`registry_id`),
  KEY `registry_contact_role_idx` (`role_cd`),
  CONSTRAINT `registry_contact_registry_fk` FOREIGN KEY (`registry_id`) REFERENCES `registry` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `registry_contact_role` FOREIGN KEY (`role_cd`) REFERENCES `contact_role` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registry_contact`
--

LOCK TABLES `registry_contact` WRITE;
/*!40000 ALTER TABLE `registry_contact` DISABLE KEYS */;
INSERT INTO `registry_contact` VALUES (1,1,1,'CON');
/*!40000 ALTER TABLE `registry_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registry_type`
--

DROP TABLE IF EXISTS `registry_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registry_type` (
  `code` varchar(3) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registry_type`
--

LOCK TABLES `registry_type` WRITE;
/*!40000 ALTER TABLE `registry_type` DISABLE KEYS */;
INSERT INTO `registry_type` VALUES ('AUT','Huvudman'),('BBD','Biobanksavdelning'),('BIO','Biobank'),('COL','Provsamling');
/*!40000 ALTER TABLE `registry_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `samplecollection`
--

DROP TABLE IF EXISTS `samplecollection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `samplecollection` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `release_dt` date DEFAULT NULL,
  `valid_from_dt` date DEFAULT NULL,
  `valid_to_dt` date DEFAULT NULL,
  `historical_refid` varchar(45) DEFAULT NULL,
  `responsible` varchar(100) DEFAULT NULL,
  `admin_user_id` int(11) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `origin_care` tinyint(1) DEFAULT NULL,
  `origin_research` tinyint(1) DEFAULT NULL,
  `regnr` varchar(45) DEFAULT NULL,
  `collection_type_cd` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `regnr_UNIQUE` (`regnr`),
  KEY `samplecollection_collection_type_cd_idx` (`collection_type_cd`),
  CONSTRAINT `samplecollection_collection_type_cd` FOREIGN KEY (`collection_type_cd`) REFERENCES `samplecollection_type` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `samplecollection`
--

LOCK TABLES `samplecollection` WRITE;
/*!40000 ALTER TABLE `samplecollection` DISABLE KEYS */;
/*!40000 ALTER TABLE `samplecollection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `samplecollection_type`
--

DROP TABLE IF EXISTS `samplecollection_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `samplecollection_type` (
  `code` varchar(3) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `samplecollection_type`
--

LOCK TABLES `samplecollection_type` WRITE;
/*!40000 ALTER TABLE `samplecollection_type` DISABLE KEYS */;
INSERT INTO `samplecollection_type` VALUES ('TP1','Patologi/Cytologi'),('TP2','Klinisk kemi'),('TP3','Gynekologi/obstetrik'),('TP4','IVF och liknande verksamhet'),('TP5','Bakteriologi/virologi'),('TP6','Transfusion'),('TP7','Transplantation'),('TP9','Kombination av provsamlingar');
/*!40000 ALTER TABLE `samplecollection_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `hsaid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-28 15:05:27
