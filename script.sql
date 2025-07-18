CREATE DATABASE DellStore;
USE DellStore;
/****** Object:  Table [dbo].[chi_tiet_san_pham]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chi_tiet_san_pham](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[san_pham_id] [int] NULL,
	[ram_id] [int] NULL,
	[cpu_id] [int] NULL,
	[ocung_id] [int] NULL,
	[card_id] [int] NULL,
	[hang_id] [int] NULL,
	[serial_id] [int] NULL,
	[gia_ban] [decimal](18, 2) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ram]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ram](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](50) NULL,
	[dung_luong] [nvarchar](50) NULL,
	[loai] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ocung]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ocung](
	[id] [int] NOT NULL,
	[ten] [nvarchar](50) NULL,
	[dung_luong] [nvarchar](50) NULL,
	[loai] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[card]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[card](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[loai] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cpu]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cpu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[toc_do] [nvarchar](50) NULL,
	[loai] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hang]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[serial]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[serial](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_serial] [nvarchar](50) NULL,
	[chi_tiet_sp_id] [int] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[san_pham]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[san_pham](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](255) NULL,
	[masp] [nvarchar](50) NULL,
	[mo_ta] [nvarchar](255) NULL,
	[loai_san_pham_id] [int] NULL,
	[hang_id] [int] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[vv_chi_tiet_san_pham_display]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[vv_chi_tiet_san_pham_display] AS
SELECT 
    ctsp.id,
    sp.ten AS ten_san_pham,
    r.ten AS ten_ram,
    cpu.ten AS ten_cpu,
    oc.ten AS ten_ocung,
    c.ten AS ten_card,
    h.ten AS ten_hang,
    s.ma_serial AS serial,
    ctsp.gia_ban,
    ctsp.trang_thai
FROM chi_tiet_san_pham ctsp
JOIN san_pham sp ON ctsp.san_pham_id = sp.id
LEFT JOIN ram r ON ctsp.ram_id = r.id
LEFT JOIN cpu cpu ON ctsp.cpu_id = cpu.id
LEFT JOIN ocung oc ON ctsp.ocung_id = oc.id
LEFT JOIN card c ON ctsp.card_id = c.id
LEFT JOIN hang h ON ctsp.hang_id = h.id
LEFT JOIN serial s ON ctsp.serial_id = s.id;
GO
/****** Object:  View [dbo].[vw_chi_tiet_san_pham_full]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
/****** Object:  View [dbo].[v_chi_tiet_san_pham_display]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[v_chi_tiet_san_pham_display] AS
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
/****** Object:  Table [dbo].[ct_hoa_don]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ct_hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[hoa_don_id] [int] NOT NULL,
	[serial_id] [int] NOT NULL,
	[so_luong] [int] NULL,
	[don_gia] [decimal](18, 2) NULL,
	[tien_khuyen_mai] [decimal](18, 2) NULL,
	[tong_tien]  AS ([don_gia]*[so_luong]-[tien_khuyen_mai]) PERSISTED,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ct_thanh_toan]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ct_thanh_toan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[hoa_don_id] [int] NULL,
	[hinh_thuc_id] [int] NULL,
	[so_tien] [decimal](18, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hinh_anh]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hinh_anh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[chi_tiet_sp_id] [int] NULL,
	[duong_dan] [nvarchar](255) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hinh_thuc_thanh_toan]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hinh_thuc_thanh_toan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](20) NULL,
	[nhan_vien_id] [int] NULL,
	[khach_hang_id] [int] NULL,
	[ngay_tao] [datetime] NULL,
	[tong_tien] [decimal](18, 2) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don_treo]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don_treo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[hoa_don_id] [int] NULL,
	[ghi_chu] [nvarchar](255) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khach_hang]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[khach_hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_sinh] [date] NULL,
	[gioi_tinh] [nvarchar](10) NULL,
	[sdt] [nvarchar](20) NULL,
	[email] [nvarchar](100) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khuyen_mai]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[khuyen_mai](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_km] [nvarchar](20) NOT NULL,
	[ten_km] [nvarchar](100) NOT NULL,
	[loai_km] [nvarchar](20) NOT NULL,
	[gia_tri] [decimal](10, 2) NOT NULL,
	[ngay_bat_dau] [date] NOT NULL,
	[ngay_ket_thuc] [date] NOT NULL,
	[trang_thai] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[loai_san_pham]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[loai_san_pham](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[man_hinh]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[man_hinh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[kich_thuoc] [nvarchar](50) NULL,
	[do_phan_giai] [nvarchar](50) NULL,
	[tan_so] [nvarchar](50) NULL,
	[loai_tam_nen] [nvarchar](50) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nhan_vien]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
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
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nhat_ky_dang_nhap]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nhat_ky_dang_nhap](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nhan_vien_id] [int] NULL,
	[thoi_gian] [datetime] NULL,
	[dia_chi_ip] [nvarchar](100) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tai_khoan]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tai_khoan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_dang_nhap] [nvarchar](50) NULL,
	[mat_khau] [nvarchar](100) NULL,
	[trang_thai] [int] NULL,
	[manv] [nvarchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[thong_ke_doanh_thu]    Script Date: 7/16/2025 5:10:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[thong_ke_doanh_thu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ngay] [date] NULL,
	[san_pham_id] [int] NULL,
	[so_luong] [int] NULL,
	[doanh_thu] [decimal](18, 2) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[card] ON 

INSERT [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (1, N'NVIDIA GTX 1050', N'Rời', 1)
INSERT [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (2, N'NVIDIA GTX 1650', N'Rời', 1)
INSERT [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (3, N'NVIDIA RTX 2060', N'Rời', 1)
INSERT [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (4, N'NVIDIA RTX 3060', N'Rời', 1)
INSERT [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (5, N'NVIDIA RTX 4060', N'Rời', 1)
INSERT [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (6, N'Intel UHD 620', N'Tích hợp', 1)
INSERT [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (7, N'Intel Iris Xe', N'Tích hợp', 1)
INSERT [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (8, N'AMD Radeon RX 560', N'Rời', 1)
INSERT [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (9, N'AMD Radeon RX 6600M', N'Rời', 1)
INSERT [dbo].[card] ([id], [ten], [loai], [trang_thai]) VALUES (10, N'NVIDIA Quadro T1000', N'Rời', 1)
SET IDENTITY_INSERT [dbo].[card] OFF
GO
SET IDENTITY_INSERT [dbo].[chi_tiet_san_pham] ON 

INSERT [dbo].[chi_tiet_san_pham] ([id], [san_pham_id], [ram_id], [cpu_id], [ocung_id], [card_id], [hang_id], [serial_id], [gia_ban], [trang_thai]) VALUES (1, 1, 2, 2, 3, 1, 1, 1, CAST(15990000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[chi_tiet_san_pham] ([id], [san_pham_id], [ram_id], [cpu_id], [ocung_id], [card_id], [hang_id], [serial_id], [gia_ban], [trang_thai]) VALUES (4, 1, 2, 2, 3, 1, 1, 1, CAST(15990000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[chi_tiet_san_pham] ([id], [san_pham_id], [ram_id], [cpu_id], [ocung_id], [card_id], [hang_id], [serial_id], [gia_ban], [trang_thai]) VALUES (5, 2, 3, 4, 4, 2, 1, 2, CAST(17990000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[chi_tiet_san_pham] ([id], [san_pham_id], [ram_id], [cpu_id], [ocung_id], [card_id], [hang_id], [serial_id], [gia_ban], [trang_thai]) VALUES (6, 3, 4, 3, 5, 3, 1, 3, CAST(13490000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[chi_tiet_san_pham] ([id], [san_pham_id], [ram_id], [cpu_id], [ocung_id], [card_id], [hang_id], [serial_id], [gia_ban], [trang_thai]) VALUES (7, 4, 5, 5, 6, 4, 1, 4, CAST(21490000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[chi_tiet_san_pham] ([id], [san_pham_id], [ram_id], [cpu_id], [ocung_id], [card_id], [hang_id], [serial_id], [gia_ban], [trang_thai]) VALUES (8, 5, 6, 6, 7, 5, 1, 5, CAST(11990000.00 AS Decimal(18, 2)), 0)
INSERT [dbo].[chi_tiet_san_pham] ([id], [san_pham_id], [ram_id], [cpu_id], [ocung_id], [card_id], [hang_id], [serial_id], [gia_ban], [trang_thai]) VALUES (9, 6, 7, 7, 8, 6, 1, 6, CAST(15490000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[chi_tiet_san_pham] ([id], [san_pham_id], [ram_id], [cpu_id], [ocung_id], [card_id], [hang_id], [serial_id], [gia_ban], [trang_thai]) VALUES (10, 7, 8, 8, 9, 7, 1, 7, CAST(14490000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[chi_tiet_san_pham] ([id], [san_pham_id], [ram_id], [cpu_id], [ocung_id], [card_id], [hang_id], [serial_id], [gia_ban], [trang_thai]) VALUES (11, 8, 9, 9, 10, 8, 1, 8, CAST(19990000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[chi_tiet_san_pham] ([id], [san_pham_id], [ram_id], [cpu_id], [ocung_id], [card_id], [hang_id], [serial_id], [gia_ban], [trang_thai]) VALUES (12, 9, 10, 10, 1, 9, 1, 9, CAST(23490000.00 AS Decimal(18, 2)), 0)
SET IDENTITY_INSERT [dbo].[chi_tiet_san_pham] OFF
GO
SET IDENTITY_INSERT [dbo].[cpu] ON 

INSERT [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (1, N'Intel Core i3-1115G4', N'3.0GHz', N'Laptop', 1)
INSERT [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (2, N'Intel Core i5-1135G7', N'2.4GHz', N'Laptop', 1)
INSERT [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (3, N'Intel Core i7-1165G7', N'2.8GHz', N'Laptop', 1)
INSERT [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (4, N'AMD Ryzen 5 5500U', N'2.1GHz', N'Laptop', 1)
INSERT [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (5, N'AMD Ryzen 7 5800U', N'1.9GHz', N'Laptop', 1)
INSERT [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (6, N'Intel Core i9-11900H', N'2.5GHz', N'Laptop', 1)
INSERT [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (7, N'AMD Ryzen 9 5900HX', N'3.3GHz', N'Laptop', 1)
INSERT [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (8, N'Intel Core i5-1240P', N'3.3GHz', N'Laptop', 1)
INSERT [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (9, N'Intel Core i7-12700H', N'2.7GHz', N'Laptop', 1)
INSERT [dbo].[cpu] ([id], [ten], [toc_do], [loai], [trang_thai]) VALUES (10, N'AMD Ryzen 7 7730U', N'2.0GHz', N'Laptop', 1)
SET IDENTITY_INSERT [dbo].[cpu] OFF
GO
SET IDENTITY_INSERT [dbo].[hang] ON 

INSERT [dbo].[hang] ([id], [ten], [trang_thai]) VALUES (1, N'Dell', 1)
SET IDENTITY_INSERT [dbo].[hang] OFF
GO
SET IDENTITY_INSERT [dbo].[hoa_don] ON 

INSERT [dbo].[hoa_don] ([id], [ma], [nhan_vien_id], [khach_hang_id], [ngay_tao], [tong_tien], [trang_thai]) VALUES (1, N'HĐ001', 1, 1, CAST(N'2023-10-01T00:00:00.000' AS DateTime), CAST(15990000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[hoa_don] ([id], [ma], [nhan_vien_id], [khach_hang_id], [ngay_tao], [tong_tien], [trang_thai]) VALUES (2, N'HĐ002', 2, 2, CAST(N'2023-10-02T00:00:00.000' AS DateTime), CAST(17990000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[hoa_don] ([id], [ma], [nhan_vien_id], [khach_hang_id], [ngay_tao], [tong_tien], [trang_thai]) VALUES (3, N'HĐ003', 3, 3, CAST(N'2023-10-03T00:00:00.000' AS DateTime), CAST(13490000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[hoa_don] ([id], [ma], [nhan_vien_id], [khach_hang_id], [ngay_tao], [tong_tien], [trang_thai]) VALUES (4, N'HĐ004', 4, 4, CAST(N'2023-10-04T00:00:00.000' AS DateTime), CAST(21490000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[hoa_don] ([id], [ma], [nhan_vien_id], [khach_hang_id], [ngay_tao], [tong_tien], [trang_thai]) VALUES (5, N'HĐ005', 5, 5, CAST(N'2023-10-05T00:00:00.000' AS DateTime), CAST(11990000.00 AS Decimal(18, 2)), 0)
INSERT [dbo].[hoa_don] ([id], [ma], [nhan_vien_id], [khach_hang_id], [ngay_tao], [tong_tien], [trang_thai]) VALUES (6, N'HĐ006', 6, 6, CAST(N'2023-10-06T00:00:00.000' AS DateTime), CAST(15490000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[hoa_don] ([id], [ma], [nhan_vien_id], [khach_hang_id], [ngay_tao], [tong_tien], [trang_thai]) VALUES (7, N'HĐ007', 7, 7, CAST(N'2023-10-07T00:00:00.000' AS DateTime), CAST(14490000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[hoa_don] ([id], [ma], [nhan_vien_id], [khach_hang_id], [ngay_tao], [tong_tien], [trang_thai]) VALUES (8, N'HĐ008', 8, 8, CAST(N'2023-10-08T00:00:00.000' AS DateTime), CAST(19990000.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[hoa_don] ([id], [ma], [nhan_vien_id], [khach_hang_id], [ngay_tao], [tong_tien], [trang_thai]) VALUES (9, N'HĐ009', 9, 9, CAST(N'2023-10-09T00:00:00.000' AS DateTime), CAST(23490000.00 AS Decimal(18, 2)), 0)
INSERT [dbo].[hoa_don] ([id], [ma], [nhan_vien_id], [khach_hang_id], [ngay_tao], [tong_tien], [trang_thai]) VALUES (10, N'HĐ010', 10, 10, CAST(N'2023-10-10T00:00:00.000' AS DateTime), CAST(22490000.00 AS Decimal(18, 2)), 1)
SET IDENTITY_INSERT [dbo].[hoa_don] OFF
GO
SET IDENTITY_INSERT [dbo].[khach_hang] ON 

INSERT [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [trang_thai]) VALUES (1, N'Nguyen Thi Lan', CAST(N'1995-05-10' AS Date), N'Nữ', N'0911111111', N'lan@gmail.com', 1)
INSERT [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [trang_thai]) VALUES (2, N'Tran Van Minh', CAST(N'1990-01-20' AS Date), N'Nam', N'0922222222', N'minh@gmail.com', 1)
INSERT [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [trang_thai]) VALUES (3, N'Le Thi Hoa', CAST(N'1985-07-30' AS Date), N'Nữ', N'0933333333', N'hoa@gmail.com', 1)
INSERT [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [trang_thai]) VALUES (4, N'Pham Van Khoa', CAST(N'1993-09-12' AS Date), N'Nam', N'0944444444', N'khoa@gmail.com', 1)
INSERT [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [trang_thai]) VALUES (5, N'Vo Thi Mai', CAST(N'1992-04-18' AS Date), N'Nữ', N'0955555555', N'mai@gmail.com', 1)
INSERT [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [trang_thai]) VALUES (6, N'Hoang Van Tien', CAST(N'1988-12-22' AS Date), N'Nam', N'0966666666', N'tien@gmail.com', 1)
INSERT [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [trang_thai]) VALUES (7, N'Do Thi Thanh', CAST(N'1996-03-08' AS Date), N'Nữ', N'0977777777', N'thanh@gmail.com', 1)
INSERT [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [trang_thai]) VALUES (8, N'Bui Van An', CAST(N'1991-11-11' AS Date), N'Nam', N'0988888888', N'an@gmail.com', 1)
INSERT [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [trang_thai]) VALUES (9, N'Dang Thi Tuyet', CAST(N'1994-08-01' AS Date), N'Nữ', N'0999999999', N'tuyet@gmail.com', 1)
INSERT [dbo].[khach_hang] ([id], [ten], [ngay_sinh], [gioi_tinh], [sdt], [email], [trang_thai]) VALUES (10, N'Nguyen Van Quy', CAST(N'1990-10-10' AS Date), N'Nam', N'0900000000', N'quy@gmail.com', 1)
SET IDENTITY_INSERT [dbo].[khach_hang] OFF
GO
SET IDENTITY_INSERT [dbo].[khuyen_mai] ON 

INSERT [dbo].[khuyen_mai] ([id], [ma_km], [ten_km], [loai_km], [gia_tri], [ngay_bat_dau], [ngay_ket_thuc], [trang_thai]) VALUES (4, N'KM001', N'Giảm 10%', N'%', CAST(10.00 AS Decimal(10, 2)), CAST(N'2025-07-01' AS Date), CAST(N'2025-07-31' AS Date), 1)
INSERT [dbo].[khuyen_mai] ([id], [ma_km], [ten_km], [loai_km], [gia_tri], [ngay_bat_dau], [ngay_ket_thuc], [trang_thai]) VALUES (5, N'KM002', N'Giảm 500K', N'tiền mặt', CAST(500000.00 AS Decimal(10, 2)), CAST(N'2025-07-01' AS Date), CAST(N'2025-07-20' AS Date), 1)
INSERT [dbo].[khuyen_mai] ([id], [ma_km], [ten_km], [loai_km], [gia_tri], [ngay_bat_dau], [ngay_ket_thuc], [trang_thai]) VALUES (6, N'KM003', N'Ưu đãi đặc biệt', N'%', CAST(15.00 AS Decimal(10, 2)), CAST(N'2025-07-10' AS Date), CAST(N'2025-08-10' AS Date), 1)
SET IDENTITY_INSERT [dbo].[khuyen_mai] OFF
GO
SET IDENTITY_INSERT [dbo].[nhan_vien] ON 

INSERT [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (1, N'NV001', N'Nguyen Van A', N'Nam', N'1990-01-01', N'Hà Nội', N'0909123456', N'a@gmail.com', 1, N'admin')
INSERT [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (2, N'NV002', N'Tran Thi B', N'Nữ', N'1992-05-12', N'TP.HCM', N'0909456123', N'b@gmail.com', 1, N'Nhân viên')
INSERT [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (3, N'NV003', N'Le Van C', N'Nam', N'1988-08-08', N'Đà Nẵng', N'0909123987', N'c@gmail.com', 1, N'Quản lý')
INSERT [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (4, N'NV004', N'Pham Thi D', N'Nữ', N'1995-03-15', N'Hải Phòng', N'0909567890', N'd@gmail.com', 1, N'Nhân viên')
INSERT [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (5, N'NV005', N'Hoang Van E', N'Nam', N'1993-07-20', N'Cần Thơ', N'0912123456', N'e@gmail.com', 0, N'Nhân viên')
INSERT [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (6, N'NV006', N'Nguyen Thi F', N'Nữ', N'1990-12-25', N'Bình Dương', N'0912345678', N'f@gmail.com', 1, N'Quản lý')
INSERT [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (7, N'NV007', N'Do Van G', N'Nam', N'1991-06-10', N'Hưng Yên', N'0921123456', N'g@gmail.com', 1, N'Nhân viên')
INSERT [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (8, N'NV008', N'Vo Thi H', N'Nữ', N'1994-11-11', N'Quảng Ninh', N'0933456123', N'h@gmail.com', 0, N'Nhân viên')
INSERT [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (9, N'NV009', N'Dang Van I', N'Nam', N'1989-09-09', N'Nam Định', N'0944123456', N'i@gmail.com', 1, N'admin')
INSERT [dbo].[nhan_vien] ([id], [ma_nv], [ten_nv], [gioi_tinh], [ngay_sinh], [dia_chi], [sdt], [email], [trang_thai], [chuc_vu]) VALUES (10, N'NV010', N'Bui Thi K', N'Nữ', N'1996-10-01', N'Thái Nguyên', N'0952123456', N'k@gmail.com', 1, N'Nhân viên')
SET IDENTITY_INSERT [dbo].[nhan_vien] OFF
GO
INSERT [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (1, N'SSD KingStone', N'128GB', N'SSD', 1)
INSERT [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (2, N'SSD Samsung', N'256GB', N'SSD', 1)
INSERT [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (3, N'SSD WD', N'512GB', N'SSD', 1)
INSERT [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (4, N'SSD Crucial', N'1TB', N'SSD', 1)
INSERT [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (5, N'HDD Seagate', N'1TB', N'HDD', 1)
INSERT [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (6, N'HDD Toshiba', N'2TB', N'HDD', 1)
INSERT [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (7, N'SSD Kingston', N'2TB', N'SSD', 1)
INSERT [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (8, N'SSD Samsung', N'4TB', N'SSD', 1)
INSERT [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (9, N'HDD WD', N'4TB', N'HDD', 1)
INSERT [dbo].[ocung] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (10, N'SSD Crucial', N'8TB', N'SSD', 1)
GO
SET IDENTITY_INSERT [dbo].[ram] ON 

INSERT [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (1, N'RAM 4GB DDR4', N'4GB', N'DDR4', 1)
INSERT [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (2, N'RAM 8GB DDR4', N'8GB', N'DDR4', 1)
INSERT [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (3, N'RAM 16GB DDR4', N'16GB', N'DDR4', 1)
INSERT [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (4, N'RAM 32GB DDR4', N'32GB', N'DDR4', 1)
INSERT [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (5, N'RAM 64GB DDR4', N'64GB', N'DDR4', 1)
INSERT [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (6, N'RAM 128GB DDR4', N'128GB', N'DDR4', 1)
INSERT [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (7, N'RAM 8GB DDR5', N'8GB', N'DDR5', 1)
INSERT [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (8, N'RAM 16GB DDR5', N'16GB', N'DDR5', 1)
INSERT [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (9, N'RAM 32GB DDR5', N'32GB', N'DDR5', 1)
INSERT [dbo].[ram] ([id], [ten], [dung_luong], [loai], [trang_thai]) VALUES (10, N'RAM 64GB DDR5', N'64GB', N'DDR5', 1)
SET IDENTITY_INSERT [dbo].[ram] OFF
GO
SET IDENTITY_INSERT [dbo].[san_pham] ON 

INSERT [dbo].[san_pham] ([id], [ten], [masp], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (1, N'Laptop Dell Vostro 15', N'SP001', N'Máy phù hợp cho sinh viên, văn phòng.', 1, 1, 1)
INSERT [dbo].[san_pham] ([id], [ten], [masp], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (2, N'Dell Inspiron 14', N'SP002', N'Hiệu năng ổn định, thiết kế gọn nhẹ.', 1, 1, 1)
INSERT [dbo].[san_pham] ([id], [ten], [masp], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (3, N'Dell XPS 13', N'SP003', N'Cao cấp, màn hình viền mỏng.', 1, 1, 1)
INSERT [dbo].[san_pham] ([id], [ten], [masp], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (4, N'Dell G15 Gaming', N'SP004', N'Laptop gaming mạnh mẽ.', 2, 1, 1)
INSERT [dbo].[san_pham] ([id], [ten], [masp], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (5, N'Dell Latitude 7410', N'SP005', N'Cho doanh nghiệp, pin lâu.', 1, 1, 1)
INSERT [dbo].[san_pham] ([id], [ten], [masp], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (6, N'Dell Precision 3551', N'SP006', N'Máy trạm đồ họa chuyên nghiệp.', 3, 1, 1)
INSERT [dbo].[san_pham] ([id], [ten], [masp], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (7, N'Dell Alienware M15', N'SP007', N'Gaming cao cấp, đèn LED RGB.', 2, 1, 1)
INSERT [dbo].[san_pham] ([id], [ten], [masp], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (8, N'Dell Inspiron 15', N'SP008', N'Giá rẻ, cấu hình ổn.', 1, 1, 1)
INSERT [dbo].[san_pham] ([id], [ten], [masp], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (9, N'Dell XPS 15', N'SP009', N'Màn hình 4K, thiết kế cao cấp.', 1, 1, 1)
INSERT [dbo].[san_pham] ([id], [ten], [masp], [mo_ta], [loai_san_pham_id], [hang_id], [trang_thai]) VALUES (10, N'Dell Chromebook 11', N'SP010', N'Dành cho giáo dục.', 4, 1, 1)
SET IDENTITY_INSERT [dbo].[san_pham] OFF
GO
SET IDENTITY_INSERT [dbo].[serial] ON 

INSERT [dbo].[serial] ([id], [ma_serial], [chi_tiet_sp_id], [trang_thai]) VALUES (1, N'SERIAL001', 1, 1)
INSERT [dbo].[serial] ([id], [ma_serial], [chi_tiet_sp_id], [trang_thai]) VALUES (2, N'SERIAL002', 2, 1)
INSERT [dbo].[serial] ([id], [ma_serial], [chi_tiet_sp_id], [trang_thai]) VALUES (3, N'SERIAL003', 3, 1)
INSERT [dbo].[serial] ([id], [ma_serial], [chi_tiet_sp_id], [trang_thai]) VALUES (4, N'SERIAL004', 4, 1)
INSERT [dbo].[serial] ([id], [ma_serial], [chi_tiet_sp_id], [trang_thai]) VALUES (5, N'SERIAL005', 5, 1)
INSERT [dbo].[serial] ([id], [ma_serial], [chi_tiet_sp_id], [trang_thai]) VALUES (6, N'SERIAL006', 6, 1)
INSERT [dbo].[serial] ([id], [ma_serial], [chi_tiet_sp_id], [trang_thai]) VALUES (7, N'SERIAL007', 7, 1)
INSERT [dbo].[serial] ([id], [ma_serial], [chi_tiet_sp_id], [trang_thai]) VALUES (8, N'SERIAL008', 8, 1)
INSERT [dbo].[serial] ([id], [ma_serial], [chi_tiet_sp_id], [trang_thai]) VALUES (9, N'SERIAL009', 9, 1)
INSERT [dbo].[serial] ([id], [ma_serial], [chi_tiet_sp_id], [trang_thai]) VALUES (10, N'SERIAL010', 10, 1)
SET IDENTITY_INSERT [dbo].[serial] OFF
GO
SET IDENTITY_INSERT [dbo].[tai_khoan] ON 

INSERT [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (1, N'admin1', N'123456', 1, N'NV001')
INSERT [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (2, N'nhanvien2', N'abc123', 1, N'NV002')
INSERT [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (3, N'quanly3', N'qwerty', 1, N'NV003')
INSERT [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (4, N'admin4', N'password', 0, N'NV004')
INSERT [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (5, N'user5', N'userpass', 1, N'NV005')
INSERT [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (6, N'user6', N'userpass6', 1, N'NV006')
INSERT [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (7, N'user7', N'userpass7', 1, N'NV007')
INSERT [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (8, N'user8', N'userpass8', 1, N'NV008')
INSERT [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (9, N'user9', N'userpass9', 1, N'NV009')
INSERT [dbo].[tai_khoan] ([id], [ten_dang_nhap], [mat_khau], [trang_thai], [manv]) VALUES (10, N'user10', N'userpass10', 1, N'NV010')
SET IDENTITY_INSERT [dbo].[tai_khoan] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__khuyen_m__0FE0B792393A75FE]    Script Date: 7/16/2025 5:10:57 PM ******/
ALTER TABLE [dbo].[khuyen_mai] ADD UNIQUE NONCLUSTERED 
(
	[ma_km] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ct_hoa_don] ADD  DEFAULT ((1)) FOR [so_luong]
