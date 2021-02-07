CREATE TABLE IF NOT EXISTS `face` (
  `fId` int NOT NULL AUTO_INCREMENT,
  `fData` varchar(100) NOT NULL,
  `fSet` int NOT NULL,
  `f_sId` int NOT NULL,
  PRIMARY KEY (`fId`),
  UNIQUE KEY `group_set` (`fSet`,`f_sId`),
  CONSTRAINT `f_sId` FOREIGN KEY (`f_sId`) REFERENCES `student` (`sId`)
);

INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/1-linh_0.jpg', '0', '1');
INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/1-linh_1.jpg', '1', '1');
INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/2-tuan_0.jpg', '0', '2');
INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/2-tuan_1.jpg', '1', '2');
INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/3-chau_0.jpg', '0', '3');
INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/3-chau_1.jpg', '1', '3');
INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/4-tu_0.jpg', '0', '4');
INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/4-tu_1.jpg', '1', '4');
INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/5-thanh_0.jpg', '0', '5');
INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/5-thanh_1.jpg', '1', '5');
INSERT INTO `face` (`fData`,`fSet`,`f_sId`)
VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/2-tuan_2.jpg', '2', '2');
