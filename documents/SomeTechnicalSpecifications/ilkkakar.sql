-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: mysql.metropolia.fi
-- Generation Time: Oct 09, 2023 at 03:39 AM
-- Server version: 10.5.22-MariaDB
-- PHP Version: 8.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ilkkakar`
--

-- --------------------------------------------------------

--
-- Table structure for table `Devicedata`
--

CREATE TABLE `Devicedata` (
  `DataID` int(11) NOT NULL,
  `DeviceID` int(11) NOT NULL,
  `Timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `Data` varchar(255) NOT NULL,
  `DataArb` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Devices`
--

CREATE TABLE `Devices` (
  `DeviceID` int(11) NOT NULL,
  `DeviceName` varchar(64) NOT NULL,
  `DeviceDesc` varchar(255) DEFAULT NULL,
  `IsFavorite` tinyint(1) NOT NULL,
  `UserID` int(11) NOT NULL,
  `DeviceControl` varchar(255) NOT NULL,
  `Format` varchar(255) NOT NULL,
  `Unit` varchar(255) DEFAULT NULL,
  `ControlTimestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` int(11) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Password` tinyblob NOT NULL,
  `salt` tinyblob NOT NULL,
  `Security1` text DEFAULT NULL,
  `Security2` text DEFAULT NULL,
  `Security3` text DEFAULT NULL,
  `SecurityA1` tinyblob DEFAULT NULL,
  `SecurityA2` tinyblob DEFAULT NULL,
  `SecurityA3` tinyblob DEFAULT NULL,
  `SecASalt1` tinyblob DEFAULT NULL,
  `SecASalt2` tinyblob DEFAULT NULL,
  `SecASalt3` tinyblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Devicedata`
--
ALTER TABLE `Devicedata`
  ADD PRIMARY KEY (`DataID`),
  ADD KEY `Data_device` (`DeviceID`);

--
-- Indexes for table `Devices`
--
ALTER TABLE `Devices`
  ADD PRIMARY KEY (`DeviceID`),
  ADD KEY `Device_User` (`UserID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Devicedata`
--
ALTER TABLE `Devicedata`
  MODIFY `DataID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Devices`
--
ALTER TABLE `Devices`
  MODIFY `DeviceID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Devicedata`
--
ALTER TABLE `Devicedata`
  ADD CONSTRAINT `Data_device` FOREIGN KEY (`DeviceID`) REFERENCES `Devices` (`DeviceID`);

--
-- Constraints for table `Devices`
--
ALTER TABLE `Devices`
  ADD CONSTRAINT `Device_User` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
