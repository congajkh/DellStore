CREATE database DellStore;
GO
USE DellStore;
GO
-- Drop tables if exist (order matters for FK)
IF OBJECT_ID('dbo.ct_dot_giam_gia', 'U') IS NOT NULL DROP TABLE dbo.ct_dot_giam_gia;
IF OBJECT_ID('dbo.ct_hoa_don', 'U') IS NOT NULL DROP TABLE dbo.ct_hoa_don;
IF OBJECT_ID('dbo.ct_thanh_toan', 'U') IS NOT NULL DROP TABLE dbo.ct_thanh_toan;
IF OBJECT_ID('dbo.dot_giam_gia', 'U') IS NOT NULL DROP TABLE dbo.dot_giam_gia;
IF OBJECT_ID('dbo.hinh_anh', 'U') IS NOT NULL DROP TABLE dbo.hinh_anh;
IF OBJECT_ID('dbo.hinh_thuc_thanh_toan', 'U') IS NOT NULL DROP TABLE dbo.hinh_thuc_thanh_toan;
IF OBJECT_ID('dbo.hoa_don', 'U') IS NOT NULL DROP TABLE dbo.hoa_don;
IF OBJECT_ID('dbo.hoa_don_treo', 'U') IS NOT NULL DROP TABLE dbo.hoa_don_treo;
IF OBJECT_ID('dbo.khach_hang', 'U') IS NOT NULL DROP TABLE dbo.khach_hang;
IF OBJECT_ID('dbo.loai_san_pham', 'U') IS NOT NULL DROP TABLE dbo.loai_san_pham;
IF OBJECT_ID('dbo.man_hinh', 'U') IS NOT NULL DROP TABLE dbo.man_hinh;
IF OBJECT_ID('dbo.nhan_vien', 'U') IS NOT NULL DROP TABLE dbo.nhan_vien;
IF OBJECT_ID('dbo.nhat_ky_dang_nhap', 'U') IS NOT NULL DROP TABLE dbo.nhat_ky_dang_nhap;
IF OBJECT_ID('dbo.ocung', 'U') IS NOT NULL DROP TABLE dbo.ocung;
IF OBJECT_ID('dbo.phieu_giam_gia', 'U') IS NOT NULL DROP TABLE dbo.phieu_giam_gia;
IF OBJECT_ID('dbo.ram', 'U') IS NOT NULL DROP TABLE dbo.ram;
IF OBJECT_ID('dbo.san_pham', 'U') IS NOT NULL DROP TABLE dbo.san_pham;
IF OBJECT_ID('dbo.serial', 'U') IS NOT NULL DROP TABLE dbo.serial;
IF OBJECT_ID('dbo.tai_khoan', 'U') IS NOT NULL DROP TABLE dbo.tai_khoan;
IF OBJECT_ID('dbo.thong_ke_doanh_thu', 'U') IS NOT NULL DROP TABLE dbo.thong_ke_doanh_thu;
IF OBJECT_ID('dbo.card', 'U') IS NOT NULL DROP TABLE dbo.card;
IF OBJECT_ID('dbo.cpu', 'U') IS NOT NULL DROP TABLE dbo.cpu;
IF OBJECT_ID('dbo.hang', 'U') IS NOT NULL DROP TABLE dbo.hang;
IF OBJECT_ID('dbo.chi_tiet_san_pham', 'U') IS NOT NULL DROP TABLE dbo.chi_tiet_san_pham;

-- Now add CREATE TABLE, VIEW, INSERT, and constraints below
-- Copy and adapt from update11.sql, removing all Windows/ALTER DATABASE commands
-- Make sure all FK for tai_khoan use manv (mã nhân viên)

-- Tạo bảng
SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON

CREATE TABLE [dbo].[ram](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](50) NULL,
	[dung_luong] [nvarchar](50) NULL,
	[loai] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[ocung](
	[id] [int] NOT NULL,
	[ten] [nvarchar](50) NULL,
	[dung_luong] [nvarchar](50) NULL,
	[loai] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[card](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[loai] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[cpu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[toc_do] [nvarchar](50) NULL,
	[loai] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[san_pham](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](255) NULL,
	[masp] [nvarchar](255) NULL,
	[mo_ta] [nvarchar](255) NULL,
	[loai_san_pham_id] [int] NULL,
	[hang_id] [int] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);
drop TABLE chi_tiet_san_pham; 
CREATE TABLE [dbo].[chi_tiet_san_pham] (
    [id] INT IDENTITY(1,1) PRIMARY KEY,
    [san_pham_id] INT,
    [ram_id] INT,
    [cpu_id] INT,
    [ocung_id] INT,
    [card_id] INT,
    [hang_id] INT,
    [serial_id] INT,  -- Thêm khóa ngoại đến bảng serial
    [gia_ban] DECIMAL(18, 2),
    [trang_thai] INT,

    CONSTRAINT FK_ctsp_sanpham FOREIGN KEY (san_pham_id) REFERENCES san_pham(id),
    CONSTRAINT FK_ctsp_ram FOREIGN KEY (ram_id) REFERENCES ram(id),
    CONSTRAINT FK_ctsp_cpu FOREIGN KEY (cpu_id) REFERENCES cpu(id),
    CONSTRAINT FK_ctsp_ocung FOREIGN KEY (ocung_id) REFERENCES ocung(id),
    CONSTRAINT FK_ctsp_card FOREIGN KEY (card_id) REFERENCES card(id),
    CONSTRAINT FK_ctsp_hang FOREIGN KEY (hang_id) REFERENCES hang(id),
    CONSTRAINT FK_ctsp_serial FOREIGN KEY (serial_id) REFERENCES serial(id)
);


-- Các bảng còn lại
-- (Copy đầy đủ các CREATE TABLE từ update11.sql, loại bỏ các lệnh ALTER DATABASE, đường dẫn Windows)

CREATE TABLE [dbo].[ct_dot_giam_gia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dot_giam_gia_id] [int] NULL,
	[chi_tiet_sp_id] [int] NULL,
	[muc_giam] [decimal](18, 2) NULL,
	[loai_giam] [nvarchar](20) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE ct_hoa_don (
    id INT IDENTITY(1,1) PRIMARY KEY,
    hoa_don_id INT NOT NULL,
    serial_id INT NOT NULL,
    so_luong INT DEFAULT 1,
    don_gia DECIMAL(18,2),
    tien_khuyen_mai DECIMAL(18,2) DEFAULT 0,
    tong_tien AS (don_gia * so_luong - tien_khuyen_mai) PERSISTED,
    trang_thai INT DEFAULT 1,

    CONSTRAINT FK_cthd_hoa_don FOREIGN KEY (hoa_don_id) REFERENCES hoa_don(id),
    CONSTRAINT FK_cthd_serial FOREIGN KEY (serial_id) REFERENCES serial(id)
);



CREATE TABLE [dbo].[ct_thanh_toan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[hoa_don_id] [int] NULL,
	[hinh_thuc_id] [int] NULL,
	[so_tien] [decimal](18, 2) NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[dot_giam_gia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_bat_dau] [datetime] NULL,
	[ngay_ket_thuc] [datetime] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[hinh_anh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[chi_tiet_sp_id] [int] NULL,
	[duong_dan] [nvarchar](255) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[hinh_thuc_thanh_toan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](20) NULL,
	[nhan_vien_id] [int] NULL,
	[khach_hang_id] [int] NULL,
	[ngay_tao] [datetime] NULL,
	[tong_tien] [decimal](18, 2) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[hoa_don_treo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[hoa_don_id] [int] NULL,
	[ghi_chu] [nvarchar](255) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[khach_hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_sinh] [date] NULL,
	[gioi_tinh] [nvarchar](10) NULL,
	[sdt] [nvarchar](20) NULL,
	[email] [nvarchar](100) NULL,
	[diem_thuong] [int] NULL,
	[hang] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[loai_san_pham](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[man_hinh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[kich_thuoc] [nvarchar](50) NULL,
	[do_phan_giai] [nvarchar](50) NULL,
	[tan_so] [nvarchar](50) NULL,
	[loai_tam_nen] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[nhan_vien](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_nv] [nvarchar](20) NULL,
	[ten_nv] [nvarchar](100) NULL,
	[gioi_tinh] [nvarchar](10) NULL,
	[ngay_sinh] [nvarchar](50) NULL,
	[dia_chi] [nvarchar](255) NULL,
	[sdt] [nvarchar](20) NULL,
	[email] [nvarchar](100) NULL,
	[trang_thai] [int] NULL,
	[chuc_vu] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[nhat_ky_dang_nhap](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nhan_vien_id] [int] NULL,
	[thoi_gian] [datetime] NULL,
	[dia_chi_ip] [nvarchar](100) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[phieu_giam_gia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[muc_giam] [decimal](18, 2) NULL,
	[loai] [nvarchar](20) NULL,
	[ngay_bat_dau] [datetime] NULL,
	[ngay_ket_thuc] [datetime] NULL,
	[hang_ap_dung] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[serial](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_serial] [nvarchar](50) NULL,
	[chi_tiet_sp_id] [int] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[tai_khoan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_dang_nhap] [nvarchar](50) NULL,
	[mat_khau] [nvarchar](100) NULL,
	[trang_thai] [int] NULL,
	[manv] [nvarchar](20) NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);

CREATE TABLE [dbo].[thong_ke_doanh_thu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ngay] [date] NULL,
	[san_pham_id] [int] NULL,
	[so_luong] [int] NULL,
	[doanh_thu] [decimal](18, 2) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED ([id] ASC)
);
SET IDENTITY_INSERT [dbo].[serial] ON
INSERT into serial (id, ma_serial, chi_tiet_sp_id, trang_thai) VALUES
(1, N'SERIAL001', 1, 1),
(2, N'SERIAL002', 2, 1),
(3, N'SERIAL003', 3, 1),
(4, N'SERIAL004', 4, 1),
(5, N'SERIAL005', 5, 1),
(6, N'SERIAL006', 6, 1),
(7, N'SERIAL007', 7, 1),
(8, N'SERIAL008', 8, 1),
(9, N'SERIAL009', 9, 1),
(10, N'SERIAL010', 10, 1);
SET IDENTITY_INSERT [dbo].[serial] OFF
-- Thêm dữ liệu mẫu
-- Dữ liệu mẫu cho bảng ram
SET IDENTITY_INSERT [dbo].[ram] ON
INSERT INTO [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (1, N'RAM 4GB DDR4', N'4GB', N'DDR4', 1)
INSERT INTO [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (2, N'RAM 8GB DDR4', N'8GB', N'DDR4', 1)
INSERT INTO [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (3, N'RAM 16GB DDR4', N'16GB', N'DDR4', 1)
INSERT INTO [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (4, N'RAM 32GB DDR4', N'32GB', N'DDR4', 1)
INSERT INTO [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (5, N'RAM 64GB DDR4', N'64GB', N'DDR4', 1)
INSERT INTO [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (6, N'RAM 128GB DDR4', N'128GB', N'DDR4', 1)
INSERT INTO [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (7, N'RAM 8GB DDR5', N'8GB', N'DDR5', 1)
INSERT INTO [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (8, N'RAM 16GB DDR5', N'16GB', N'DDR5', 1)
INSERT INTO [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (9, N'RAM 32GB DDR5', N'32GB', N'DDR5', 1)
INSERT INTO [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (10, N'RAM 64GB DDR5', N'64GB', N'DDR5', 1)
SET IDENTITY_INSERT [dbo].[ram] OFF

-- Dữ liệu mẫu cho bảng ocung
INSERT INTO [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (1, N'SSD KingStone', N'128GB', N'SSD', 1)
INSERT INTO [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (2, N'SSD Samsung', N'256GB', N'SSD', 1)
INSERT INTO [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (3, N'SSD WD', N'512GB', N'SSD', 1)
INSERT INTO [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (4, N'SSD Crucial', N'1TB', N'SSD', 1)
INSERT INTO [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (5, N'HDD Seagate', N'1TB', N'HDD', 1)
INSERT INTO [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (6, N'HDD Toshiba', N'2TB', N'HDD', 1)
INSERT INTO [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (7, N'SSD Kingston', N'2TB', N'SSD', 1)
INSERT INTO [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (8, N'SSD Samsung', N'4TB', N'SSD', 1)
INSERT INTO [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (9, N'HDD WD', N'4TB', N'HDD', 1)
INSERT INTO [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (10, N'SSD Crucial', N'8TB', N'SSD', 1)

-- Dữ liệu mẫu cho bảng card
SET IDENTITY_INSERT [dbo].[card] ON
INSERT INTO [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (1, N'NVIDIA GTX 1050', N'Rời', 1)
INSERT INTO [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (2, N'NVIDIA GTX 1650', N'Rời', 1)
INSERT INTO [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (3, N'NVIDIA RTX 2060', N'Rời', 1)
INSERT INTO [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (4, N'NVIDIA RTX 3060', N'Rời', 1)
INSERT INTO [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (5, N'NVIDIA RTX 4060', N'Rời', 1)
INSERT INTO [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (6, N'Intel UHD 620', N'Tích hợp', 1)
INSERT INTO [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (7, N'Intel Iris Xe', N'Tích hợp', 1)
INSERT INTO [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (8, N'AMD Radeon RX 560', N'Rời', 1)
INSERT INTO [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (9, N'AMD Radeon RX 6600M', N'Rời', 1)
INSERT INTO [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (10, N'NVIDIA Quadro T1000', N'Rời', 1)
SET IDENTITY_INSERT [dbo].[card] OFF

-- Dữ liệu mẫu cho bảng cpu
SET IDENTITY_INSERT [dbo].[cpu] ON
INSERT INTO [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (1, N'Intel Core i3-1115G4', N'3.0GHz', N'Laptop', 1)
INSERT INTO [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (2, N'Intel Core i5-1135G7', N'2.4GHz', N'Laptop', 1)
INSERT INTO [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (3, N'Intel Core i7-1165G7', N'2.8GHz', N'Laptop', 1)
INSERT INTO [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (4, N'AMD Ryzen 5 5500U', N'2.1GHz', N'Laptop', 1)
INSERT INTO [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (5, N'AMD Ryzen 7 5800U', N'1.9GHz', N'Laptop', 1)
INSERT INTO [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (6, N'Intel Core i9-11900H', N'2.5GHz', N'Laptop', 1)
INSERT INTO [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (7, N'AMD Ryzen 9 5900HX', N'3.3GHz', N'Laptop', 1)
INSERT INTO [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (8, N'Intel Core i5-1240P', N'3.3GHz', N'Laptop', 1)
INSERT INTO [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (9, N'Intel Core i7-12700H', N'2.7GHz', N'Laptop', 1)
INSERT INTO [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (10, N'AMD Ryzen 7 7730U', N'2.0GHz', N'Laptop', 1)
SET IDENTITY_INSERT [dbo].[cpu] OFF

-- Dữ liệu mẫu cho bảng hang
SET IDENTITY_INSERT [dbo].[hang] ON
INSERT INTO [dbo].[hang] ([id], [ten], [trang_thai]) VALUES (1, N'Dell', 1)
SET IDENTITY_INSERT [dbo].[hang] OFF

-- Dữ liệu mẫu cho bảng san_pham
SET IDENTITY_INSERT [dbo].[san_pham] ON
INSERT INTO [dbo].[san_pham] ([id], [ten], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (1, N'Laptop Dell Inspiron 15', N'Intel Core i5, RAM 8GB, SSD 512GB', 1, 1, 1)
INSERT INTO [dbo].[san_pham] ([id], [ten], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (2, N'Laptop HP Pavilion', N'AMD Ryzen 5, RAM 16GB, SSD 512GB', 1, 2, 1)
INSERT INTO [dbo].[san_pham] ([id], [ten], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (3, N'MacBook Pro M1', N'Apple M1, RAM 8GB, SSD 256GB', 2, 3, 1)
INSERT INTO [dbo].[san_pham] ([id], [ten], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (4, N'Laptop Asus Vivobook', N'Intel Core i7, RAM 16GB, SSD 1TB', 1, 4, 1)
INSERT INTO [dbo].[san_pham] ([id], [ten], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (5, N'Acer Aspire 5', N'Intel Core i3, RAM 4GB, HDD 1TB', 1, 5, 0)
INSERT INTO [dbo].[san_pham] ([id], [ten], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (6, N'Lenovo ThinkPad E14', N'Intel Core i5, RAM 8GB, SSD 256GB', 1, 6, 1)
INSERT INTO [dbo].[san_pham] ([id], [ten], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (7, N'MSI Gaming GF63', N'Intel Core i7, RAM 16GB, SSD 512GB, GTX 1650', 3, 7, 1)
INSERT INTO [dbo].[san_pham] ([id], [ten], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (8, N'MacBook Air M2', N'Apple M2, RAM 8GB, SSD 512GB', 2, 3, 1)
INSERT INTO [dbo].[san_pham] ([id], [ten], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (9, N'Laptop LG Gram', N'Intel Evo, RAM 16GB, SSD 1TB', 1, 8, 1)
INSERT INTO [dbo].[san_pham] ([id], [ten], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (10, N'Surface Laptop 4', N'Intel Core i5, RAM 8GB, SSD 512GB', 2, 9, 1)
SET IDENTITY_INSERT [dbo].[san_pham] OFF

-- Dữ liệu mẫu cho bảng nhan_vien
SET IDENTITY_INSERT [dbo].[nhan_vien] ON
INSERT INTO [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (1, N'NV001', N'Nguyen Van A', N'Nam', N'1990-01-01', N'Hà Nội', N'0909123456', N'a@gmail.com', 1, N'admin')
INSERT INTO [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (2, N'NV002', N'Tran Thi B', N'Nữ', N'1992-05-12', N'TP.HCM', N'0909456123', N'b@gmail.com', 1, N'Nhân viên')
INSERT INTO [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (3, N'NV003', N'Le Van C', N'Nam', N'1988-08-08', N'Đà Nẵng', N'0909123987', N'c@gmail.com', 1, N'Quản lý')
INSERT INTO [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (4, N'NV004', N'Pham Thi D', N'Nữ', N'1995-03-15', N'Hải Phòng', N'0909567890', N'd@gmail.com', 1, N'Nhân viên')
INSERT INTO [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (5, N'NV005', N'Hoang Van E', N'Nam', N'1993-07-20', N'Cần Thơ', N'0912123456', N'e@gmail.com', 0, N'Nhân viên')
INSERT INTO [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (6, N'NV006', N'Nguyen Thi F', N'Nữ', N'1990-12-25', N'Bình Dương', N'0912345678', N'f@gmail.com', 1, N'Quản lý')
INSERT INTO [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (7, N'NV007', N'Do Van G', N'Nam', N'1991-06-10', N'Hưng Yên', N'0921123456', N'g@gmail.com', 1, N'Nhân viên')
INSERT INTO [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (8, N'NV008', N'Vo Thi H', N'Nữ', N'1994-11-11', N'Quảng Ninh', N'0933456123', N'h@gmail.com', 0, N'Nhân viên')
INSERT INTO [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (9, N'NV009', N'Dang Van I', N'Nam', N'1989-09-09', N'Nam Định', N'0944123456', N'i@gmail.com', 1, N'admin')
INSERT INTO [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (10, N'NV010', N'Bui Thi K', N'Nữ', N'1996-10-01', N'Thái Nguyên', N'0952123456', N'k@gmail.com', 1, N'Nhân viên')
SET IDENTITY_INSERT [dbo].[nhan_vien] OFF

-- Dữ liệu mẫu cho bảng khach_hang
SET IDENTITY_INSERT [dbo].[khach_hang] ON
INSERT INTO [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [diem_thuong], [hang], [trang_thai]) VALUES (1, N'Nguyen Thi Lan', '1995-05-10', N'Nữ', N'0911111111', N'lan@gmail.com', 100, N'Dong', 1)
INSERT INTO [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [diem_thuong], [hang], [trang_thai]) VALUES (2, N'Tran Van Minh', '1990-01-20', N'Nam', N'0922222222', N'minh@gmail.com', 200, N'Bac', 1)
INSERT INTO [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [diem_thuong], [hang], [trang_thai]) VALUES (3, N'Le Thi Hoa', '1985-07-30', N'Nữ', N'0933333333', N'hoa@gmail.com', 50, N'Dong', 1)
INSERT INTO [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [diem_thuong], [hang], [trang_thai]) VALUES (4, N'Pham Van Khoa', '1993-09-12', N'Nam', N'0944444444', N'khoa@gmail.com', 500, N'Vang', 1)
INSERT INTO [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [diem_thuong], [hang], [trang_thai]) VALUES (5, N'Vo Thi Mai', '1992-04-18', N'Nữ', N'0955555555', N'mai@gmail.com', 300, N'Bac', 1)
INSERT INTO [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [diem_thuong], [hang], [trang_thai]) VALUES (6, N'Hoang Van Tien', '1988-12-22', N'Nam', N'0966666666', N'tien@gmail.com', 150, N'Dong', 1)
INSERT INTO [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [diem_thuong], [hang], [trang_thai]) VALUES (7, N'Do Thi Thanh', '1996-03-08', N'Nữ', N'0977777777', N'thanh@gmail.com', 220, N'Bac', 1)
INSERT INTO [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [diem_thuong], [hang], [trang_thai]) VALUES (8, N'Bui Van An', '1991-11-11', N'Nam', N'0988888888', N'an@gmail.com', 400, N'Vang', 1)
INSERT INTO [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [diem_thuong], [hang], [trang_thai]) VALUES (9, N'Dang Thi Tuyet', '1994-08-01', N'Nữ', N'0999999999', N'tuyet@gmail.com', 90, N'Dong', 1)
INSERT INTO [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [diem_thuong], [hang], [trang_thai]) VALUES (10, N'Nguyen Van Quy', '1990-10-10', N'Nam', N'0900000000', N'quy@gmail.com', 50, N'Dong', 1)
SET IDENTITY_INSERT [dbo].[khach_hang] OFF

-- Dữ liệu mẫu cho bảng tai_khoan
SET IDENTITY_INSERT [dbo].[tai_khoan] ON
INSERT INTO [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (1, N'admin1', N'123456', 1, N'NV001')
INSERT INTO [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (2, N'nhanvien2', N'abc123', 1, N'NV002')
INSERT INTO [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (3, N'quanly3', N'qwerty', 1, N'NV003')
INSERT INTO [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (4, N'admin4', N'password', 0, N'NV004')
INSERT INTO [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (5, N'user5', N'userpass', 1, N'NV005')
INSERT INTO [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (6, N'user6', N'userpass6', 1, N'NV006')
INSERT INTO [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (7, N'user7', N'userpass7', 1, N'NV007')
INSERT INTO [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (8, N'user8', N'userpass8', 1, N'NV008')
INSERT INTO [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (9, N'user9', N'userpass9', 1, N'NV009')
INSERT INTO [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (10, N'user10', N'userpass10', 1, N'NV010')
SET IDENTITY_INSERT [dbo].[tai_khoan] OFF

-- Dữ liệu mẫu cho bảng chi_tiet_san_pham
INSERT INTO chi_tiet_san_pham (san_pham_id, ram_id, cpu_id, ocung_id, card_id, hang_id, serial_id, gia_ban, trang_thai)
VALUES 
(1, 2, 2, 3, 1, 1, 1, 15990000.00, 1),
(2, 3, 4, 4, 2, 1, 2, 17990000.00, 1),
(3, 4, 3, 5, 3, 1, 3, 13490000.00, 1),
(4, 5, 5, 6, 4, 1, 4, 21490000.00, 1),
(5, 6, 6, 7, 5, 1, 5, 11990000.00, 0),
(6, 7, 7, 8, 6, 1, 6, 15490000.00, 1),
(7, 8, 8, 9, 7, 1, 7, 14490000.00, 1),
(8, 9, 9, 10, 8, 1, 8, 19990000.00, 1),
(9, 10, 10, 1, 9, 1, 9, 23490000.00, 0);


SET IDENTITY_INSERT [dbo].[hoa_don] ON;
INSERT into hoa_don (id , ma, nhan_vien_id,khach_hang_id,ngay_tao,tong_tien,trang_thai) 
VALUES 
(1, N'HĐ001', 1, 1, '2023-10-01', 15990000.00, 1),
(2, N'HĐ002', 2, 2, '2023-10-02', 17990000.00, 1),
(3, N'HĐ003', 3, 3, '2023-10-03', 13490000.00, 1),
(4, N'HĐ004', 4, 4, '2023-10-04', 21490000.00, 1),
(5, N'HĐ005', 5, 5, '2023-10-05', 11990000.00, 0),
(6, N'HĐ006', 6, 6, '2023-10-06', 15490000.00, 1),
(7, N'HĐ007', 7, 7, '2023-10-07', 14490000.00, 1),
(8, N'HĐ008', 8, 8, '2023-10-08', 19990000.00, 1),
(9, N'HĐ009', 9, 9, '2023-10-09', 23490000.00, 0),
(10, N'HĐ010', 10, 10, '2023-10-10', 22490000.00, 1);
SET IDENTITY_INSERT [dbo].[hoa_don] OFF;
-- Tạo ràng buộc khoá ngoại

ALTER TABLE [dbo].[ct_dot_giam_gia] ADD CONSTRAINT FK_ct_dot_giam_gia_dot_giam_gia FOREIGN KEY([dot_giam_gia_id]) REFERENCES [dbo].[dot_giam_gia]([id]);
ALTER TABLE [dbo].[ct_dot_giam_gia] ADD CONSTRAINT FK_ct_dot_giam_gia_chi_tiet_sp FOREIGN KEY([chi_tiet_sp_id]) REFERENCES [dbo].[chi_tiet_san_pham]([id]);
--ty sua
-- ALTER TABLE [dbo].[ct_hoa_don] ADD CONSTRAINT FK_ct_hoa_don_hoa_don FOREIGN KEY([hoa_don_id]) REFERENCES [dbo].[hoa_don]([id]);
-- ALTER TABLE [dbo].[ct_hoa_don] ADD CONSTRAINT FK_ct_hoa_don_serial FOREIGN KEY([serial_id]) REFERENCES [dbo].[serial]([id]);

ALTER TABLE [dbo].[ct_thanh_toan] ADD CONSTRAINT FK_ct_thanh_toan_hinh_thuc FOREIGN KEY([hinh_thuc_id]) REFERENCES [dbo].[hinh_thuc_thanh_toan]([id]);
ALTER TABLE [dbo].[ct_thanh_toan] ADD CONSTRAINT FK_ct_thanh_toan_hoa_don FOREIGN KEY([hoa_don_id]) REFERENCES [dbo].[hoa_don]([id]);

ALTER TABLE [dbo].[hoa_don] ADD CONSTRAINT FK_hoa_don_khach_hang FOREIGN KEY([khach_hang_id]) REFERENCES [dbo].[khach_hang]([id]);
ALTER TABLE [dbo].[hoa_don] ADD CONSTRAINT FK_hoa_don_nhan_vien FOREIGN KEY([nhan_vien_id]) REFERENCES [dbo].[nhan_vien]([id]);

ALTER TABLE [dbo].[hoa_don_treo] ADD CONSTRAINT FK_hoa_don_treo_hoa_don FOREIGN KEY([hoa_don_id]) REFERENCES [dbo].[hoa_don]([id]);

ALTER TABLE [dbo].[nhat_ky_dang_nhap] ADD CONSTRAINT FK_nhat_ky_dang_nhap_nhan_vien FOREIGN KEY([nhan_vien_id]) REFERENCES [dbo].[nhan_vien]([id]);

ALTER TABLE [dbo].[san_pham] ADD CONSTRAINT FK_san_pham_hang FOREIGN KEY([hang_id]) REFERENCES [dbo].[hang]([id]);
ALTER TABLE [dbo].[serial] ADD CONSTRAINT FK_serial_chi_tiet_sp FOREIGN KEY([chi_tiet_sp_id]) REFERENCES [dbo].[chi_tiet_san_pham]([id]);

ALTER TABLE [dbo].[hinh_anh] ADD CONSTRAINT FK_hinh_anh_chi_tiet_sp FOREIGN KEY([chi_tiet_sp_id]) REFERENCES [dbo].[chi_tiet_san_pham]([id]);

-- Ràng buộc cho bảng tai_khoan liên kết với nhan_vien qua mã nhân viên
ALTER TABLE [dbo].[tai_khoan] ADD CONSTRAINT FK_tai_khoan_nhan_vien FOREIGN KEY([manv]) REFERENCES [dbo].[nhan_vien]([ma_nv]);

-- Tách các lệnh CREATE VIEW ra batch riêng biệt
GO
CREATE VIEW [dbo].[vw_chi_tiet_san_pham_full] AS
SELECT ct.id, sp.ten AS ten_san_pham, cpu.ten AS ten_cpu, ram.ten AS ten_ram,
       oc.ten AS ten_ocung, card.ten AS ten_card, h.ten AS ten_hang,
       ct.serial, ct.gia_ban, ct.trang_thai
FROM chi_tiet_san_pham ct
JOIN san_pham sp ON ct.san_pham_id = sp.id
JOIN cpu ON ct.cpu_id = cpu.id
JOIN ram ON ct.ram_id = ram.id
JOIN ocung oc ON ct.ocung_id = oc.id
JOIN card ON ct.card_id = card.id
JOIN hang h ON ct.hang_id = h.id;
GO
CREATE VIEW v_chi_tiet_san_pham_display AS
SELECT 
    ct.id,
    sp.ten AS ten_san_pham,
    cpu.ten AS ten_cpu,
    card.ten AS ten_card,
    h.ten AS ten_hang,
    oc.dung_luong AS ten_ocung,
    ram.dung_luong AS ten_ram,
    ct.serial,
    ct.gia_ban,
    ct.trang_thai
FROM chi_tiet_san_pham ct
JOIN san_pham sp ON ct.san_pham_id = sp.id
JOIN cpu ON ct.cpu_id = cpu.id
JOIN card ON ct.card_id = card.id
JOIN hang h ON ct.hang_id = h.id
JOIN ocung oc ON ct.ocung_id = oc.id
JOIN ram ON ct.ram_id = ram.id;
GO
DELETE from san_pham;
SET IDENTITY_INSERT [dbo].[san_pham] ON
SET IDENTITY_INSERT san_pham ON;

INSERT INTO san_pham (id, ten, masp, mo_ta, loai_san_pham_id, hang_id, trang_thai) VALUES
(1, N'Laptop Dell Vostro 15', 'SP001', N'Máy phù hợp cho sinh viên, văn phòng.', 1, 1, 1),
(2, N'Dell Inspiron 14', 'SP002', N'Hiệu năng ổn định, thiết kế gọn nhẹ.', 1, 1, 1),
(3, N'Dell XPS 13', 'SP003', N'Cao cấp, màn hình viền mỏng.', 1, 1, 1),
(4, N'Dell G15 Gaming', 'SP004', N'Laptop gaming mạnh mẽ.', 2, 1, 1),
(5, N'Dell Latitude 7410', 'SP005', N'Cho doanh nghiệp, pin lâu.', 1, 1, 1),
(6, N'Dell Precision 3551', 'SP006', N'Máy trạm đồ họa chuyên nghiệp.', 3, 1, 1),
(7, N'Dell Alienware M15', 'SP007', N'Gaming cao cấp, đèn LED RGB.', 2, 1, 1),
(8, N'Dell Inspiron 15', 'SP008', N'Giá rẻ, cấu hình ổn.', 1, 1, 1),
(9, N'Dell XPS 15', 'SP009', N'Màn hình 4K, thiết kế cao cấp.', 1, 1, 1),
(10, N'Dell Chromebook 11', 'SP010', N'Dành cho giáo dục.', 4, 1, 1);

SET IDENTITY_INSERT san_pham OFF;

CREATE TABLE khuyen_mai (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ma_km NVARCHAR(20) NOT NULL UNIQUE,         -- Mã khuyến mãi
    ten_km NVARCHAR(100) NOT NULL,              -- Tên chương trình
    loai_km NVARCHAR(20) NOT NULL,              -- '%' hoặc 'tiền mặt'
    gia_tri DECIMAL(10, 2) NOT NULL,            -- Giá trị khuyến mãi
    ngay_bat_dau DATE NOT NULL,
    ngay_ket_thuc DATE NOT NULL,
    trang_thai INT NOT NULL                     -- 0 = hết hạn, 1 = còn hiệu lực
);
INSERT INTO khuyen_mai (ma_km, ten_km, loai_km, gia_tri, ngay_bat_dau, ngay_ket_thuc, trang_thai)
VALUES 
('KM001', N'Giảm 10%', N'%', 10.00, '2025-07-01', '2025-07-31', 1),
('KM002', N'Giảm 500K', N'tiền mặt', 500000.00, '2025-07-01', '2025-07-20', 1),
('KM003', N'Ưu đãi đặc biệt', N'%', 15.00, '2025-07-10', '2025-08-10', 1);
 

 select * from chi_tiet_san_pham;
 delete from khuyen_mai;
 INSERT INTO khuyen_mai (ma_km, ten_km, loai_km, gia_tri, ngay_bat_dau, ngay_ket_thuc, trang_thai)
VALUES 
('KM001', N'Giảm 10%', N'%', 10.00, '2025-07-01', '2025-07-31', 1),
('KM002', N'Giảm 500K', N'tiền mặt', 500000.00, '2025-07-01', '2025-07-20', 1),
('KM003', N'Ưu đãi đặc biệt', N'%', 15.00, '2025-07-10', '2025-08-10', 1);