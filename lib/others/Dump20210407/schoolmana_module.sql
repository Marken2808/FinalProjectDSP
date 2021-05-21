--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `module` (
  `mId` int NOT NULL AUTO_INCREMENT,
  `mMath` double DEFAULT NULL,
  `mPhysics` double DEFAULT NULL,
  `mChemistry` double DEFAULT NULL,
  `mEnglish` double DEFAULT NULL,
  `mHistory` double DEFAULT NULL,
  `mBiology` double DEFAULT NULL,
  `mGeography` double DEFAULT NULL,
  `m_sId` int NOT NULL,
  PRIMARY KEY (`mId`),
  UNIQUE KEY `m_sId_UNIQUE` (`m_sId`),
  CONSTRAINT `m_sId` FOREIGN KEY (`m_sId`) REFERENCES `student` (`sId`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- CREATE TABLE `module` (
--                           `mId` int NOT NULL AUTO_INCREMENT,
--                           `mMath` double DEFAULT NULL,
--                           `mPhysics` double DEFAULT NULL,
--                           `mChemistry` double DEFAULT NULL,
--                           `mEnglish` double DEFAULT NULL,
--                           `mHistory` double DEFAULT NULL,
--                           `mBiology` double DEFAULT NULL,
--                           `mGeography` double DEFAULT NULL,
--                           `m_sId` int NOT NULL,
--                           PRIMARY KEY (`mId`),
--                           UNIQUE KEY `m_sId_UNIQUE` (`m_sId`),
--                           CONSTRAINT `m_sId` FOREIGN KEY (`m_sId`) REFERENCES `student` (`sId`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES (49,10,2,3,4,5,6,1,1),(50,1,8,8,8,8,1,8,2),(51,9,9,9,9,9,9,9,3),(52,5,10,10,10,10,10,10,4),(53,4,10,6,2,3,10,10,5);
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-07 13:56:53
