import utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabasePreLoad {

    private static Connection conn;
    private static PreparedStatement ps;

    public static void main(String[] args) {
        System.out.println("OK");
        run();
    }

    public static void run(){
        conn = DBbean.getConnection();
        tableStudent();
        tableFace();
        tableAttendance();
        tableModule();
    }

    public static void exeQuery(String query){
        try {
            String[] insert = query.split(";");

            for (String i : insert) {
                ps = conn.prepareStatement(i);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void tableStudent(){
        String create =
                "CREATE TABLE IF NOT EXISTS `student` (\n" +
                "  `sId` int NOT NULL,\n" +
                "  `sName` varchar(20) NOT NULL,\n" +
                "  PRIMARY KEY (`sId`)\n" +
                ");";
        exeQuery(create);
        System.out.println("Table STUDENT created successfully!");

        String insert =
                "INSERT INTO `student`(`sId`,`sName`)\n" +
                "                VALUES ('1', 'linh');\n" +
                "INSERT INTO `student`(`sId`,`sName`)\n" +
                "                VALUES ('2', 'tuan');\n" +
                "INSERT INTO `student`(`sId`,`sName`)\n" +
                "                VALUES ('3', 'me chau');\n" +
                "INSERT INTO `student`(`sId`,`sName`)\n" +
                "                VALUES ('4', 'Thanh Tu');\n" +
                "INSERT INTO `student`(`sId`,`sName`)\n" +
                "                VALUES ('5', 'bo thanh');";
        exeQuery(insert);
        System.out.println("Table STUDENT inserted successfully!");
    }

    public static void tableModule(){
        String create =
                "CREATE TABLE IF NOT EXISTS `module` (\n" +
                "  `mId` int NOT NULL AUTO_INCREMENT,\n" +
                "  `mMath` double DEFAULT NULL,\n" +
                "  `mPhysics` double DEFAULT NULL,\n" +
                "  `mChemistry` double DEFAULT NULL,\n" +
                "  `mEnglish` double DEFAULT NULL,\n" +
                "  `mHistory` double DEFAULT NULL,\n" +
                "  `mBiology` double DEFAULT NULL,\n" +
                "  `mGeography` double DEFAULT NULL,\n" +
                "  `m_sId` int NOT NULL,\n" +
                "  PRIMARY KEY (`mId`),\n" +
                "  UNIQUE KEY `m_sId_UNIQUE` (`m_sId`),\n" +
                "  CONSTRAINT `m_sId` FOREIGN KEY (`m_sId`) REFERENCES `student` (`sId`)\n" +
                ");";
        exeQuery(create);
        System.out.println("Table MODULE created successfully!");

        String insert =
                "INSERT INTO `module` (`mMath`,`mPhysics`,`mChemistry`,`mEnglish`,`mHistory`,`mBiology`,`mGeography`,`m_sId`)\n" +
                "VALUES ('10', '2', '3', '4', '5', '6', '1', '1');\n" +
                "INSERT INTO `module` (`mMath`,`mPhysics`,`mChemistry`,`mEnglish`,`mHistory`,`mBiology`,`mGeography`,`m_sId`)\n" +
                "VALUES ('1', '8', '8', '8', '8', '1', '8', '2');\n" +
                "INSERT INTO `module` (`mMath`,`mPhysics`,`mChemistry`,`mEnglish`,`mHistory`,`mBiology`,`mGeography`,`m_sId`)\n" +
                "VALUES ('9', '9', '9', '9', '9', '9', '9', '3');\n" +
                "INSERT INTO `module` (`mMath`,`mPhysics`,`mChemistry`,`mEnglish`,`mHistory`,`mBiology`,`mGeography`,`m_sId`)\n" +
                "VALUES ('5', '10', '10', '10', '10', '10', '10', '4');\n" +
                "INSERT INTO `module` (`mMath`,`mPhysics`,`mChemistry`,`mEnglish`,`mHistory`,`mBiology`,`mGeography`,`m_sId`)\n" +
                "VALUES ('4', '10', '6', '2', '3', '10', '10', '5');";
        exeQuery(insert);
        System.out.println("Table MODULE inserted successfully!");
    }

    public static void tableAttendance(){
        String create =
                "CREATE TABLE IF NOT EXISTS `attendance` (\n" +
                "  `aDate` date NOT NULL,\n" +
                "  `aStatus` varchar(1) NOT NULL,\n" +
                "  `a_sId` int NOT NULL,\n" +
                "  UNIQUE KEY `group_status` (`aStatus`,`a_sId`,`aDate`),\n" +
                "  KEY `a_sid` (`a_sId`),\n" +
                "  CONSTRAINT `a_sid` FOREIGN KEY (`a_sId`) REFERENCES `student` (`sId`)\n" +
                ")\n";
        exeQuery(create);
        System.out.println("Table ATTENDANCE created successfully!");

        String insert =
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-06', 'A', '1');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-06', 'P', '2');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-06', 'A', '3');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-06', 'P', '4');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-06', 'A', '5');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-07', 'P', '1');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-07', 'A', '2');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-07', 'P', '3');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-07', 'A', '4');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-07', 'P', '5');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-08', 'A', '1');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-08', 'P', '2');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-08', 'A', '3');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-08', 'P', '4');\n" +
                "INSERT INTO `attendance` (`aDate`,`aStatus`,`a_sId`)\n" +
                "                                                    VALUES ('2021-02-08', 'P', '5');";
        exeQuery(insert);
        System.out.println("Table ATTENDANCE inserted successfully!");
    }

    public static void tableFace(){
        String create =
                "CREATE TABLE IF NOT EXISTS `face` (\n" +
                "  `fId` int NOT NULL AUTO_INCREMENT,\n" +
                "  `fData` varchar(100) NOT NULL,\n" +
                "  `fSet` int NOT NULL,\n" +
                "  `f_sId` int NOT NULL,\n" +
                "  PRIMARY KEY (`fId`),\n" +
                "  UNIQUE KEY `group_set` (`fSet`,`f_sId`),\n" +
                "  CONSTRAINT `f_sId` FOREIGN KEY (`f_sId`) REFERENCES `student` (`sId`)\n" +
                ");";
        exeQuery(create);
        System.out.println("Table FACE created successfully!");

        String insert =
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/1-linh_0.jpg', '0', '1');\n" +
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/1-linh_1.jpg', '1', '1');\n" +
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/2-tuan_0.jpg', '0', '2');\n" +
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/2-tuan_1.jpg', '1', '2');\n" +
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/3-chau_0.jpg', '0', '3');\n" +
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/3-chau_1.jpg', '1', '3');\n" +
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/4-tu_0.jpg', '0', '4');\n" +
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/4-tu_1.jpg', '1', '4');\n" +
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/5-thanh_0.jpg', '0', '5');\n" +
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/5-thanh_1.jpg', '1', '5');\n" +
                "INSERT INTO `face` (`fData`,`fSet`,`f_sId`)\n" +
                "VALUES ('D:\\UWE\\Year_3\\DSP\\FinalProject\\SchoolManagement\\src\\resources\\images/dataset/2-tuan_2.jpg', '2', '2');";
        exeQuery(insert);
        System.out.println("Table FACE inserted successfully!");
    }




}
