CREATE TABLE IF NOT EXISTS `attendance` (
  `aDate` date NOT NULL,
  `aStatus` varchar(1) NOT NULL,
  `a_sId` int NOT NULL,
  UNIQUE KEY `group_status` (`aStatus`,`a_sId`,`aDate`),
  KEY `a_sid` (`a_sId`),
  CONSTRAINT `a_sid` FOREIGN KEY (`a_sId`) REFERENCES `student` (`sId`)
)


INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-06', 'A', '1');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-06', 'P', '2');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-06', 'A', '3');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-06', 'P', '4');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-06', 'A', '5');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-07', 'P', '1');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-07', 'A', '2');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-07', 'P', '3');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-07', 'A', '4');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-07', 'P', '5');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-08', 'A', '1');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-08', 'P', '2');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-08', 'A', '3');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-08', 'P', '4');
INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)
                                                    VALUES ('2021-02-08', 'P', '5');