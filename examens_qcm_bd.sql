-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 14, 2026 at 10:45 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `examens_qcm_bd`
--

-- --------------------------------------------------------

--
-- Table structure for table `choix`
--

CREATE TABLE `choix` (
  `id` int(11) NOT NULL,
  `id_question` int(11) NOT NULL,
  `texte_choix` text NOT NULL,
  `est_correct` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `choix`
--

INSERT INTO `choix` (`id`, `id_question`, `texte_choix`, `est_correct`) VALUES
(1, 1, 'JFrame', 1),
(2, 1, 'JWindowBox', 0),
(3, 1, 'SwingBox', 0),
(4, 1, 'JPanel', 0),
(5, 2, 'BorderLayout', 0),
(6, 2, 'FlowLayout', 0),
(7, 2, 'GridLayout', 1),
(8, 3, 'executeUpdate()', 0),
(9, 3, 'executeQuery()', 1),
(10, 3, 'executeSelect()', 0),
(11, 4, 'HTTP', 0),
(12, 4, 'FTP', 1),
(13, 4, 'SMTP', 0),
(14, 4, 'DNS', 0),
(15, 5, 'Centralisée', 1),
(16, 5, 'Distribuée (Peer-to-Peer)', 0),
(17, 6, 'Very High Description Language', 0),
(18, 6, 'VHSIC Hardware Description Language', 1),
(19, 6, 'Virtual Hardware Logic', 0),
(20, 7, 'Un Multiplexeur', 0),
(21, 7, 'Une Bascule (Flip-Flop)', 1),
(22, 7, 'Une porte AND', 0),
(23, 8, '1', 0),
(24, 8, '0', 0),
(25, 8, 'X', 1),
(32, 11, 'She don’t like coffee.', 0),
(33, 11, 'She doesn’t likes coffee.', 0),
(34, 11, 'She doesn’t like coffee.', 1),
(35, 12, 'goed', 0),
(36, 12, 'goes', 0),
(37, 12, 'went', 1),
(38, 13, 'expensive', 0),
(39, 13, 'trustworthy', 1),
(40, 13, 'fast', 0),
(41, 13, 'dangerous', 0),
(42, 14, 'in', 1),
(43, 14, 'on', 0),
(44, 14, 'at', 0),
(45, 14, 'to', 0),
(46, 15, 'Where you are going?', 1),
(47, 15, 'Where are you going?', 0),
(48, 15, 'C. Where going are you?', 0),
(49, 15, 'D. Where you going are?', 0),
(50, 16, 'childs', 0),
(51, 16, 'childrens', 1),
(52, 16, 'children', 0),
(53, 17, 'rain', 0),
(54, 17, 'rained', 0),
(55, 17, 'rains', 1),
(56, 17, 'raining', 0),
(57, 18, 'more easy', 0),
(58, 18, 'easier', 1),
(59, 18, 'most easy', 0),
(60, 18, 'not easy', 0),
(61, 18, 'less not easy', 0),
(62, 19, 'He has finished his work already.', 1),
(63, 19, 'He have finished his work already', 0),
(64, 20, 'Continue', 1),
(65, 20, 'Stop trying', 0),
(66, 20, 'Start again', 0),
(75, 25, 'working?', 1),
(76, 25, 'Not working?', 0),
(77, 25, 'IDK, I\'m blind', 1),
(78, 26, 'is it working?', 1),
(79, 26, 'is it not working?', 0),
(80, 26, 'idk', 0),
(81, 27, 'yes', 1),
(82, 27, 'no', 0),
(83, 27, 'idk', 0),
(84, 28, 'public static void main(String[] args)', 1),
(85, 28, 'public void main(String args)', 0),
(86, 28, 'static public int main(String[] args)', 0),
(87, 28, 'void main(String[] args)', 0),
(88, 29, 'Comparable', 1),
(89, 29, 'Comparator', 1),
(90, 29, 'Sortable', 0),
(91, 29, 'Orderable', 0),
(92, 30, 'La classe ne peut pas être instanciée', 0),
(93, 30, 'La classe ne peut pas être héritée', 1),
(94, 30, 'La classe est immuable', 0),
(95, 31, 'Le théorème ACID', 0),
(96, 31, 'Le théorème CAP', 1),
(97, 31, 'Le théorème de Bayes', 0),
(98, 32, 'Colonnes', 0),
(99, 32, 'Graphe', 0),
(100, 32, 'Documents', 1),
(101, 32, 'Clé-Valeur', 0),
(102, 33, 'Docker Compose', 0),
(103, 33, 'Kubernetes', 1),
(104, 33, 'Jenkins', 0),
(105, 34, 'Saga Pattern', 1),
(106, 34, 'Singleton Pattern', 0),
(107, 34, 'MVC Pattern', 0),
(108, 35, 'Distributed Denial of Service', 1),
(109, 35, 'Direct Domain Operating System', 0),
(110, 35, 'Data Destruction on Server', 0),
(111, 36, 'HTTP', 0),
(112, 36, 'FTP', 0),
(113, 36, 'HTTPS', 1),
(114, 36, 'SSH', 1),
(115, 37, 'Un fichier binaire (.exe)', 0),
(116, 37, 'Un fichier Bytecode (.class)', 1),
(117, 37, 'Un fichier assembleur (.asm)', 0),
(118, 38, 'public static void main(String[] args)', 1),
(119, 38, 'public void main(String args)', 0),
(120, 38, 'static void start()', 0),
(121, 39, 'int', 0),
(122, 39, 'boolean', 0),
(123, 39, 'String', 1),
(124, 39, 'double', 0),
(125, 40, 'implements', 0),
(126, 40, 'extends', 1),
(127, 40, 'inherits', 0),
(128, 41, 'Oui, sans limite', 1),
(129, 41, 'Non, une seule maximum', 0),
(130, 41, 'Oui, mais seulement deux', 0),
(131, 42, 'public', 0),
(132, 42, 'protected', 0),
(133, 42, 'private', 1),
(134, 43, 'for', 0),
(135, 43, 'while', 0),
(136, 43, 'do...while', 1),
(137, 44, 'Même nom de méthode, signatures différentes', 1),
(138, 44, 'Même signature, implémentation différente (héritage)', 0),
(139, 44, 'Avoir trop de variables', 0),
(140, 45, 'Il doit avoir le type void', 0),
(141, 45, 'Il porte le même nom que la classe', 1),
(142, 45, 'Il est obligatoire de l\'écrire manuellement', 0),
(143, 46, 'À chaque instance (objet)', 0),
(144, 46, 'À la classe elle-même', 1),
(145, 46, 'À la méthode main seulement', 0),
(146, 47, 'ArrayList', 0),
(147, 47, 'HashSet', 1),
(148, 47, 'LinkedList', 0),
(149, 48, 'final', 0),
(150, 48, 'finally', 1),
(151, 48, 'finish', 0),
(197, 65, 'java.lang', 0),
(198, 65, 'java.util', 1),
(199, 65, 'java.io', 0),
(200, 66, 'Oui', 0),
(201, 66, 'Non', 1),
(202, 66, 'Oui, si elle a un constructeur', 0),
(203, 67, 'finally', 1),
(204, 67, 'default', 0),
(205, 67, 'final', 0),
(206, 68, 'L\'opérateur ==', 0),
(207, 68, 'La méthode .equals()', 1),
(208, 68, 'La méthode .compare()', 0),
(209, 69, 'À appeler le constructeur de la classe parente', 1),
(210, 69, 'À créer une super classe', 0),
(211, 69, 'À déclarer une méthode supérieure', 0),
(212, 70, 'Runnable', 1),
(213, 70, 'Threadable', 0),
(214, 70, 'Executable', 0),
(215, 71, 'Compacter le code', 0),
(216, 71, 'Libérer la mémoire des objets inutilisés', 1),
(217, 71, 'Supprimer les virus', 0),
(218, 72, 'for', 0),
(219, 72, 'while', 0),
(220, 72, 'do-while', 1),
(221, 73, 'yes', 1),
(222, 73, 'no', 0),
(223, 74, 'HH', 0),
(224, 74, 'JJ', 1),
(225, 74, 'OO', 0),
(226, 75, 'JJ', 0),
(227, 75, 'U', 1);

-- --------------------------------------------------------

--
-- Table structure for table `etudiant`
--

CREATE TABLE `etudiant` (
  `id` int(11) NOT NULL,
  `nom_complet` varchar(100) NOT NULL,
  `matricule` varchar(255) DEFAULT NULL,
  `filiere` varchar(50) NOT NULL,
  `niveau` varchar(10) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `etudiant`
--

INSERT INTO `etudiant` (`id`, `nom_complet`, `matricule`, `filiere`, `niveau`, `email`, `password`) VALUES
(1, 'Ahmed Etudiant', 'H123456', 'Genie Info', 'M1', 'ahmed@test.com', 'pass'),
(2, 'Sara El Amrani', 'D130001', 'IESE', 'M1', 'sara.elamrani@usmba.ac.ma', 'pass'),
(3, 'Youssef Benali', 'D130002', 'IESE', 'M1', 'youssef.benali@usmba.ac.ma', 'pass'),
(4, 'Hiba Idrissi', 'D130003', 'IESE', 'M1', 'hiba.idrissi@usmba.ac.ma', 'pass'),
(5, 'Karim Tazi', 'D130004', 'IESE', 'M1', 'karim.tazi@usmba.ac.ma', 'pass'),
(6, 'Salma Bennani', 'D130005', 'IESE', 'M2', 'salma.bennani@usmba.ac.ma', 'pass'),
(7, 'Omar Fassi', 'D130006', 'IESE', 'M2', 'omar.fassi@usmba.ac.ma', 'pass'),
(8, 'Amina Chraibi', 'D130007', 'IESE', 'M2', 'amina.chraibi@usmba.ac.ma', 'pass'),
(9, 'Ahmed Etudiant', 'H123456', 'IESE', 'M1', 'ahmed.etudiant@usmba.ac.ma', '123456'),
(10, 'Sara Bennani', 'H654321', 'IESE', 'M2', 'sara.bennani@usmba.ac.ma', '123456'),
(11, 'Yassine Tazi', 'H987654', 'IESE', 'M1', 'yassine.tazi@usmba.ac.ma', '123456'),
(12, 'Mohammed Amansour', 'H112233', 'IESE', 'M1', 'mohammed.amansour@usmba.ac.ma', 'pass');

-- --------------------------------------------------------

--
-- Table structure for table `examen`
--

CREATE TABLE `examen` (
  `id` int(11) NOT NULL,
  `titre` varchar(200) NOT NULL,
  `filiere` varchar(50) NOT NULL,
  `niveau` varchar(10) NOT NULL,
  `id_prof` int(11) NOT NULL,
  `point_si_juste` double DEFAULT 1,
  `point_si_faux` double DEFAULT 0,
  `point_si_vide` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `examen`
--

INSERT INTO `examen` (`id`, `titre`, `filiere`, `niveau`, `id_prof`, `point_si_juste`, `point_si_faux`, `point_si_vide`) VALUES
(1, 'Java Avancé - Swing & JDBC', 'Genie Info', 'M1', 1, 2, -0.5, 0),
(2, 'Systèmes Distribués', 'Genie Info', 'M1', 1, 1, 0, 0),
(4, 'Électronique Numérique & VHDL', 'IESE', 'M1', 1, 2, -0.5, 0),
(6, 'ENGLISH EXAM', 'IESE', 'M1', 1, 1, 0, 0),
(9, 'EXAM for test', 'IESE', 'M1', 1, 1, 0, 0),
(10, 'Exam for test with pictures', 'IESE', 'M1', 1, 1, 0, 0),
(11, 'Programmation Java Avancée', 'IESE', 'M1', 2, 2, -0.5, 0),
(12, 'Bases de Données NoSQL', 'IESE', 'M2', 3, 1.5, -0.5, 0),
(13, 'Architecture Microservices', 'IESE', 'M2', 2, 1, 0, 0),
(14, 'Sécurité des Systèmes', 'IESE', 'M1', 4, 2, -1, 0),
(15, 'QCM Java Complet (20 Questions)', 'IESE', 'M1', 5, 1, -0.5, 0),
(16, 'Examen avec des photos', 'IESE', 'M1', 5, 1.5, -0.5, 0),
(17, 'JAVXXXX', 'IESE', 'M1', 5, 1.5, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `professeur`
--

CREATE TABLE `professeur` (
  `id` int(11) NOT NULL,
  `nom_complet` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `specialite` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `professeur`
--

INSERT INTO `professeur` (`id`, `nom_complet`, `email`, `password`, `specialite`) VALUES
(1, 'Dr. John Smith', 'prof@test.com', 'pass', 'Informatique'),
(2, 'Dr. Mohammed Alami', 'mohammed.alami@usmba.ac.ma', '123456', 'Génie Logiciel'),
(3, 'Dr. Fatima Idrissi', 'fatima.idrissi@usmba.ac.ma', '123456', 'Bases de Données & Big Data'),
(4, 'Dr. Karim Tahiri', 'karim.tahiri@usmba.ac.ma', '123456', 'Réseaux & Sécurité'),
(5, 'Younes Lakhrissi', 'younes.lakhrissi@usmba.ac.ma', 'pass', 'Informatique');

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `id_examen` int(11) NOT NULL,
  `enonce` text NOT NULL,
  `media` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`id`, `id_examen`, `enonce`, `media`) VALUES
(1, 1, 'Quelle classe est utilisée pour créer une fenêtre en Java Swing ?', NULL),
(2, 1, 'Quel Layout Manager place les composants en grille (lignes/colonnes) ?', NULL),
(3, 1, 'Quelle méthode exécute une requête SQL SELECT via JDBC ?', NULL),
(4, 2, 'Quel protocole est utilisé pour le transfert de fichiers ?', NULL),
(5, 2, 'Une architecture Client-Serveur est :', NULL),
(6, 4, 'Que signifie l\'acronyme VHDL ?', NULL),
(7, 4, 'Quel composant stocke un bit d\'information ?', NULL),
(8, 4, 'Quelle est la sortie de X OR X ?', NULL),
(11, 6, 'Choose the correct sentence: ', NULL),
(12, 6, 'Choose the correct past form of the verb go:', NULL),
(13, 6, 'What does the word “reliable” mean?', NULL),
(14, 6, 'Choose the correct preposition: I am interested ___ learning English. ', NULL),
(15, 6, 'Choose the correct question form:', NULL),
(16, 6, 'Choose the correct plural form:', NULL),
(17, 6, 'Complete the sentence: If it ___ tomorrow, we will stay at home. ', NULL),
(18, 6, 'Choose the correct adjective: This exercise is ___ than the last one. ', NULL),
(19, 6, 'Choose the correct sentence:', NULL),
(20, 6, 'Choose the correct meaning of “give up”: ', NULL),
(25, 9, 'TEST video', 'D:\\Mega\\Personal\\Documents\\The 5\\Simo\\Maghreb\\l\'étude\\0___FSTF\\0___MST IESE\\MST IESE - 1\\MST IESE 1 - Study Ressources\\S1\\Java et Bases de données\\Java_BD_Lakhrissi.mp4'),
(26, 10, 'is the file audible?', 'D:\\Mega\\Quran\\الحصري\\001.mp3'),
(27, 10, 'is picture visible?', 'D:\\Mega\\Windows\\Windows apps\\Desktop\\Hadana\\Bilal\\Vaccine.jpg'),
(28, 11, 'Quelle est la signature correcte de la méthode main ?', NULL),
(29, 11, 'Quelle interface est utilisée pour trier une liste d\'objets ?', NULL),
(30, 11, 'Le mot clé \"final\" sur une classe signifie que :', NULL),
(31, 12, 'Quel théorème stipule qu\'il est impossible d\'avoir simultanément Cohérence, Disponibilité et Tolérance au partitionnement ?', NULL),
(32, 12, 'MongoDB est une base de données orientée :', NULL),
(33, 13, 'Quel outil est souvent utilisé pour l\'orchestration de conteneurs ?', NULL),
(34, 13, 'Quel pattern permet de gérer les transactions distribuées ?', NULL),
(35, 14, 'Que signifie l\'attaque DDOS ?', NULL),
(36, 14, 'Quel protocole est sécurisé ?', NULL),
(37, 15, 'Quel est le résultat de la compilation d\'un fichier Java (.java) ?', NULL),
(38, 15, 'Quelle méthode est le point d\'entrée d\'une application Java ?', NULL),
(39, 15, 'Lequel de ces types n\'est PAS un type primitif en Java ?', NULL),
(40, 15, 'Quel mot-clé est utilisé pour l\'héritage de classe ?', NULL),
(41, 15, 'Une classe peut-elle implémenter plusieurs interfaces ?', NULL),
(42, 15, 'Quel modificateur rend un attribut accessible uniquement dans sa propre classe ?', NULL),
(43, 15, 'Quelle boucle garantit au moins une exécution du bloc de code ?', NULL),
(44, 15, 'Qu\'est-ce que la surcharge (Overloading) ?', NULL),
(45, 15, 'Quelle affirmation est vraie concernant le constructeur ?', NULL),
(46, 15, 'À quoi appartient une variable déclarée \"static\" ?', NULL),
(47, 15, 'Quelle collection n\'accepte pas les doublons ?', NULL),
(48, 15, 'Quel bloc est toujours exécuté après un try/catch (sauf arrêt brutal) ?', NULL),
(65, 15, 'ArrayList fait partie de quel package ?', NULL),
(66, 15, 'Peut-on instancier une classe abstraite ?', NULL),
(67, 15, 'Quel bloc est toujours exécuté après un try-catch ?', NULL),
(68, 15, 'Pour comparer le contenu de deux objets String, on utilise :', NULL),
(69, 15, 'À quoi sert le mot clé super() ?', NULL),
(70, 15, 'Quelle interface implémenter pour créer un Thread ?', NULL),
(71, 15, 'Quel est le rôle du Garbage Collector ?', NULL),
(72, 15, 'Quelle boucle s\'exécute toujours au moins une fois ?', NULL),
(73, 16, 'can you this picture?', 'D:\\Mega\\Windows\\Windows apps\\Desktop\\Hadana\\Bilal\\Vaccine.jpg'),
(74, 17, 'BB', 'D:\\Mega\\Windows\\Windows apps\\Desktop\\Hadana\\Bilal\\Vaccine.jpg'),
(75, 17, 'GG', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `resultat`
--

CREATE TABLE `resultat` (
  `id` int(11) NOT NULL,
  `id_etudiant` int(11) NOT NULL,
  `id_examen` int(11) NOT NULL,
  `note_sur_20` double NOT NULL,
  `date_passage` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `resultat`
--

INSERT INTO `resultat` (`id`, `id_etudiant`, `id_examen`, `note_sur_20`, `date_passage`) VALUES
(11, 5, 6, 20, '2026-01-12 22:31:40'),
(12, 11, 16, 20, '2026-01-13 10:26:12'),
(13, 11, 15, 12.5, '2026-01-13 10:30:37'),
(14, 11, 10, 10, '2026-01-13 12:35:52'),
(15, 12, 14, 13.333333333333332, '2026-01-13 13:13:09'),
(16, 12, 9, 10, '2026-01-13 13:13:34'),
(17, 12, 17, 10, '2026-01-13 15:22:24');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `choix`
--
ALTER TABLE `choix`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_question` (`id_question`);

--
-- Indexes for table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `examen`
--
ALTER TABLE `examen`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_prof` (`id_prof`);

--
-- Indexes for table `professeur`
--
ALTER TABLE `professeur`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_question_examen` (`id_examen`);

--
-- Indexes for table `resultat`
--
ALTER TABLE `resultat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_etudiant` (`id_etudiant`),
  ADD KEY `id_examen` (`id_examen`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `choix`
--
ALTER TABLE `choix`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=228;

--
-- AUTO_INCREMENT for table `etudiant`
--
ALTER TABLE `etudiant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `examen`
--
ALTER TABLE `examen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `professeur`
--
ALTER TABLE `professeur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `question`
--
ALTER TABLE `question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- AUTO_INCREMENT for table `resultat`
--
ALTER TABLE `resultat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `choix`
--
ALTER TABLE `choix`
  ADD CONSTRAINT `choix_ibfk_1` FOREIGN KEY (`id_question`) REFERENCES `question` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `examen`
--
ALTER TABLE `examen`
  ADD CONSTRAINT `examen_ibfk_1` FOREIGN KEY (`id_prof`) REFERENCES `professeur` (`id`);

--
-- Constraints for table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `fk_question_examen` FOREIGN KEY (`id_examen`) REFERENCES `examen` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `resultat`
--
ALTER TABLE `resultat`
  ADD CONSTRAINT `resultat_ibfk_1` FOREIGN KEY (`id_etudiant`) REFERENCES `etudiant` (`id`),
  ADD CONSTRAINT `resultat_ibfk_2` FOREIGN KEY (`id_examen`) REFERENCES `examen` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
