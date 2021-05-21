--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `sId` int NOT NULL,
  `sName` varchar(20) NOT NULL,
  `s_uId` int NOT NULL,
  PRIMARY KEY (`sId`),
  UNIQUE KEY `s_uId_UNIQUE` (`s_uId`),
  CONSTRAINT `s_uId` FOREIGN KEY (`s_uId`) REFERENCES `user` (`uId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- CREATE TABLE `student` (
--                            `sId` int NOT NULL,
--                            `sName` varchar(20) DEFAULT 'DEFAULT',
--                            `s_uId` int NOT NULL,
--                            PRIMARY KEY (`sId`),
--                            UNIQUE KEY `s_uId_UNIQUE` (`s_uId`),
--                            CONSTRAINT `s_uId` FOREIGN KEY (`s_uId`) REFERENCES `user` (`uId`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'LINH',1),(2,'TUAN',2),(3,'ME',3),(4,'TU',4),(5,'THANH',5);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
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