GO
ALTER TABLE [dbo].[ct_hoa_don] ADD  DEFAULT ((0)) FOR [tien_khuyen_mai]
GO
ALTER TABLE [dbo].[ct_hoa_don] ADD  DEFAULT ((1)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FK_ctsp_card] FOREIGN KEY([card_id])
REFERENCES [dbo].[card] ([id])
GO
ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FK_ctsp_card]
GO
ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FK_ctsp_cpu] FOREIGN KEY([cpu_id])
REFERENCES [dbo].[cpu] ([id])
GO
ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FK_ctsp_cpu]
GO
ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FK_ctsp_hang] FOREIGN KEY([hang_id])
REFERENCES [dbo].[hang] ([id])
GO
ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FK_ctsp_hang]
GO
ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FK_ctsp_ocung] FOREIGN KEY([ocung_id])
REFERENCES [dbo].[ocung] ([id])
GO
ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FK_ctsp_ocung]
GO
ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FK_ctsp_ram] FOREIGN KEY([ram_id])
REFERENCES [dbo].[ram] ([id])
GO
ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FK_ctsp_ram]
GO
ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FK_ctsp_sanpham] FOREIGN KEY([san_pham_id])
REFERENCES [dbo].[san_pham] ([id])
GO
ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FK_ctsp_sanpham]
GO
ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FK_ctsp_serial] FOREIGN KEY([serial_id])
REFERENCES [dbo].[serial] ([id])
GO
ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FK_ctsp_serial]
GO
ALTER TABLE [dbo].[ct_hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_cthd_hoa_don] FOREIGN KEY([hoa_don_id])
REFERENCES [dbo].[hoa_don] ([id])
GO
ALTER TABLE [dbo].[ct_hoa_don] CHECK CONSTRAINT [FK_cthd_hoa_don]
GO
ALTER TABLE [dbo].[ct_hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_cthd_serial] FOREIGN KEY([serial_id])
REFERENCES [dbo].[serial] ([id])
GO
ALTER TABLE [dbo].[ct_hoa_don] CHECK CONSTRAINT [FK_cthd_serial]
GO
ALTER DATABASE [DellStore] SET  READ_WRITE 
GO

