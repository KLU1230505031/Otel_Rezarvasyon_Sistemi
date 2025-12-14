-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1:3306
-- Üretim Zamanı: 13 Ara 2025, 12:42:45
-- Sunucu sürümü: 9.1.0
-- PHP Sürümü: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `otel_db`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `reservations`
--

DROP TABLE IF EXISTS `reservations`;
CREATE TABLE IF NOT EXISTS `reservations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tc_no` varchar(11) DEFAULT NULL,
  `oda_no` int DEFAULT NULL,
  `giris_tarihi` date DEFAULT NULL,
  `cikis_tarihi` date DEFAULT NULL,
  `ucret` double DEFAULT NULL,
  `durum` varchar(20) DEFAULT 'AKTIF',
  `odeme_durumu` varchar(20) DEFAULT 'BEKLIYOR',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Tablo döküm verisi `reservations`
--

INSERT INTO `reservations` (`id`, `tc_no`, `oda_no`, `giris_tarihi`, `cikis_tarihi`, `ucret`, `durum`, `odeme_durumu`) VALUES
(1, '31463722732', 136, '2025-12-12', '2025-12-17', 2500, 'TAMAMLANDI', 'ODENDI'),
(2, '75499524990', 135, '2025-12-28', '2025-12-31', 3000, 'IPTAL', 'ODENDI'),
(3, '34520208514', 126, '2026-01-21', '2026-01-23', 1000, 'AKTIF', 'ODENDI'),
(4, '60671126863', 113, '2025-11-18', '2025-11-20', 3000, 'AKTIF', 'ODENDI'),
(5, '75499524990', 125, '2025-11-12', '2025-11-13', 500, 'IPTAL', 'ODENDI'),
(6, '49824157712', 124, '2025-11-08', '2025-11-12', 6000, 'TAMAMLANDI', 'BEKLIYOR'),
(7, '39553704744', 129, '2026-01-28', '2026-01-29', 500, 'TAMAMLANDI', 'ODENDI'),
(8, '74767878328', 146, '2025-11-10', '2025-11-14', 6000, 'AKTIF', 'ODENDI'),
(9, '33660046983', 108, '2026-01-19', '2026-01-26', 10500, 'AKTIF', 'BEKLIYOR'),
(10, '54511416959', 142, '2025-11-12', '2025-11-17', 2500, 'IPTAL', 'ODENDI'),
(11, '19154600068', 107, '2026-01-23', '2026-01-27', 2000, 'IPTAL', 'ODENDI'),
(12, '73065341947', 101, '2026-01-18', '2026-01-20', 1000, 'IPTAL', 'ODENDI'),
(13, '99347048679', 109, '2025-12-18', '2025-12-19', 1500, 'AKTIF', 'ODENDI'),
(14, '73065341947', 145, '2025-12-04', '2025-12-08', 4000, 'AKTIF', 'ODENDI'),
(15, '17187806698', 145, '2025-11-08', '2025-11-15', 7000, 'IPTAL', 'BEKLIYOR'),
(16, '99727946203', 109, '2025-12-07', '2025-12-11', 6000, 'IPTAL', 'ODENDI'),
(17, '43902700137', 122, '2025-11-11', '2025-11-14', 1500, 'AKTIF', 'ODENDI'),
(18, '54511416959', 140, '2025-11-09', '2025-11-15', 6000, 'AKTIF', 'ODENDI'),
(19, '99605427898', 109, '2025-11-26', '2025-11-28', 3000, 'IPTAL', 'ODENDI'),
(20, '73952875449', 107, '2025-12-14', '2025-12-19', 2500, 'TAMAMLANDI', 'BEKLIYOR'),
(21, '43674875385', 123, '2026-01-30', '2026-02-01', 2000, 'AKTIF', 'ODENDI'),
(22, '33660046983', 113, '2026-01-20', '2026-01-25', 7500, 'AKTIF', 'ODENDI'),
(23, '99605427898', 127, '2026-01-14', '2026-01-20', 3000, 'AKTIF', 'ODENDI'),
(24, '98183742034', 138, '2025-12-13', '2025-12-18', 2500, 'AKTIF', 'BEKLIYOR'),
(25, '24044905727', 102, '2025-12-06', '2025-12-08', 3000, 'AKTIF', 'ODENDI'),
(26, '43674875385', 119, '2026-02-03', '2026-02-05', 2000, 'AKTIF', 'BEKLIYOR'),
(27, '43902700137', 110, '2026-01-17', '2026-01-21', 2000, 'AKTIF', 'ODENDI'),
(28, '79717030280', 130, '2026-01-05', '2026-01-12', 10500, 'AKTIF', 'ODENDI'),
(29, '43902700137', 110, '2026-01-27', '2026-02-01', 2500, 'AKTIF', 'BEKLIYOR'),
(30, '99605427898', 146, '2026-01-14', '2026-01-18', 6000, 'AKTIF', 'ODENDI'),
(31, '99605427898', 136, '2025-11-27', '2025-11-29', 1000, 'IPTAL', 'ODENDI'),
(32, '68992691062', 118, '2025-12-22', '2025-12-26', 2000, 'AKTIF', 'ODENDI'),
(33, '60671126863', 122, '2025-12-05', '2025-12-12', 3500, 'AKTIF', 'ODENDI'),
(34, '22307518031', 134, '2025-11-23', '2025-11-25', 3000, 'AKTIF', 'ODENDI'),
(35, '99727946203', 113, '2026-01-17', '2026-01-20', 4500, 'AKTIF', 'ODENDI'),
(36, '73858177570', 134, '2025-11-10', '2025-11-13', 4500, 'AKTIF', 'ODENDI'),
(37, '19154600068', 103, '2025-12-08', '2025-12-14', 9000, 'AKTIF', 'ODENDI'),
(38, '33365086286', 104, '2025-12-26', '2025-12-29', 1500, 'AKTIF', 'ODENDI'),
(39, '46911121183', 104, '2025-11-23', '2025-11-25', 1000, 'TAMAMLANDI', 'BEKLIYOR'),
(40, '80387776746', 127, '2025-12-04', '2025-12-05', 500, 'AKTIF', 'BEKLIYOR'),
(41, '46911121183', 113, '2025-12-15', '2025-12-20', 7500, 'AKTIF', 'ODENDI'),
(42, '24044905727', 128, '2025-11-11', '2025-11-13', 2000, 'AKTIF', 'BEKLIYOR'),
(43, '41892424179', 111, '2026-01-26', '2026-01-30', 4000, 'AKTIF', 'ODENDI'),
(44, '43674875385', 144, '2026-01-21', '2026-01-26', 7500, 'TAMAMLANDI', 'BEKLIYOR'),
(45, '73065341947', 140, '2025-12-27', '2026-01-02', 6000, 'AKTIF', 'ODENDI'),
(46, '52069258652', 106, '2025-12-08', '2025-12-09', 1000, 'IPTAL', 'ODENDI'),
(47, '99913984686', 147, '2025-12-16', '2025-12-17', 1500, 'AKTIF', 'BEKLIYOR'),
(48, '23921792254', 117, '2025-11-23', '2025-11-29', 6000, 'TAMAMLANDI', 'BEKLIYOR'),
(49, '99913984686', 127, '2026-01-25', '2026-01-29', 2000, 'AKTIF', 'BEKLIYOR'),
(50, '83033160191', 117, '2025-12-13', '2025-12-19', 6000, 'IPTAL', 'ODENDI'),
(51, '10000000000', 102, '2025-12-09', '2025-12-10', 1500, 'IPTAL', 'BEKLIYOR'),
(52, '10000000000', 107, '2025-12-09', '2025-12-10', 500, 'AKTIF', 'BEKLIYOR'),
(53, '10000000000', 101, '2025-12-09', '2025-12-12', 1500, 'AKTIF', 'ODENDI'),
(54, '10000000000', 113, '2025-12-10', '2025-12-11', 1500, 'AKTIF', 'ODENDI'),
(55, '10000000000', 102, '2025-12-10', '2025-12-11', 1500, 'AKTIF', 'BEKLIYOR'),
(56, '10000000000', 104, '2025-12-12', '2025-12-20', 4000, 'AKTIF', 'BEKLIYOR');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `rooms`
--

