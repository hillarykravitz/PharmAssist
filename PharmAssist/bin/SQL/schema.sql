CREATE DATABASE `pharmDB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `Dosages` (
  `dosageID` int NOT NULL AUTO_INCREMENT,
  `medID` int NOT NULL,
  `strength` varchar(50) DEFAULT NULL,
  `form` varchar(100) DEFAULT NULL,
  `appearance` varchar(200) DEFAULT NULL,
  `frequency` varchar(30) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dosageID`),
  KEY `fk_medDosage` (`medID`),
  CONSTRAINT `fk_medDosage` FOREIGN KEY (`medID`) REFERENCES `Medications` (`medID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `DrugInteractions` (
  `interactionID` int NOT NULL AUTO_INCREMENT,
  `medID_1` int DEFAULT NULL,
  `medID_2` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `level` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`interactionID`),
  KEY `medID_1` (`medID_1`),
  KEY `medID_2` (`medID_2`),
  CONSTRAINT `druginteractions_ibfk_1` FOREIGN KEY (`medID_1`) REFERENCES `Medications` (`medID`),
  CONSTRAINT `druginteractions_ibfk_2` FOREIGN KEY (`medID_2`) REFERENCES `Medications` (`medID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Employees` (
  `employeeID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `username` varchar(100) NOT NULL,
  `role` varchar(45) NOT NULL,
  `passwordHash` varchar(100) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`employeeID`),
  UNIQUE KEY `userName_UNIQUE` (`username`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `empMedAccessLog` (
  `logID` int NOT NULL AUTO_INCREMENT,
  `employeeID` int DEFAULT NULL,
  `medID` int DEFAULT NULL,
  `accessTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`logID`),
  KEY `employeeID` (`employeeID`),
  KEY `medID` (`medID`),
  CONSTRAINT `empmedaccesslog_ibfk_1` FOREIGN KEY (`employeeID`) REFERENCES `Employees` (`employeeID`),
  CONSTRAINT `empmedaccesslog_ibfk_2` FOREIGN KEY (`medID`) REFERENCES `Medications` (`medID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `empPatientAccessLog` (
  `logID` int NOT NULL AUTO_INCREMENT,
  `employeeID` int DEFAULT NULL,
  `patientID` int DEFAULT NULL,
  `accessTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`logID`),
  KEY `employeeID` (`employeeID`),
  KEY `patientID` (`patientID`),
  CONSTRAINT `emppatientaccesslog_ibfk_1` FOREIGN KEY (`employeeID`) REFERENCES `Employees` (`employeeID`),
  CONSTRAINT `emppatientaccesslog_ibfk_2` FOREIGN KEY (`patientID`) REFERENCES `Patients` (`patientID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `empRxAccessLog` (
  `logID` int NOT NULL AUTO_INCREMENT,
  `employeeID` int DEFAULT NULL,
  `prescriptionID` int DEFAULT NULL,
  `accessTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`logID`),
  KEY `employeeID` (`employeeID`),
  KEY `prescriptionID` (`prescriptionID`),
  CONSTRAINT `emprxaccesslog_ibfk_1` FOREIGN KEY (`employeeID`) REFERENCES `Employees` (`employeeID`),
  CONSTRAINT `emprxaccesslog_ibfk_2` FOREIGN KEY (`prescriptionID`) REFERENCES `Prescriptions` (`prescriptionID`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Medications` (
  `medID` int NOT NULL AUTO_INCREMENT,
  `medName` varchar(100) NOT NULL,
  `genericName` varchar(100) DEFAULT NULL,
  `isInStock` tinyint(1) DEFAULT NULL,
  `pharmacyQuantity` int DEFAULT NULL,
  PRIMARY KEY (`medID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `PatientRxDetails` (
  `prescriptionID` int DEFAULT NULL,
  `medID` int DEFAULT NULL,
  `dosageID` int DEFAULT NULL,
  `pickupDate` date DEFAULT NULL,
  `patientID` int DEFAULT NULL,
  KEY `fk_RxDetails_1` (`prescriptionID`),
  KEY `fk_RxDetails_2` (`medID`),
  KEY `fk_RxDetails_3` (`dosageID`),
  KEY `fk_RxDetails_4` (`patientID`),
  CONSTRAINT `fk_RxDetails_1` FOREIGN KEY (`prescriptionID`) REFERENCES `Prescriptions` (`prescriptionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_RxDetails_2` FOREIGN KEY (`medID`) REFERENCES `Medications` (`medID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_RxDetails_3` FOREIGN KEY (`dosageID`) REFERENCES `Dosages` (`dosageID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_RxDetails_4` FOREIGN KEY (`patientID`) REFERENCES `Patients` (`patientID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Patients` (
  `patientID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `birthdate` date NOT NULL,
  `phoneNumber` varchar(15) NOT NULL,
  PRIMARY KEY (`patientID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Prescriptions` (
  `prescriptionID` int NOT NULL AUTO_INCREMENT,
  `creationDate` date DEFAULT NULL,
  `completionDate` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`prescriptionID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
