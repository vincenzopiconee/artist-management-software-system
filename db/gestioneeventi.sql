-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 03, 2024 at 09:25 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestioneeventi`
--
--

-- --------------------------------------------------------
-- Table structure for table `musicisti`
--

CREATE TABLE `musicisti` (
  `IdMusicista` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  `Cognome` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Specializzazione` enum('Cantante','Strumentista') NOT NULL,
  `Cachet` float NOT NULL,
  `Telefono` int(10) UNSIGNED NOT NULL,
  `registroVocale` VARCHAR(50) NULL,
   PRIMARY KEY (`IdMusicista`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

-- --------------------------------------------------------

--
-- Table structure for table `disponibility`
--
CREATE TABLE `disponibility` (
  `IdMusicista` int(11) UNSIGNED NOT NULL,
  `Data` date NOT NULL,
  PRIMARY KEY (`IdMusicista`, `Data`),
  CONSTRAINT `ChiaveEst3` FOREIGN KEY (`IdMusicista`) REFERENCES `musicisti` (`IdMusicista`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------

--
-- Table structure for table `eventi`
--

CREATE TABLE `eventi` (
  `IdEvento` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Data` date NOT NULL,
  `Luogo` varchar(50) NOT NULL,
  `Tipologia` enum('Concerto','Esibizione','Strumentale') NOT NULL,
  `CostoTotale` float NOT NULL,
  `MusicistiIncerti` int(11) NOT NULL,
  `Stato` enum('CONFERMATO','IN_ATTESA') NOT NULL DEFAULT 'IN_ATTESA',
  PRIMARY KEY (`IdEvento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `generemusicalecantanti`
--

CREATE TABLE `generemusicalecantanti` (
  `IdMusicista` int(10) UNSIGNED NOT NULL,
  `GenereMusicale` enum('Classica','Leggera','Pop','Jazz') NOT NULL,
  `RegistroVocale` enum('Soprano','Mezzosoprano','Contralto','Basso','Tenore','Baritono') DEFAULT NULL,
  PRIMARY KEY (`IdMusicista`),
  CONSTRAINT `ChiaveExt3` FOREIGN KEY (`IdMusicista`) REFERENCES `musicisti` (`IdMusicista`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `partecipazioni`
--

CREATE TABLE `partecipazioni` (
  `IdEvento` int(10) UNSIGNED NOT NULL,
  `IdMusicista` int(10) UNSIGNED NOT NULL,
  `esito` enum('IN_ATTESA','RIFIUTATO','ACCETTATO') NOT NULL DEFAULT 'IN_ATTESA',
  PRIMARY KEY (`IdEvento`,`IdMusicista`),
  CONSTRAINT `ChiaveEst` FOREIGN KEY (`IdEvento`) REFERENCES `eventi` (`IdEvento`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `ChiaveEst2` FOREIGN KEY (`IdMusicista`) REFERENCES `musicisti` (`IdMusicista`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `strumentimusicisti`
--

CREATE TABLE `strumentimusicisti` (
  `IdMusicista` int(10) UNSIGNED NOT NULL,
  `Strumento` varchar(30) NOT NULL,
   PRIMARY KEY (`IdMusicista`,`Strumento`),
   CONSTRAINT `ChiaveExt4` FOREIGN KEY (`IdMusicista`) REFERENCES `musicisti` (`IdMusicista`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--

--
-- Dumping data for table `musicisti`
--

INSERT INTO `musicisti` (`IdMusicista`, `Nome`, `Cognome`, `Email`, `Password`, `Specializzazione`, `Cachet`, `Telefono`) VALUES
(1, 'Mario', 'Rossi', 'mario.rossi@example.com', 'a', 'Strumentista', 5, 3331112233),
(2, 'Luigi', 'Verdi', 'luigi.verdi@example.com', 'v', 'Strumentista', 57, 3334445566),
(3, 'Anna', 'Bianchi', 'anna.bianchi@example.com', 'g', 'Cantante', 3, 3337778899);

--
-- Dumping data for table `strumentimusicisti`
--

INSERT INTO `strumentimusicisti` (`IdMusicista`, `Strumento`) VALUES
(1, 'Chitarra'),
(1, 'Violino'),
(2, 'Flauto');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
