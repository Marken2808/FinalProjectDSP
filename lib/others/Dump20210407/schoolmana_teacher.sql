--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `tId` int NOT NULL AUTO_INCREMENT,
  `tName` varchar(45) NOT NULL,
  `tCode` varchar(6) DEFAULT NULL,
  `t_uId` int NOT NULL,
  PRIMARY KEY (`tId`),
  UNIQUE KEY `t_uId_UNIQUE` (`t_uId`),
  CONSTRAINT `t_uId` FOREIGN KEY (`t_uId`) REFERENCES `user` (`uId`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- CREATE TABLE `teacher` (
--                            `tId` int NOT NULL AUTO_INCREMENT,
--                            `tName` varchar(45) NOT NULL,
--                            `tCode` varchar(6) DEFAULT NULL,
--                            `t_uId` int NOT NULL,
--                            PRIMARY KEY (`tId`),
--                            UNIQUE KEY `t_uId_UNIQUE` (`t_uId`),
--                            CONSTRAINT `t_uId` FOREIGN KEY (`t_uId`) REFERENCES `user` (`uId`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (100,'imzxc',NULL,20),(102,'Default',NULL,21);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
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