DROP TABLE IF EXISTS `rooms`;
CREATE TABLE IF NOT EXISTS `rooms` (
  `oda_no` int NOT NULL,
  `tip` varchar(20) NOT NULL,
  `fiyat` double NOT NULL,
  `durum` varchar(20) DEFAULT 'MUSAIT',
  `kapasite` int DEFAULT '2',
  PRIMARY KEY (`oda_no`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Tablo döküm verisi `rooms`
--

INSERT INTO `rooms` (`oda_no`, `tip`, `fiyat`, `durum`, `kapasite`) VALUES
(101, 'STANDART', 500, 'DOLU', 2),
(102, 'SUIT', 1500, 'REZERVE', 4),
(103, 'SUIT', 1500, 'REZERVE', 4),
(104, 'STANDART', 500, 'DOLU', 2),
(105, 'AILE', 1000, 'REZERVE', 3),
(106, 'AILE', 1000, 'REZERVE', 3),
(107, 'STANDART', 500, 'REZERVE', 2),
(108, 'SUIT', 1500, 'MUSAIT', 4),
(109, 'SUIT', 1500, 'MUSAIT', 4),
(111, 'AILE', 1000, 'REZERVE', 3),
(112, 'SUIT', 1500, 'REZERVE', 4),
(113, 'SUIT', 1500, 'REZERVE', 4),
(114, 'AILE', 1000, 'REZERVE', 3),
(115, 'SUIT', 1500, 'DOLU', 4),
(116, 'SUIT', 1500, 'DOLU', 4),
(117, 'AILE', 1000, 'MUSAIT', 3),
(118, 'STANDART', 500, 'MUSAIT', 2),
(119, 'AILE', 1000, 'REZERVE', 3),
(120, 'STANDART', 500, 'MUSAIT', 2),
(121, 'SUIT', 1500, 'MUSAIT', 4),
(122, 'STANDART', 500, 'MUSAIT', 2),
(123, 'AILE', 1000, 'REZERVE', 3),
(124, 'SUIT', 1500, 'DOLU', 4),
(125, 'STANDART', 500, 'REZERVE', 2),
(126, 'STANDART', 500, 'MUSAIT', 2),
(127, 'STANDART', 500, 'REZERVE', 2),
(128, 'AILE', 1000, 'DOLU', 3),
(129, 'STANDART', 500, 'MUSAIT', 2),
(130, 'SUIT', 1500, 'MUSAIT', 4),
(131, 'STANDART', 500, 'REZERVE', 2),
(132, 'SUIT', 1500, 'MUSAIT', 4),
(133, 'SUIT', 1500, 'MUSAIT', 4),
(134, 'SUIT', 1500, 'DOLU', 4),
(135, 'AILE', 1000, 'REZERVE', 3),
(136, 'STANDART', 500, 'REZERVE', 2),
(137, 'STANDART', 500, 'MUSAIT', 2),
(138, 'STANDART', 500, 'REZERVE', 2),
(139, 'STANDART', 500, 'MUSAIT', 2),
(140, 'AILE', 1000, 'MUSAIT', 3),
(141, 'AILE', 1000, 'REZERVE', 3),
(142, 'STANDART', 500, 'REZERVE', 2),
(143, 'STANDART', 500, 'DOLU', 2),
(144, 'SUIT', 1500, 'MUSAIT', 4),
(145, 'AILE', 1000, 'MUSAIT', 3),
(146, 'SUIT', 1500, 'REZERVE', 4),
(147, 'SUIT', 1500, 'MUSAIT', 4),
(148, 'SUIT', 1500, 'MUSAIT', 4),
(149, 'STANDART', 500, 'MUSAIT', 2),
(150, 'AILE', 1000, 'REZERVE', 3),
(401, 'EKONOMIK', 350, 'MUSAIT', 1),
(402, 'EKONOMIK', 350, 'MUSAIT', 1),
(403, 'EKONOMIK', 350, 'DOLU', 1),
(501, 'DELUXE', 850, 'MUSAIT', 2),
(502, 'DELUXE', 850, 'REZERVE', 2),
(503, 'DELUXE', 850, 'MUSAIT', 2),
(601, 'KRAL', 5000, 'MUSAIT', 6),
(602, 'KRAL', 5000, 'MUSAIT', 6);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tc_no` varchar(11) NOT NULL,
  `ad_soyad` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `sifre` varchar(50) NOT NULL,
  `rol` enum('MUSTERI','PERSONEL') NOT NULL,
  `telefon` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tc_no` (`tc_no`)
) ENGINE=MyISAM AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Tablo döküm verisi `users`
--

INSERT INTO `users` (`id`, `tc_no`, `ad_soyad`, `email`, `sifre`, `rol`, `telefon`) VALUES
(1, '83033160191', 'Ebru Kara', 'ebru.kara0@gmail.com', '1234', 'MUSTERI', '5409947269'),
(2, '74767878328', 'Ahmet Doğan', 'ahmet.doğan1@gmail.com', '1234', 'MUSTERI', '5505088909'),
(3, '75499524990', 'Ayşe Özdemir', 'ayşe.özdemir2@gmail.com', '1234', 'MUSTERI', '5333995136'),
(4, '48379070706', 'Ayşe Doğan', 'ayşe.doğan3@gmail.com', '1234', 'MUSTERI', '5458124648'),
(5, '98183742034', 'Gamze Aslan', 'gamze.aslan4@gmail.com', '1234', 'MUSTERI', '5395710501'),
(6, '31463722732', 'Zeynep Arslan', 'zeynep.arslan5@gmail.com', '1234', 'MUSTERI', '5497578388'),
(7, '60810005723', 'Deniz Koç', 'deniz.koç6@gmail.com', '1234', 'MUSTERI', '5506744922'),
(8, '22302514990', 'Selin Kılıç', 'selin.kılıç7@gmail.com', '1234', 'MUSTERI', '5402456110'),
(9, '99913984686', 'Mehmet Şahin', 'mehmet.şahin8@gmail.com', '1234', 'MUSTERI', '5462648379'),
(10, '99347048679', 'Emre Çelik', 'emre.çelik9@gmail.com', '1234', 'MUSTERI', '5486262819'),
(11, '36111822861', 'Gamze Koç', 'gamze.koç10@gmail.com', '1234', 'MUSTERI', '5321197983'),
(12, '22307518031', 'Gamze Kılıç', 'gamze.kılıç11@gmail.com', '1234', 'MUSTERI', '5445016269'),
(13, '30091199369', 'Gamze Kaya', 'gamze.kaya12@gmail.com', '1234', 'MUSTERI', '5456419987'),
(14, '79717030280', 'Onur Kaya', 'onur.kaya13@gmail.com', '1234', 'MUSTERI', '5322932832'),
(15, '33365086286', 'Selin Yıldız', 'selin.yıldız14@gmail.com', '1234', 'MUSTERI', '5404897036'),
(16, '52069258652', 'Selin Çetin', 'selin.çetin15@gmail.com', '1234', 'MUSTERI', '5322460988'),
(17, '25234525036', 'Mehmet Çelik', 'mehmet.çelik16@gmail.com', '1234', 'MUSTERI', '5367092156'),
(18, '99605427898', 'Mehmet Yılmaz', 'mehmet.yılmaz17@gmail.com', '1234', 'MUSTERI', '5547757639'),
(19, '73858177570', 'Emre Öztürk', 'emre.öztürk18@gmail.com', '1234', 'MUSTERI', '5396441428'),
(20, '80387776746', 'Ahmet Aslan', 'ahmet.aslan19@gmail.com', '1234', 'MUSTERI', '5397123468'),
(21, '33660046983', 'Veli Demir', 'veli.demir20@gmail.com', '1234', 'MUSTERI', '5384194395'),
(22, '88436490697', 'Veli Kara', 'veli.kara21@gmail.com', '1234', 'MUSTERI', '5512924515'),
(23, '54511416959', 'Selin Demir', 'selin.demir22@gmail.com', '1234', 'MUSTERI', '5326848599'),
(24, '19783172003', 'Ebru Öztürk', 'ebru.öztürk23@gmail.com', '1234', 'MUSTERI', '5462570027'),
(25, '17187806698', 'Mustafa Kılıç', 'mustafa.kılıç24@gmail.com', '1234', 'MUSTERI', '5362901156'),
(26, '23921792254', 'Onur Arslan', 'onur.arslan25@gmail.com', '1234', 'MUSTERI', '5351730390'),
(27, '38898501806', 'Esra Şimşek', 'esra.şimşek26@gmail.com', '1234', 'MUSTERI', '5387117251'),
(28, '35380318032', 'Deniz Aydın', 'deniz.aydın27@gmail.com', '1234', 'MUSTERI', '5513319530'),
(29, '13803772156', 'Deniz Polat', 'deniz.polat28@gmail.com', '1234', 'MUSTERI', '5523696183'),
(30, '76474859892', 'Mustafa Çelik', 'mustafa.çelik29@gmail.com', '1234', 'MUSTERI', '5319209002'),
(31, '88993518784', 'Selin Kılıç', 'selin.kılıç30@gmail.com', '1234', 'MUSTERI', '5444033696'),
(32, '39553704744', 'Deniz Kaya', 'deniz.kaya31@gmail.com', '1234', 'MUSTERI', '5379633690'),
(33, '72388438413', 'Zeynep Yılmaz', 'zeynep.yılmaz32@gmail.com', '1234', 'MUSTERI', '5511545194'),
(34, '73952875449', 'Selin Çetin', 'selin.çetin33@gmail.com', '1234', 'MUSTERI', '5301198011'),
(35, '34520208514', 'Burak Yılmaz', 'burak.yılmaz34@gmail.com', '1234', 'MUSTERI', '5538071609'),
(36, '99727946203', 'Ahmet Yılmaz', 'ahmet.yılmaz35@gmail.com', '1234', 'MUSTERI', '5528512952'),
(37, '68992691062', 'Murat Doğan', 'murat.doğan36@gmail.com', '1234', 'MUSTERI', '5356025329'),
(38, '43902700137', 'Burak Kurt', 'burak.kurt37@gmail.com', '1234', 'MUSTERI', '5361579755'),
(39, '32043386754', 'Murat Kara', 'murat.kara38@gmail.com', '1234', 'MUSTERI', '5329721109'),
(40, '17887617421', 'Zeynep Kara', 'zeynep.kara39@gmail.com', '1234', 'MUSTERI', '5463157998'),
(41, '49824157712', 'Ebru Aydın', 'ebru.aydın40@gmail.com', '1234', 'MUSTERI', '5507960586'),
(42, '63193553488', 'Can Yılmaz', 'can.yılmaz41@gmail.com', '1234', 'MUSTERI', '5496180804'),
(43, '19154600068', 'Emre Kara', 'emre.kara42@gmail.com', '1234', 'MUSTERI', '5404186352'),
(44, '24044905727', 'Ali Kurt', 'ali.kurt43@gmail.com', '1234', 'MUSTERI', '5373541587'),
(45, '10000000000', 'Mustafa Kurt', 'mustafa.kurt44@gmail.com', '1234', 'MUSTERI', '5538393316'),
(46, '11111111111', 'Zeynep Demir', 'zeynep.demir45@hotmail.com', '1234', 'PERSONEL', '5511334923'),
(47, '41892424179', 'Deniz Kurt', 'deniz.kurt46@hotmail.com', '1234', 'PERSONEL', '5445990950'),
(48, '73065341947', 'Elif Aslan', 'elif.aslan47@hotmail.com', '1234', 'PERSONEL', '5458029753'),
(49, '46480843889', 'Ahmet Özdemir', 'ahmet.özdemir48@hotmail.com', '1234', 'PERSONEL', '5417471077'),
(50, '60671126863', 'Onur Aslan', 'onur.aslan49@hotmail.com', '1234', 'PERSONEL', '5476679578');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
