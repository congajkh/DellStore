CREATE DATABASE DellStore;
GO
USE DellStore;

CREATE TABLE card (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(100),
  loai NVARCHAR(50),
  trang_thai INT
);
drop table chi_tiet_san_pham;
CREATE TABLE chi_tiet_san_pham (
  id INT IDENTITY(1,1) PRIMARY KEY,
  san_pham_id INT,
  ram_id INT,
  cpu_id INT,
  ocung_id INT,
  card_id INT,
  man_hinh_id INT,
  gia_nhap DECIMAL(18,2),
  gia_ban DECIMAL(18,2),
  so_luong INT,
  trang_thai INT
);
SELECT 
    f.name AS constraint_name,
    OBJECT_NAME(f.parent_object_id) AS referencing_table,
    COL_NAME(fc.parent_object_id, fc.parent_column_id) AS referencing_column
FROM 
    sys.foreign_keys AS f
INNER JOIN 
    sys.foreign_key_columns AS fc ON f.object_id = fc.constraint_object_id
WHERE 
    OBJECT_NAME(f.referenced_object_id) = 'chi_tiet_san_pham';
ALTER TABLE ct_dot_giam_gia DROP CONSTRAINT FK_ct_dot_giam_gia_chi_tiet_sp;
ALTER TABLE hinh_anh DROP CONSTRAINT FK_hinh_anh_chi_tiet_sp;
ALTER TABLE serial DROP CONSTRAINT FK_serial_chi_tiet_san_pham;

ALTER TABLE chi_tiet_san_pham
ADD CONSTRAINT FK_ctsp_sp FOREIGN KEY (san_pham_id) REFERENCES san_pham(id),
    CONSTRAINT FK_ctsp_ram FOREIGN KEY (ram_id) REFERENCES ram(id),
    CONSTRAINT FK_ctsp_cpu FOREIGN KEY (cpu_id) REFERENCES cpu(id),
    CONSTRAINT FK_ctsp_ocung FOREIGN KEY (ocung_id) REFERENCES ocung(id),
    CONSTRAINT FK_ctsp_card FOREIGN KEY (card_id) REFERENCES card(id),
    CONSTRAINT FK_ctsp_mh FOREIGN KEY (man_hinh_id) REFERENCES man_hinh(id);

ALTER TABLE ct_dot_giam_gia
ADD CONSTRAINT FK_ct_dot_giam_gia_chi_tiet_sp
FOREIGN KEY (chi_tiet_sp_id) REFERENCES chi_tiet_san_pham(id);

ALTER TABLE hinh_anh
ADD CONSTRAINT FK_hinh_anh_chi_tiet_sp
FOREIGN KEY (chi_tiet_sp_id) REFERENCES chi_tiet_san_pham(id);

ALTER TABLE serial
ADD CONSTRAINT FK_serial_chi_tiet_san_pham
FOREIGN KEY (chi_tiet_sp_id) REFERENCES chi_tiet_san_pham(id);


CREATE TABLE cpu (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(100),
  toc_do NVARCHAR(50),
  loai NVARCHAR(50),
  trang_thai INT
);

CREATE TABLE ct_dot_giam_gia (
  id INT IDENTITY(1,1) PRIMARY KEY,
  dot_giam_gia_id INT,
  chi_tiet_sp_id INT,
  muc_giam DECIMAL(18,2),
  loai_giam NVARCHAR(20),
  trang_thai INT
);

CREATE TABLE ct_hoa_don (
  id INT IDENTITY(1,1) PRIMARY KEY,
  hoa_don_id INT,
  serial_id INT,
  don_gia DECIMAL(18,2),
  trang_thai INT
);

CREATE TABLE ct_thanh_toan (
  id INT IDENTITY(1,1) PRIMARY KEY,
  hoa_don_id INT,
  hinh_thuc_id INT,
  so_tien DECIMAL(18,2)
);

CREATE TABLE dot_giam_gia (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(100),
  ngay_bat_dau DATETIME,
  ngay_ket_thuc DATETIME,
  trang_thai INT
);

CREATE TABLE hang (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(100),
  trang_thai INT
);

CREATE TABLE hinh_anh (
  id INT IDENTITY(1,1) PRIMARY KEY,
  chi_tiet_sp_id INT,
  duong_dan NVARCHAR(255),
  trang_thai INT
);

CREATE TABLE hinh_thuc_thanh_toan (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(50),
  trang_thai INT
);

CREATE TABLE hoa_don (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ma NVARCHAR(20),
  nhan_vien_id INT,
  khach_hang_id INT,
  ngay_tao DATETIME,
  tong_tien DECIMAL(18,2),
  trang_thai INT
);

