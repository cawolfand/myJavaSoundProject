-- phpMyAdmin SQL Dump
-- version 4.0.10
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 27, 2014 at 12:41 AM
-- Server version: 5.6.11
-- PHP Version: 5.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mycontactdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `contacts`
--

CREATE TABLE IF NOT EXISTS `library` (
  `ContactID` INTEGER PRIMARY KEY AUTOINCREMENT,
  `album` varchar(100) NOT NULL,
  `artist` varchar(50) DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `tracknum` varchar(50) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
    
) ;

--
-- Dumping data for table `contacts`
--

INSERT INTO `contacts` ( `firstName`, `middleName`, `lastName`, `Address1`, `Address2`, `City`, `State`, `Zip`, `Phone1`, `Phone2`, `Email1`, `Email2`) VALUES
( 'Carol', 'Ann', 'Wolf', '34965 NW Willis Ln', '', 'North Plains', 'OR', '97133', '5036470349', '5038078180', 'joelandcarol@yahoo.com', 'cawolf@live.com'),
( 'Joel', 'David', 'Birkeland', '34965 NW Willis Ln', '', 'North Plains', 'OR', '97133', '5036470349', '5038036671', 'joelandcarol@yahoo.com', 'joel.birkeland@mxim.com'),
( 'Evan', 'Alexander', 'Birkeland', '626 SW Sherman Drive', '', 'Portland', 'OR', '97204', '5032779361', '', 'evan.birkeland@yahoo.com', ''),
( 'Alana', 'Marie', 'Birkeland', '34965 NW Willis Ln', '', 'North Plains', 'OR', '97133', '5032779362', '', 'alana.bean@gmail.com', ''),
( 'Nancy', 'Lee', 'Files', '1011 Morgan Ln', '', 'Perkasie', 'PA', '18944', '2023351523', '', 'files5@verizon.net', ''),
( 'Karen', 'Ann', 'Funk', '12354 NW Shipley Dr.', '', 'North Plains', 'OR', '97133', '5031225563', '5036477652', 'northplains@coho.net', ''),
( 'Tucker', '', 'Alley', '21324 NW Green Mountain Road', '', 'Banks', 'OR', '97133', '5032264595', 'null', 'tucker.alley@yahoo.com', 'null'),
( 'Wayne', 'Waldean', 'Wolf', '3456 Villa Marie Dr', 'Apt 304', 'Bryan', 'TX', '77807', '8415552635', '', 'wwwolf@verizon.com', ''),
( 'Helen', 'Jean', 'Wolf', '5462 Villa Marie Rd', '', 'Bryan', 'TX', '77807', '544545455', '', '', ''),
( 'Ruby', '', 'Bracken', '34956 NW Willis Ln', '', 'North Plains', 'OR', '97133', '5036475465', '', '', ''),
( 'Chris', '', 'Lantz', '54621 NW Old Pumpkin Ridge Rd', '', 'North Plains', 'OR', '97133', '1523546236', '', '', ''),
( 'Ann', 'null', 'Gabel', '23453 NW 1st Ave', '#434', 'NP', 'OR', '345434', '', 'null', 'wert@fer.com', 'null'),
( 'abb', 'null', 'Gabes', 'asdf', 'asdf', 'we', 'ds', '32', '', 'null', '', 'null');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