CREATE VIEW vw_ct_hoa_don_display AS
SELECT 
    cthd.id,
    cthd.hoa_don_id,
    s.ma_serial,
    sp.ten AS ten_san_pham,
    cthd.so_luong,
    cthd.don_gia,
    cthd.tien_khuyen_mai,
    cthd.tong_tien,
    cthd.trang_thai
FROM ct_hoa_don cthd
JOIN serial s ON cthd.serial_id = s.id
JOIN chi_tiet_san_pham ctsp ON s.chi_tiet_sp_id = ctsp.id
JOIN san_pham sp ON ctsp.san_pham_id = sp.id;
GO

INSERT INTO ct_hoa_don (hoa_don_id, serial_id, so_luong, don_gia, tien_khuyen_mai, trang_thai)
VALUES
(1, 1, 2, 15990000, 0, 1),
(1, 2, 1, 17990000, 0, 1),
(2, 3, 3, 13490000, 0, 1),
(2, 4, 1, 21490000, 0, 1),
(3, 5, 2, 11990000, 0, 0),
(3, 6, 1, 15490000, 0, 1),
(4, 7, 4, 14490000, CAST(500000 AS decimal(10, 2)), 1),
(4, 8, 2, 19990000, CAST(1000000 AS decimal(10, 2)), 1);