CREATE TABLE hoa_don_treo (
  id INT IDENTITY(1,1) PRIMARY KEY,
  hoa_don_id INT,
  ghi_chu NVARCHAR(255),
  trang_thai INT
);

CREATE TABLE khach_hang (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(100),
  ngay_sinh DATE,
  gioi_tinh NVARCHAR(10),
  sdt NVARCHAR(20),
  email NVARCHAR(100),
  diem_thuong INT,
  hang NVARCHAR(50),
  trang_thai INT
);

INSERT INTO khach_hang (ten, ngay_sinh, gioi_tinh, sdt, email, diem_thuong, hang, trang_thai) VALUES
('Nguyen Thi Lan', '1995-05-10', N'Nữ', '0911111111', 'lan@gmail.com', 100, 'Dong', 1),
('Tran Van Minh', '1990-01-20', N'Nam', '0922222222', 'minh@gmail.com', 200, 'Bac', 1),
('Le Thi Hoa', '1985-07-30', N'Nữ', '0933333333', 'hoa@gmail.com', 50, 'Dong', 1),
('Pham Van Khoa', '1993-09-12', N'Nam', '0944444444', 'khoa@gmail.com', 500, 'Vang', 1),
('Vo Thi Mai', '1992-04-18', N'Nữ', '0955555555', 'mai@gmail.com', 300, 'Bac', 1),
('Hoang Van Tien', '1988-12-22', N'Nam', '0966666666', 'tien@gmail.com', 150, 'Dong', 1),
('Do Thi Thanh', '1996-03-08', N'Nữ', '0977777777', 'thanh@gmail.com', 220, 'Bac', 1),
('Bui Van An', '1991-11-11', N'Nam', '0988888888', 'an@gmail.com', 400, 'Vang', 1),
('Dang Thi Tuyet', '1994-08-01', N'Nữ', '0999999999', 'tuyet@gmail.com', 90, 'Dong', 1),
('Nguyen Van Quy', '1990-10-10', N'Nam', '0900000000', 'quy@gmail.com', 50, 'Dong', 1);

CREATE TABLE loai_san_pham (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(100),
  trang_thai INT
);

CREATE TABLE man_hinh (
  id INT IDENTITY(1,1) PRIMARY KEY,
  kich_thuoc NVARCHAR(50),
  do_phan_giai NVARCHAR(50),
  tan_so NVARCHAR(50),
  loai_tam_nen NVARCHAR(50),
  trang_thai INT
);

CREATE TABLE nhan_vien (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ma_nv NVARCHAR(20) UNIQUE,
  ten_nv NVARCHAR(100),
  gioi_tinh NVARCHAR(10),
  ngay_sinh NVARCHAR(50),
  dia_chi NVARCHAR(255),
  sdt NVARCHAR(20),
  email NVARCHAR(100),
  trang_thai INT,
  chuc_vu NVARCHAR(50)
);

INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu) VALUES
('NV001', 'Nguyen Van A', N'Nam', '1990-01-01', N'Hà Nội', '0909123456', 'a@gmail.com', 1, 'admin'),
('NV002', 'Tran Thi B', N'Nữ', '1992-05-12', N'TP.HCM', '0909456123', 'b@gmail.com', 1, 'nhan_vien'),
('NV003', 'Le Van C', N'Nam', '1988-08-08', N'Đà Nẵng', '0909123987', 'c@gmail.com', 1, 'quan_ly'),
('NV004', 'Pham Thi D', N'Nữ', '1995-03-15', N'Hải Phòng', '0909567890', 'd@gmail.com', 1, 'nhan_vien'),
('NV005', 'Hoang Van E', N'Nam', '1993-07-20', N'Cần Thơ', '0912123456', 'e@gmail.com', 0, 'nhan_vien'),
('NV006', 'Nguyen Thi F', N'Nữ', '1990-12-25', N'Bình Dương', '0912345678', 'f@gmail.com', 1, 'quan_ly'),
('NV007', 'Do Van G', N'Nam', '1991-06-10', N'Hưng Yên', '0921123456', 'g@gmail.com', 1, 'nhan_vien'),
('NV008', 'Vo Thi H', N'Nữ', '1994-11-11', N'Quảng Ninh', '0933456123', 'h@gmail.com', 0, 'nhan_vien'),
('NV009', 'Dang Van I', N'Nam', '1989-09-09', N'Nam Định', '0944123456', 'i@gmail.com', 1, 'admin'),
('NV010', 'Bui Thi K', N'Nữ', '1996-10-01', N'Thái Nguyên', '0952123456', 'k@gmail.com', 1, 'nhan_vien'),
('NV011', N'Đỗ Chí Công', N'Nam', '23/12/2000', N'Hà Nội', '0963852741', 'docong989@gmail.com', 1, N'Nhân viên');

