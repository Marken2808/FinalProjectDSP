--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `uId` int NOT NULL AUTO_INCREMENT,
  `uUsername` varchar(20) NOT NULL,
  `uPassword` varchar(100) NOT NULL,
  `uStatus` varchar(10) NOT NULL DEFAULT 'Inactive',
  `uRole` varchar(45) NOT NULL DEFAULT 'Guest',
  PRIMARY KEY (`uId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- CREATE TABLE `user` (
--                         `uId` int NOT NULL AUTO_INCREMENT,
--                         `uUsername` varchar(20) NOT NULL,
--                         `uPassword` varchar(100) NOT NULL,
--                         `uStatus` varchar(10) NOT NULL DEFAULT 'Inactive',
--                         `uRole` varchar(45) NOT NULL DEFAULT 'Guest',
--                         PRIMARY KEY (`uId`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (0,'admin','admin','Active','Admin'),(1,'linh','linh','Active','Student'),(2,'tuan','tuan','Active','Student'),(3,'me','me','Active','Student'),(4,'tu','tu','Active','Student'),(5,'thanh','thanh','Active','Student'),(6,'vu','vu','Active','Student'),(11,'ok','ok','Active','Teacher'),(13,'test','test','Active','Teacher'),(15,'haha','haha','Active','Teacher'),(20,'zxc','zxc','Active','Teacher'),(21,'qwe','qwe','Active','Teacher');
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

-- Dump completed on 2021-04-07 13:56:52
