--
-- Table structure for table `face`
--

DROP TABLE IF EXISTS `face`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `face` (
  `fId` int NOT NULL AUTO_INCREMENT,
  `fData` varchar(100) NOT NULL,
  `fSet` int NOT NULL,
  `f_sId` int NOT NULL,
  PRIMARY KEY (`fId`),
  UNIQUE KEY `group_set` (`fSet`,`f_sId`),
  KEY `f_sId` (`f_sId`),
  CONSTRAINT `f_sId` FOREIGN KEY (`f_sId`) REFERENCES `student` (`sId`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- CREATE TABLE `face` (
--     `fId` int NOT NULL AUTO_INCREMENT,
--     `fData` blob NOT NULL,
--     `fSet` int NOT NULL,
--     `f_sId` int NOT NULL,
--     PRIMARY KEY (`fId`),
--     UNIQUE KEY `group_set` (`fSet`,`f_sId`),
--     KEY `f_sId` (`f_sId`),
--     CONSTRAINT `f_sId` FOREIGN KEY (`f_sId`) REFERENCES `student` (`sId`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `face`
--

LOCK TABLES `face` WRITE;
/*!40000 ALTER TABLE `face` DISABLE KEYS */;
INSERT INTO `face` VALUES (120,'D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/test/0-new_0.jpg',0,1),(121,'D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/test/0-new_0.jpg',0,2),(122,'D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/test/0-new_0.jpg',1,2),(124,'D:\\UWE\\Year_3\\DSP\\FinalProject\\StudentManagement\\src\\resources\\images/test/0-new_0.jpg',0,5),(125,'D:\\UWE\\Year_3\\DSP\\FinalProject\\StudentManagement\\src\\resources\\images/test/0-new_0.jpg',1,5);
/*!40000 ALTER TABLE `face` ENABLE KEYS */;
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
