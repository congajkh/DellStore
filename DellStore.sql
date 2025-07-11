-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th7 11, 2025 lúc 01:22 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `dellstore`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `card`
--

CREATE TABLE `card` (
  `id` int(11) NOT NULL,
  `ten` varchar(100) DEFAULT NULL,
  `loai` varchar(50) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chi_tiet_san_pham`
--

CREATE TABLE `chi_tiet_san_pham` (
  `id` int(11) NOT NULL,
  `san_pham_id` int(11) DEFAULT NULL,
  `ram_id` int(11) DEFAULT NULL,
  `cpu_id` int(11) DEFAULT NULL,
  `ssd_id` int(11) DEFAULT NULL,
  `card_id` int(11) DEFAULT NULL,
  `man_hinh_id` int(11) DEFAULT NULL,
  `gia_nhap` decimal(18,2) DEFAULT NULL,
  `gia_ban` decimal(18,2) DEFAULT NULL,
  `so_luong` int(11) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cpu`
--

CREATE TABLE `cpu` (
  `id` int(11) NOT NULL,
  `ten` varchar(100) DEFAULT NULL,
  `toc_do` varchar(50) DEFAULT NULL,
  `loai` varchar(50) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ct_dot_giam_gia`
--

CREATE TABLE `ct_dot_giam_gia` (
  `id` int(11) NOT NULL,
  `dot_giam_gia_id` int(11) DEFAULT NULL,
  `chi_tiet_sp_id` int(11) DEFAULT NULL,
  `muc_giam` decimal(18,2) DEFAULT NULL,
  `loai_giam` varchar(20) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ct_hoa_don`
--

CREATE TABLE `ct_hoa_don` (
  `id` int(11) NOT NULL,
  `hoa_don_id` int(11) DEFAULT NULL,
  `serial_id` int(11) DEFAULT NULL,
  `don_gia` decimal(18,2) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ct_thanh_toan`
--

CREATE TABLE `ct_thanh_toan` (
  `id` int(11) NOT NULL,
  `hoa_don_id` int(11) DEFAULT NULL,
  `hinh_thuc_id` int(11) DEFAULT NULL,
  `so_tien` decimal(18,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `dot_giam_gia`
--

CREATE TABLE `dot_giam_gia` (
  `id` int(11) NOT NULL,
  `ten` varchar(100) DEFAULT NULL,
  `ngay_bat_dau` datetime DEFAULT NULL,
  `ngay_ket_thuc` datetime DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hang`
--

CREATE TABLE `hang` (
  `id` int(11) NOT NULL,
  `ten` varchar(100) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hinh_anh`
--

CREATE TABLE `hinh_anh` (
  `id` int(11) NOT NULL,
  `chi_tiet_sp_id` int(11) DEFAULT NULL,
  `duong_dan` varchar(255) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hinh_thuc_thanh_toan`
--

CREATE TABLE `hinh_thuc_thanh_toan` (
  `id` int(11) NOT NULL,
  `ten` varchar(50) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoa_don`
--

CREATE TABLE `hoa_don` (
  `id` int(11) NOT NULL,
  `ma` varchar(20) DEFAULT NULL,
  `nhan_vien_id` int(11) DEFAULT NULL,
  `khach_hang_id` int(11) DEFAULT NULL,
  `ngay_tao` datetime DEFAULT NULL,
  `tong_tien` decimal(18,2) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoa_don_treo`
--

CREATE TABLE `hoa_don_treo` (
  `id` int(11) NOT NULL,
  `hoa_don_id` int(11) DEFAULT NULL,
  `ghi_chu` varchar(255) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khach_hang`
--

CREATE TABLE `khach_hang` (
  `id` int(11) NOT NULL,
  `ten` varchar(100) DEFAULT NULL,
  `ngay_sinh` date DEFAULT NULL,
  `gioi_tinh` varchar(10) DEFAULT NULL,
  `sdt` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `diem_thuong` int(11) DEFAULT NULL,
  `hang` varchar(50) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khach_hang`
--

INSERT INTO `khach_hang` (`id`, `ten`, `ngay_sinh`, `gioi_tinh`, `sdt`, `email`, `diem_thuong`, `hang`, `trang_thai`) VALUES
(1, 'Nguyen Thi Lan', '1995-05-10', 'Nữ', '0911111111', 'lan@gmail.com', 100, 'Dong', 1),
(2, 'Tran Van Minh', '1990-01-20', 'Nam', '0922222222', 'minh@gmail.com', 200, 'Bac', 1),
(3, 'Le Thi Hoa', '1985-07-30', 'Nữ', '0933333333', 'hoa@gmail.com', 50, 'Dong', 1),
(4, 'Pham Van Khoa', '1993-09-12', 'Nam', '0944444444', 'khoa@gmail.com', 500, 'Vang', 1),
(5, 'Vo Thi Mai', '1992-04-18', 'Nữ', '0955555555', 'mai@gmail.com', 300, 'Bac', 1),
(6, 'Hoang Van Tien', '1988-12-22', 'Nam', '0966666666', 'tien@gmail.com', 150, 'Dong', 1),
(7, 'Do Thi Thanh', '1996-03-08', 'Nữ', '0977777777', 'thanh@gmail.com', 220, 'Bac', 1),
(8, 'Bui Van An', '1991-11-11', 'Nam', '0988888888', 'an@gmail.com', 400, 'Vang', 1),
(9, 'Dang Thi Tuyet', '1994-08-01', 'Nữ', '0999999999', 'tuyet@gmail.com', 90, 'Dong', 1),
(10, 'Nguyen Van Quy', '1990-10-10', 'Nam', '0900000000', 'quy@gmail.com', 50, 'Dong', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loai_san_pham`
--

CREATE TABLE `loai_san_pham` (
  `id` int(11) NOT NULL,
  `ten` varchar(100) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `man_hinh`
--

CREATE TABLE `man_hinh` (
  `id` int(11) NOT NULL,
  `kich_thuoc` varchar(50) DEFAULT NULL,
  `do_phan_giai` varchar(50) DEFAULT NULL,
  `tan_so` varchar(50) DEFAULT NULL,
  `loai_tam_nen` varchar(50) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhan_vien`
--

CREATE TABLE `nhan_vien` (
  `id` int(11) NOT NULL,
  `ma_nv` varchar(20) DEFAULT NULL,
  `ten_nv` varchar(100) DEFAULT NULL,
  `gioi_tinh` varchar(10) DEFAULT NULL,
  `ngay_sinh` varchar(50) DEFAULT NULL,
  `dia_chi` varchar(255) DEFAULT NULL,
  `sdt` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL,
  `chuc_vu` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhan_vien`
--

INSERT INTO `nhan_vien` (`id`, `ma_nv`, `ten_nv`, `gioi_tinh`, `ngay_sinh`, `dia_chi`, `sdt`, `email`, `trang_thai`, `chuc_vu`) VALUES
(28, 'NV001', 'Nguyen Van A', 'Nam', '1990-01-01', 'Hà Nội', '0909123456', 'a@gmail.com', 1, 'admin'),
(29, 'NV002', 'Tran Thi B', 'Nữ', '1992-05-12', 'TP.HCM', '0909456123', 'b@gmail.com', 1, 'nhan_vien'),
(30, 'NV003', 'Le Van C', 'Nam', '1988-08-08', 'Đà Nẵng', '0909123987', 'c@gmail.com', 1, 'quan_ly'),
(31, 'NV004', 'Pham Thi D', 'Nữ', '1995-03-15', 'Hải Phòng', '0909567890', 'd@gmail.com', 1, 'nhan_vien'),
(32, 'NV005', 'Hoang Van E', 'Nam', '1993-07-20', 'Cần Thơ', '0912123456', 'e@gmail.com', 0, 'nhan_vien'),
(33, 'NV006', 'Nguyen Thi F', 'Nữ', '1990-12-25', 'Bình Dương', '0912345678', 'f@gmail.com', 1, 'quan_ly'),
(34, 'NV007', 'Do Van G', 'Nam', '1991-06-10', 'Hưng Yên', '0921123456', 'g@gmail.com', 1, 'nhan_vien'),
(35, 'NV008', 'Vo Thi H', 'Nữ', '1994-11-11', 'Quảng Ninh', '0933456123', 'h@gmail.com', 0, 'nhan_vien'),
(36, 'NV009', 'Dang Van I', 'Nam', '1989-09-09', 'Nam Định', '0944123456', 'i@gmail.com', 1, 'admin'),
(37, 'NV010', 'Bui Thi K', 'Nữ', '1996-10-01', 'Thái Nguyên', '0952123456', 'k@gmail.com', 1, 'nhan_vien'),
(38, 'NV011', 'Đỗ Chí Công', 'Nam', '23/12/2000', 'Hà Nội', '0963852741', 'docong989@gmail.com', 1, 'Nhân viên');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhat_ky_dang_nhap`
--

CREATE TABLE `nhat_ky_dang_nhap` (
  `id` int(11) NOT NULL,
  `nhan_vien_id` int(11) DEFAULT NULL,
  `thoi_gian` datetime DEFAULT NULL,
  `dia_chi_ip` varchar(100) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieu_giam_gia`
--

CREATE TABLE `phieu_giam_gia` (
  `id` int(11) NOT NULL,
  `ten` varchar(100) DEFAULT NULL,
  `muc_giam` decimal(18,2) DEFAULT NULL,
  `loai` varchar(20) DEFAULT NULL,
  `ngay_bat_dau` datetime DEFAULT NULL,
  `ngay_ket_thuc` datetime DEFAULT NULL,
  `hang_ap_dung` varchar(50) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ram`
--

CREATE TABLE `ram` (
  `id` int(11) NOT NULL,
  `dung_luong` varchar(50) DEFAULT NULL,
  `loai` varchar(50) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `san_pham`
--

CREATE TABLE `san_pham` (
  `id` int(11) NOT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(255) DEFAULT NULL,
  `loai_san_pham_id` int(11) DEFAULT NULL,
  `hang_id` int(11) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `serial`
--

CREATE TABLE `serial` (
  `id` int(11) NOT NULL,
  `ma_serial` varchar(50) DEFAULT NULL,
  `chi_tiet_sp_id` int(11) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ssd`
--

CREATE TABLE `ssd` (
  `id` int(11) NOT NULL,
  `dung_luong` varchar(50) DEFAULT NULL,
  `loai` varchar(50) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tai_khoan`
--

CREATE TABLE `tai_khoan` (
  `id` int(11) NOT NULL,
  `ten_dang_nhap` varchar(50) DEFAULT NULL,
  `mat_khau` varchar(100) DEFAULT NULL,
  `nhan_vien_id` int(11) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tai_khoan`
--

INSERT INTO `tai_khoan` (`id`, `ten_dang_nhap`, `mat_khau`, `nhan_vien_id`, `trang_thai`) VALUES
(1, 'admin1', '123456', 28, 1),
(2, 'nhanvien2', 'abc123', 29, 1),
(3, 'quanly3', 'qwerty', 30, 1),
(4, 'admin4', 'password', 31, 0),
(5, 'user5', 'userpass', 32, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thong_ke_doanh_thu`
--

CREATE TABLE `thong_ke_doanh_thu` (
  `id` int(11) NOT NULL,
  `ngay` date DEFAULT NULL,
  `san_pham_id` int(11) DEFAULT NULL,
  `so_luong` int(11) DEFAULT NULL,
  `doanh_thu` decimal(18,2) DEFAULT NULL,
  `trang_thai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `card`
--
ALTER TABLE `card`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `chi_tiet_san_pham`
--
ALTER TABLE `chi_tiet_san_pham`
  ADD PRIMARY KEY (`id`),
  ADD KEY `san_pham_id` (`san_pham_id`),
  ADD KEY `ram_id` (`ram_id`),
  ADD KEY `cpu_id` (`cpu_id`),
  ADD KEY `ssd_id` (`ssd_id`),
  ADD KEY `card_id` (`card_id`),
  ADD KEY `man_hinh_id` (`man_hinh_id`);

--
-- Chỉ mục cho bảng `cpu`
--
ALTER TABLE `cpu`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `ct_dot_giam_gia`
--
ALTER TABLE `ct_dot_giam_gia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `dot_giam_gia_id` (`dot_giam_gia_id`),
  ADD KEY `chi_tiet_sp_id` (`chi_tiet_sp_id`);

--
-- Chỉ mục cho bảng `ct_hoa_don`
--
ALTER TABLE `ct_hoa_don`
  ADD PRIMARY KEY (`id`),
  ADD KEY `hoa_don_id` (`hoa_don_id`),
  ADD KEY `serial_id` (`serial_id`);

--
-- Chỉ mục cho bảng `ct_thanh_toan`
--
ALTER TABLE `ct_thanh_toan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `hoa_don_id` (`hoa_don_id`),
  ADD KEY `hinh_thuc_id` (`hinh_thuc_id`);

--
-- Chỉ mục cho bảng `dot_giam_gia`
--
ALTER TABLE `dot_giam_gia`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `hang`
--
ALTER TABLE `hang`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `hinh_anh`
--
ALTER TABLE `hinh_anh`
  ADD PRIMARY KEY (`id`),
  ADD KEY `chi_tiet_sp_id` (`chi_tiet_sp_id`);

--
-- Chỉ mục cho bảng `hinh_thuc_thanh_toan`
--
ALTER TABLE `hinh_thuc_thanh_toan`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `hoa_don`
--
ALTER TABLE `hoa_don`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nhan_vien_id` (`nhan_vien_id`),
  ADD KEY `khach_hang_id` (`khach_hang_id`);

--
-- Chỉ mục cho bảng `hoa_don_treo`
--
ALTER TABLE `hoa_don_treo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `hoa_don_id` (`hoa_don_id`);

--
-- Chỉ mục cho bảng `khach_hang`
--
ALTER TABLE `khach_hang`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `loai_san_pham`
--
ALTER TABLE `loai_san_pham`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `man_hinh`
--
ALTER TABLE `man_hinh`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `nhan_vien`
--
ALTER TABLE `nhan_vien`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ma_nv` (`ma_nv`);

--
-- Chỉ mục cho bảng `nhat_ky_dang_nhap`
--
ALTER TABLE `nhat_ky_dang_nhap`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nhan_vien_id` (`nhan_vien_id`);

--
-- Chỉ mục cho bảng `phieu_giam_gia`
--
ALTER TABLE `phieu_giam_gia`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `ram`
--
ALTER TABLE `ram`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `san_pham`
--
ALTER TABLE `san_pham`
  ADD PRIMARY KEY (`id`),
  ADD KEY `loai_san_pham_id` (`loai_san_pham_id`),
  ADD KEY `hang_id` (`hang_id`);

--
-- Chỉ mục cho bảng `serial`
--
ALTER TABLE `serial`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ma_serial` (`ma_serial`),
  ADD KEY `chi_tiet_sp_id` (`chi_tiet_sp_id`);

--
-- Chỉ mục cho bảng `ssd`
--
ALTER TABLE `ssd`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tai_khoan`
--
ALTER TABLE `tai_khoan`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ten_dang_nhap` (`ten_dang_nhap`),
  ADD KEY `nhan_vien_id` (`nhan_vien_id`);

--
-- Chỉ mục cho bảng `thong_ke_doanh_thu`
--
ALTER TABLE `thong_ke_doanh_thu`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `card`
--
ALTER TABLE `card`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `chi_tiet_san_pham`
--
ALTER TABLE `chi_tiet_san_pham`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `cpu`
--
ALTER TABLE `cpu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `ct_dot_giam_gia`
--
ALTER TABLE `ct_dot_giam_gia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `ct_hoa_don`
--
ALTER TABLE `ct_hoa_don`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `ct_thanh_toan`
--
ALTER TABLE `ct_thanh_toan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `dot_giam_gia`
--
ALTER TABLE `dot_giam_gia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `hang`
--
ALTER TABLE `hang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `hinh_anh`
--
ALTER TABLE `hinh_anh`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `hinh_thuc_thanh_toan`
--
ALTER TABLE `hinh_thuc_thanh_toan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `hoa_don`
--
ALTER TABLE `hoa_don`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `hoa_don_treo`
--
ALTER TABLE `hoa_don_treo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `khach_hang`
--
ALTER TABLE `khach_hang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `loai_san_pham`
--
ALTER TABLE `loai_san_pham`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `man_hinh`
--
ALTER TABLE `man_hinh`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `nhan_vien`
--
ALTER TABLE `nhan_vien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT cho bảng `nhat_ky_dang_nhap`
--
ALTER TABLE `nhat_ky_dang_nhap`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `phieu_giam_gia`
--
ALTER TABLE `phieu_giam_gia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `ram`
--
ALTER TABLE `ram`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `san_pham`
--
ALTER TABLE `san_pham`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `serial`
--
ALTER TABLE `serial`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `ssd`
--
ALTER TABLE `ssd`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tai_khoan`
--
ALTER TABLE `tai_khoan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `thong_ke_doanh_thu`
--
ALTER TABLE `thong_ke_doanh_thu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chi_tiet_san_pham`
--
ALTER TABLE `chi_tiet_san_pham`
  ADD CONSTRAINT `chi_tiet_san_pham_ibfk_1` FOREIGN KEY (`san_pham_id`) REFERENCES `san_pham` (`id`),
  ADD CONSTRAINT `chi_tiet_san_pham_ibfk_2` FOREIGN KEY (`ram_id`) REFERENCES `ram` (`id`),
  ADD CONSTRAINT `chi_tiet_san_pham_ibfk_3` FOREIGN KEY (`cpu_id`) REFERENCES `cpu` (`id`),
  ADD CONSTRAINT `chi_tiet_san_pham_ibfk_4` FOREIGN KEY (`ssd_id`) REFERENCES `ssd` (`id`),
  ADD CONSTRAINT `chi_tiet_san_pham_ibfk_5` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`),
  ADD CONSTRAINT `chi_tiet_san_pham_ibfk_6` FOREIGN KEY (`man_hinh_id`) REFERENCES `man_hinh` (`id`);

--
-- Các ràng buộc cho bảng `ct_dot_giam_gia`
--
ALTER TABLE `ct_dot_giam_gia`
  ADD CONSTRAINT `ct_dot_giam_gia_ibfk_1` FOREIGN KEY (`dot_giam_gia_id`) REFERENCES `dot_giam_gia` (`id`),
  ADD CONSTRAINT `ct_dot_giam_gia_ibfk_2` FOREIGN KEY (`chi_tiet_sp_id`) REFERENCES `chi_tiet_san_pham` (`id`);

--
-- Các ràng buộc cho bảng `ct_hoa_don`
--
ALTER TABLE `ct_hoa_don`
  ADD CONSTRAINT `ct_hoa_don_ibfk_1` FOREIGN KEY (`hoa_don_id`) REFERENCES `hoa_don` (`id`),
  ADD CONSTRAINT `ct_hoa_don_ibfk_2` FOREIGN KEY (`serial_id`) REFERENCES `serial` (`id`);

--
-- Các ràng buộc cho bảng `ct_thanh_toan`
--
ALTER TABLE `ct_thanh_toan`
  ADD CONSTRAINT `ct_thanh_toan_ibfk_1` FOREIGN KEY (`hoa_don_id`) REFERENCES `hoa_don` (`id`),
  ADD CONSTRAINT `ct_thanh_toan_ibfk_2` FOREIGN KEY (`hinh_thuc_id`) REFERENCES `hinh_thuc_thanh_toan` (`id`);

--
-- Các ràng buộc cho bảng `hinh_anh`
--
ALTER TABLE `hinh_anh`
  ADD CONSTRAINT `hinh_anh_ibfk_1` FOREIGN KEY (`chi_tiet_sp_id`) REFERENCES `chi_tiet_san_pham` (`id`);

--
-- Các ràng buộc cho bảng `hoa_don`
--
ALTER TABLE `hoa_don`
  ADD CONSTRAINT `hoa_don_ibfk_1` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`),
  ADD CONSTRAINT `hoa_don_ibfk_2` FOREIGN KEY (`khach_hang_id`) REFERENCES `khach_hang` (`id`);

--
-- Các ràng buộc cho bảng `hoa_don_treo`
--
ALTER TABLE `hoa_don_treo`
  ADD CONSTRAINT `hoa_don_treo_ibfk_1` FOREIGN KEY (`hoa_don_id`) REFERENCES `hoa_don` (`id`);

--
-- Các ràng buộc cho bảng nhat_ky_dang_nhap
--
ALTER TABLE nhat_ky_dang_nhap
  ADD CONSTRAINT FK_nhat_ky_dang_nhap_nhan_vien FOREIGN KEY (nhan_vien_id) REFERENCES nhan_vien(id);
GO

--
-- Các ràng buộc cho bảng san_pham
--
ALTER TABLE san_pham
  ADD CONSTRAINT FK_san_pham_loai_san_pham FOREIGN KEY (loai_san_pham_id) REFERENCES loai_san_pham(id),
  ADD CONSTRAINT FK_san_pham_hang FOREIGN KEY (hang_id) REFERENCES hang(id);
GO

--
-- Các ràng buộc cho bảng serial
--
ALTER TABLE serial
  ADD CONSTRAINT FK_serial_chi_tiet_san_pham FOREIGN KEY (chi_tiet_sp_id) REFERENCES chi_tiet_san_pham(id);
GO

--
-- Các ràng buộc cho bảng tai_khoan
--
ALTER TABLE tai_khoan
  ADD CONSTRAINT FK_tai_khoan_nhan_vien FOREIGN KEY (nhan_vien_id) REFERENCES nhan_vien(id);
GO