CREATE TABLE nhat_ky_dang_nhap (
  id INT IDENTITY(1,1) PRIMARY KEY,
  nhan_vien_id INT,
  thoi_gian DATETIME,
  dia_chi_ip NVARCHAR(100),
  trang_thai INT
);

CREATE TABLE phieu_giam_gia (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(100),
  muc_giam DECIMAL(18,2),
  loai NVARCHAR(20),
  ngay_bat_dau DATETIME,
  ngay_ket_thuc DATETIME,
  hang_ap_dung NVARCHAR(50),
  trang_thai INT
);

CREATE TABLE ram (
  id INT IDENTITY(1,1) PRIMARY KEY,
  dung_luong NVARCHAR(50),
  loai NVARCHAR(50),
  trang_thai INT
);

CREATE TABLE san_pham (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(255),
  mo_ta NVARCHAR(255),
  loai_san_pham_id INT,
  hang_id INT,
  trang_thai INT
);

CREATE TABLE serial (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ma_serial NVARCHAR(50) UNIQUE,
  chi_tiet_sp_id INT,
  trang_thai INT
);

CREATE TABLE ssd (
  id INT IDENTITY(1,1) PRIMARY KEY,
  dung_luong NVARCHAR(50),
  loai NVARCHAR(50),
  trang_thai INT
);

CREATE TABLE tai_khoan (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten_dang_nhap NVARCHAR(50) UNIQUE,
  mat_khau NVARCHAR(100),
  nhan_vien_id INT,
  trang_thai INT
);

INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, nhan_vien_id, trang_thai) VALUES
('admin1', '123456', 1, 1),
('nhanvien2', 'abc123', 2, 1),
('quanly3', 'qwerty', 3, 1),
('admin4', 'password', 4, 0),
('user5', 'userpass', 5, 1);

CREATE TABLE thong_ke_doanh_thu (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ngay DATE,
  san_pham_id INT,
  so_luong INT,
  doanh_thu DECIMAL(18,2),
  trang_thai INT
);

-- FOREIGN KEY constraints cho chi_tiet_san_pham
ALTER TABLE chi_tiet_san_pham
  ADD CONSTRAINT FK_chi_tiet_san_pham_san_pham FOREIGN KEY (san_pham_id) REFERENCES san_pham(id);
GO
//lat sua
ALTER TABLE chi_tiet_san_pham
  ADD CONSTRAINT FK_chi_tiet_san_pham_ram FOREIGN KEY (ram_id) REFERENCES ram(id);
GO

ALTER TABLE chi_tiet_san_pham
  ADD CONSTRAINT FK_chi_tiet_san_pham_cpu FOREIGN KEY (cpu_id) REFERENCES cpu(id);
GO

ALTER TABLE chi_tiet_san_pham
  ADD CONSTRAINT FK_chi_tiet_san_pham_ssd FOREIGN KEY (ssd_id) REFERENCES ssd(id);
GO

ALTER TABLE chi_tiet_san_pham
  ADD CONSTRAINT FK_chi_tiet_san_pham_card FOREIGN KEY (card_id) REFERENCES card(id);
GO

ALTER TABLE chi_tiet_san_pham
  ADD CONSTRAINT FK_chi_tiet_san_pham_man_hinh FOREIGN KEY (man_hinh_id) REFERENCES man_hinh(id);
GO

-- FOREIGN KEY constraints cho ct_dot_giam_gia
ALTER TABLE ct_dot_giam_gia
  ADD CONSTRAINT FK_ct_dot_giam_gia_dot_giam_gia FOREIGN KEY (dot_giam_gia_id) REFERENCES dot_giam_gia(id);
GO

ALTER TABLE ct_dot_giam_gia
  ADD CONSTRAINT FK_ct_dot_giam_gia_chi_tiet_sp FOREIGN KEY (chi_tiet_sp_id) REFERENCES chi_tiet_san_pham(id);
GO

-- FOREIGN KEY constraints cho ct_hoa_don
ALTER TABLE ct_hoa_don
  ADD CONSTRAINT FK_ct_hoa_don_hoa_don FOREIGN KEY (hoa_don_id) REFERENCES hoa_don(id);
GO

ALTER TABLE ct_hoa_don
  ADD CONSTRAINT FK_ct_hoa_don_serial FOREIGN KEY (serial_id) REFERENCES serial(id);
