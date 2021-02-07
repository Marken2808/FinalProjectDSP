CREATE TABLE IF NOT EXISTS `module` (
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
);

INSERT INTO `module` (`mMath`,`mPhysics`,`mChemistry`,`mEnglish`,`mHistory`,`mBiology`,`mGeography`,`m_sId`)
                                                                                                       VALUES ('10', '2', '3', '4', '5', '6', '1', '1');
INSERT INTO `module` (`mMath`,`mPhysics`,`mChemistry`,`mEnglish`,`mHistory`,`mBiology`,`mGeography`,`m_sId`)
                                                                                                       VALUES ('1', '8', '8', '8', '8', '1', '8', '2');
INSERT INTO `module` (`mMath`,`mPhysics`,`mChemistry`,`mEnglish`,`mHistory`,`mBiology`,`mGeography`,`m_sId`)
                                                                                                       VALUES ('9', '9', '9', '9', '9', '9', '9', '3');
INSERT INTO `module` (`mMath`,`mPhysics`,`mChemistry`,`mEnglish`,`mHistory`,`mBiology`,`mGeography`,`m_sId`)
                                                                                                       VALUES ('5', '10', '10', '10', '10', '10', '10', '4');
INSERT INTO `module` (`mMath`,`mPhysics`,`mChemistry`,`mEnglish`,`mHistory`,`mBiology`,`mGeography`,`m_sId`)
                                                                                                       VALUES ('4', '10', '6', '2', '3', '10', '10', '5');


select  m_sId from module where
mMath is null or 
mPhysics is null or 
mChemistry is null or
mEnglish is null or
mHistory is null or
mBiology is null or
mGeography is null
;