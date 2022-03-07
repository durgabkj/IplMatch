-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 03, 2022 at 06:04 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ipl`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(25) NOT NULL,
  `password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `name`, `email`, `password`) VALUES
(1, 'Ramesh', 'ramesh@gmail.com', 'hey123'),
(2, 'Bharat', 'bharat@gmail.com', 'hi123');

-- --------------------------------------------------------

--
-- Table structure for table `matches`
--

CREATE TABLE `matches` (
  `id` int(11) NOT NULL,
  `time` time NOT NULL,
  `status` int(2) NOT NULL,
  `active` int(2) NOT NULL,
  `date` date NOT NULL,
  `location` varchar(100) NOT NULL,
  `team1_id` int(2) NOT NULL,
  `team2_id` int(2) NOT NULL,
  `winner_id` int(2) DEFAULT NULL,
  `looser_id` int(11) NOT NULL,
  `winner_run` text NOT NULL,
  `looser_run` text NOT NULL,
  `winner_over` decimal(10,1) NOT NULL,
  `looser_over` decimal(10,1) NOT NULL,
  `winner_wicket` text NOT NULL,
  `looser_wicket` text NOT NULL,
  `description` varchar(100) NOT NULL,
  `live` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `matches`
--

INSERT INTO `matches` (`id`, `time`, `status`, `active`, `date`, `location`, `team1_id`, `team2_id`, `winner_id`, `looser_id`, `winner_run`, `looser_run`, `winner_over`, `looser_over`, `winner_wicket`, `looser_wicket`, `description`, `live`, `created`, `updated`) VALUES
(1, '21:38:00', 1, 0, '2022-02-26', 'patna', 6, 3, 3, 6, '190', '182', '19.4', '20.0', '5', '7', 'RCB won by 5 Wicket', 0, '2010-02-22 09:36:44', '2022-02-22 06:37:30'),
(2, '21:42:00', 1, 0, '2022-02-26', 'Delhi', 6, 4, 4, 6, '167', '154', '20.0', '18.5', '8', '10', 'SRH  won by 13 Run', 0, '2010-02-22 09:38:06', '2022-02-22 06:40:05'),
(3, '02:44:00', 1, 0, '2022-02-28', 'Kolkata', 1, 3, 3, 1, '197', '145', '20.0', '20.0', '4', '9', '', 0, '2010-02-22 09:40:50', '2022-02-22 05:10:50'),
(4, '21:44:00', 1, 0, '2022-02-17', 'Banglore', 9, 1, 3, 1, '201', '199', '20.0', '20.0', '3', '5', '', 0, '2010-02-22 09:41:53', '2022-02-22 05:10:56'),
(5, '07:30:00', 1, 0, '2022-01-05', 'Delhi', 6, 1, 1, 6, '234', '220', '20.0', '18.2', '7', '9', '', 1, '2022-02-19 18:49:18', '2022-03-02 09:48:36'),
(6, '15:02:13', 1, 1, '2022-02-20', 'Bengaluru', 1, 7, 1, 7, '178', '167', '20.0', '20.0', '3', '7', '', 0, '2022-02-19 18:50:13', '2022-03-02 12:09:45');

-- --------------------------------------------------------

--
-- Table structure for table `news`
--

CREATE TABLE `news` (
  `id` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `description` text NOT NULL,
  `date` date DEFAULT NULL,
  `photo` varchar(1000) NOT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `news`
--

INSERT INTO `news` (`id`, `title`, `description`, `date`, `photo`, `created`, `updated`) VALUES
(1, 'Faf du Plessis Wanted to Show Cricket South Africa How Good He Was After T20 World Cup Snub', 'South Africa’s Faf du Plessis maybe 37 years old, but he continues to be a hit when it comes scoring the runs. He was the second-highest run-scorer in IPL 2021 and went onto top score in the final as CSK beat KKR. Despite his best form, he wasn’t picked in South Africa’s ICC T20 World Cup squad. Speaking to Paddy Upton on his podcast, he revealed how he wanted to show CSA that he was still good to play international level, adding that his goal was to finish in the top three at IPL.\r\n\r\n“I have a good batting voice and a bad batting voice. To give you a practical example of this voice, in this last IPL, I had a fantastic IPL, to the point where the goals I set for myself, which were to be in the top three [run-scorers] right from the beginning, I got along to this goal pretty well,” Faf Du Plessis told Paddy Upton on the ‘Lessons from the World’s Best’ podcast.', '2022-02-03', 'https://images.news18.com/ibnlive/uploads/2021/10/faf-du-plessis-1-16343636753x2.jpg?impolicy=website&width=510&height=356', '2022-01-30 12:17:25', '2022-02-04 15:26:34');

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE `player` (
  `id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `player_name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`id`, `team_id`, `player_name`) VALUES
(3, 7, 'Shikhar Dhawan (BatsMan)'),
(4, 1, 'Rahul Chahar (Bowler)'),
(5, 1, 'Dhoni(caption)'),
(6, 7, 'Ambati Rayedu');

-- --------------------------------------------------------

--
-- Table structure for table `point_table`
--

CREATE TABLE `point_table` (
  `id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `matchs` int(11) NOT NULL,
  `won` int(11) NOT NULL,
  `loss` int(11) NOT NULL,
  `tie` int(11) NOT NULL,
  `pts` int(11) NOT NULL,
  `NRR` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `teams`
--

CREATE TABLE `teams` (
  `id` int(11) NOT NULL,
  `team_name` varchar(30) NOT NULL,
  `short_name` varchar(10) NOT NULL,
  `team_logo` text NOT NULL,
  `team_desc` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `teams`
--

INSERT INTO `teams` (`id`, `team_name`, `short_name`, `team_logo`, `team_desc`) VALUES
(1, 'chennai super king', 'Csk', 'https://www.pngall.com/wp-content/uploads/2017/04/Chennai-Super-Kings-Logo-PNG.png', 'The Chennai Super Kings (CSK) are a franchise cricket team based in Chennai, Tamil Nadu. '),
(3, 'Royal Challengers Bangalore', 'RCB', 'https://www.kindpng.com/picc/m/489-4898659_royal-challengers-bangalore-rcb-logo-royal-challengers-bangalore.png', 'The Royal Challengers Bangalore (often abbreviated as RCB) are a franchise cricket team based in Bangalore, Karnataka, that plays in the Indian Premier League (IPL). It was founded in 2008 by United Spirits and named after the company\'s liquor brand Royal Challenge. Since its inception, the team has played its home matches at the M. Chinnaswamy Stadium.[2]'),
(4, 'Sunrisers Hyderabad', 'SRH', 'https://www.santabanta.com/images/wallpapers/sports/the-official-logo-of-ipl-team-sunrisers-hyderabad_1024-768.jpg', 'The Sunrisers Hyderabad are a franchise cricket team based in Hyderabad, Telangana, India, that plays in the Indian Premier League.'),
(5, 'Kolkata Knight Riders', 'KKR', 'https://www.mbaskool.com/2015_images/stories/apr-images/ipl-logos01.jpg', 'The Kolkata Knight Riders are a franchise cricket team representing the city of Kolkata in the Indian Premier League.'),
(6, 'Delhi Capitals', 'DC', 'data:image/png;base64,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', 'The Delhi Capitals are a franchise cricket team based out of Delhi in the Indian Premier League., the franchise is jointly owned by the GMR Group and the JSW Group. The team\'s home ground is Arun Jaitley Stadium, located in New Delhi. The Capitals appeared in their first IPL final in 2020'),
(7, 'Punjab Kings', 'KXIP', 'https://upload.wikimedia.org/wikipedia/en/1/1c/Punjab_Kings_logo_2021.png', 'The Punjab Kings are a franchise cricket team based in Mohali, Punjab, that plays in the Indian Premier League. Established in 2008 as the Kings XI Punjab, the franchise is jointly owned by Mohit Burman, Ness Wadia, Preity Zinta and Karan Paul. The team plays its home matches at the PCA Stadium, Mohali.'),
(9, 'Mumbai Indians', 'MI', 'http://localhost/ipladmin/assets/images/teams-logo/62041aea0fdfb.png', 'The Mumbai Indians are a franchise cricket team based in Mumbai, Maharashtra, that competes in the Indian Premier League.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `matches`
--
ALTER TABLE `matches`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `point_table`
--
ALTER TABLE `point_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `teams`
--
ALTER TABLE `teams`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `matches`
--
ALTER TABLE `matches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `news`
--
ALTER TABLE `news`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `player`
--
ALTER TABLE `player`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `point_table`
--
ALTER TABLE `point_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `teams`
--
ALTER TABLE `teams`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