GO

-- FOREIGN KEY constraints cho ct_thanh_toan
ALTER TABLE ct_thanh_toan
  ADD CONSTRAINT FK_ct_thanh_toan_hoa_don FOREIGN KEY (hoa_don_id) REFERENCES hoa_don(id);
GO

ALTER TABLE ct_thanh_toan
  ADD CONSTRAINT FK_ct_thanh_toan_hinh_thuc FOREIGN KEY (hinh_thuc_id) REFERENCES hinh_thuc_thanh_toan(id);
GO

-- FOREIGN KEY constraint cho hinh_anh
ALTER TABLE hinh_anh
  ADD CONSTRAINT FK_hinh_anh_chi_tiet_sp FOREIGN KEY (chi_tiet_sp_id) REFERENCES chi_tiet_san_pham(id);
GO

-- FOREIGN KEY constraints cho hoa_don
ALTER TABLE hoa_don
  ADD CONSTRAINT FK_hoa_don_nhan_vien FOREIGN KEY (nhan_vien_id) REFERENCES nhan_vien(id);
GO

ALTER TABLE hoa_don
  ADD CONSTRAINT FK_hoa_don_khach_hang FOREIGN KEY (khach_hang_id) REFERENCES khach_hang(id);
GO

-- FOREIGN KEY constraint cho hoa_don_treo
ALTER TABLE hoa_don_treo
  ADD CONSTRAINT FK_hoa_don_treo_hoa_don FOREIGN KEY (hoa_don_id) REFERENCES hoa_don(id);
GO

-- FOREIGN KEY constraint cho nhat_ky_dang_nhap
ALTER TABLE nhat_ky_dang_nhap
  ADD CONSTRAINT FK_nhat_ky_dang_nhap_nhan_vien FOREIGN KEY (nhan_vien_id) REFERENCES nhan_vien(id);
GO

-- FOREIGN KEY constraints cho san_pham
ALTER TABLE san_pham
  ADD CONSTRAINT FK_san_pham_loai_san_pham FOREIGN KEY (loai_san_pham_id) REFERENCES loai_san_pham(id);
GO

ALTER TABLE san_pham
  ADD CONSTRAINT FK_san_pham_hang FOREIGN KEY (hang_id) REFERENCES hang(id);
GO

-- FOREIGN KEY constraint cho serial
ALTER TABLE serial
  ADD CONSTRAINT FK_serial_chi_tiet_san_pham FOREIGN KEY (chi_tiet_sp_id) REFERENCES chi_tiet_san_pham(id);
GO

-- FOREIGN KEY constraint cho tai_khoan
ALTER TABLE tai_khoan
  ADD CONSTRAINT FK_tai_khoan_nhan_vien FOREIGN KEY (nhan_vien_id) REFERENCES nhan_vien(id);
GO

ALTER TABLE tai_khoan DROP CONSTRAINT FK_tai_khoan_nhan_vien;
ALTER TABLE tai_khoan DROP COLUMN nhan_vien_id;
ALTER TABLE tai_khoan ADD manv NVARCHAR(20);

EXEC sp_rename 'ssd', 'ocung';
INSERT INTO card (ten, loai, trang_thai) VALUES
(N'NVIDIA GTX 1050', N'Rời', 1),
(N'NVIDIA GTX 1650', N'Rời', 1),
(N'NVIDIA RTX 2060', N'Rời', 1),
(N'NVIDIA RTX 3060', N'Rời', 1),
(N'NVIDIA RTX 4060', N'Rời', 1),
(N'Intel UHD 620', N'Tích hợp', 1),
(N'Intel Iris Xe', N'Tích hợp', 1),
(N'AMD Radeon RX 560', N'Rời', 1),
(N'AMD Radeon RX 6600M', N'Rời', 1),
(N'NVIDIA Quadro T1000', N'Rời', 1);

INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES
(N'Intel Core i5-10300H', N'2.5GHz', N'Laptop', 1),
(N'Intel Core i7-10750H', N'2.6GHz', N'Laptop', 1),
(N'Intel Core i9-11900H', N'3.3GHz', N'Laptop', 1),
(N'AMD Ryzen 5 5600H', N'3.3GHz', N'Laptop', 1),
(N'AMD Ryzen 7 5800H', N'3.2GHz', N'Laptop', 1),
(N'Intel Core i3-1115G4', N'3.0GHz', N'Laptop', 1),
(N'Intel Core i5-1240P', N'3.3GHz', N'Laptop', 1),
(N'AMD Ryzen 9 5900HX', N'3.3GHz', N'Laptop', 1),
(N'Intel Core i9-12900HK', N'3.6GHz', N'Laptop', 1),
(N'Intel Xeon E-2286M', N'2.4GHz', N'Mobile Workstation', 1);

