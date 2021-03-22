CREATE TABLE IF NOT EXISTS `student` (
  `sId` int NOT NULL,
  `sName` varchar(20) NOT NULL,
  `s_uId` int NOT NULL,
  PRIMARY KEY (`sId`),
  UNIQUE KEY `s_uId_UNIQUE` (`s_uId`),
  CONSTRAINT `s_uId` FOREIGN KEY (`s_uId`) REFERENCES `user` (`uId`)
)

INSERT INTO `student`(`sId`,`sName`)
                VALUES ('1', 'linh');
INSERT INTO `student`(`sId`,`sName`)
                VALUES ('2', 'tuan');
INSERT INTO `student`(`sId`,`sName`)
                VALUES ('3', 'me chau');
INSERT INTO `student`(`sId`,`sName`)
                VALUES ('4', 'Thanh Tu');
INSERT INTO `student`(`sId`,`sName`)
                VALUES ('5', 'bo thanh');