ALTER TABLE chi_tiet_san_pham
DROP CONSTRAINT FK_chi_tiet_san_pham_ram;

DROP TABLE ram;

CREATE TABLE ram (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(50),
  dung_luong NVARCHAR(50),
  loai NVARCHAR(50),
  trang_thai INT
);
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES
( N'Kingston 8GB DDR4', N'8GB', N'DDR4', 1),
( N'Corsair 16GB DDR4', N'16GB', N'DDR4', 1),
( N'G.Skill 32GB DDR4', N'32GB', N'DDR4', 1),
( N'ADATA 4GB DDR4', N'4GB', N'DDR4', 1),
( N'Crucial 8GB DDR4', N'8GB', N'DDR4', 1),
( N'Samsung 16GB DDR4', N'16GB', N'DDR4', 1),
( N'Teamgroup 32GB DDR4', N'32GB', N'DDR4', 1),
( N'Kingston Fury 8GB DDR5', N'8GB', N'DDR5', 1),
( N'Corsair Vengeance 16GB DDR5', N'16GB', N'DDR5', 1),
( N'G.Skill Ripjaws 32GB DDR5', N'32GB', N'DDR5', 1);

INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES
(N'Samsung EVO 256GB', 256, N'Nvme', 1),
(N'Samsung EVO 512GB', 512, N'Nvme', 1),
(N'WD Black 1TB', 1024, N'Nvme', 1),
(N'Kingston A2000 512GB', 512, N'Nvme', 1),
(N'Crucial MX500 250GB', 250, N'SATA', 1),
(N'Crucial MX500 1TB', 1024, N'SATA', 1),
(N'SanDisk Ultra 480GB', 480, N'SATA', 1),
(N'ADATA XPG 512GB', 512, N'Nvme', 1),
(N'Intel 660p 1TB', 1024, N'Nvme', 1),
(N'Lexar NM610 256GB', 256, N'Nvme', 1);

-- Bước 1: Đổi tên bảng cũ tạm thời để backup
EXEC sp_rename 'ocung', 'ocung_old';

-- Bước 2: Tạo lại bảng mới đúng thứ tự cột
CREATE TABLE ocung (
  id INT PRIMARY KEY,
  ten NVARCHAR(50),           -- 👈 Cột 'ten' đứng ngay sau 'id'
  dung_luong NVARCHAR(50),
  loai NVARCHAR(50),
  trang_thai INT
);

-- Bước 3: Chèn lại dữ liệu nếu cần
-- (Nếu bảng cũ có cột 'ten', thì copy từ đó. Nếu không thì thêm giá trị mặc định)
INSERT INTO ocung (id, ten, dung_luong, loai, trang_thai)
SELECT id, NULL AS ten, dung_luong, loai, trang_thai
FROM ocung_old;

INSERT INTO ocung (id, ten, dung_luong, loai, trang_thai) VALUES
(1, N'Kingston 8GB DDR4', N'8GB', N'DDR4', 1),
(2, N'Corsair 16GB DDR4', N'16GB', N'DDR4', 1),
(3, N'G.Skill 32GB DDR4', N'32GB', N'DDR4', 1),
(4, N'ADATA 4GB DDR4', N'4GB', N'DDR4', 1),
(5, N'Crucial 8GB DDR4', N'8GB', N'DDR4', 1),
(6, N'Samsung 16GB DDR4', N'16GB', N'DDR4', 1),
(7, N'Teamgroup 32GB DDR4', N'32GB', N'DDR4', 1),
(8, N'Kingston Fury 8GB DDR5', N'8GB', N'DDR5', 1),
(9, N'Corsair Vengeance 16GB DDR5', N'16GB', N'DDR5', 1),
(10, N'G.Skill Ripjaws 32GB DDR5', N'32GB', N'DDR5', 1);


INSERT INTO hang (ten, trang_thai) VALUES
(N'Dell', 1);
DELETE FROM cpu;
DELETE FROM ram;
DELETE FROM ocung;
SELECT * from cpu;
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES
('Intel Core i3', '3.2GHz', 'Thế hệ 10', 1),
('Intel Core i5', '3.8GHz', 'Thế hệ 11', 1),
('Intel Core i9', '5.0GHz', 'Thế hệ 12', 1);

select * FROM tai_khoan;
select * from CPU;
