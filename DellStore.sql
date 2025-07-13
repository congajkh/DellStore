USE DellStore;

CREATE TABLE card (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(100),
  loai NVARCHAR(50),
  trang_thai INT
);

CREATE TABLE chi_tiet_san_pham (
  id INT IDENTITY(1,1) PRIMARY KEY,
  san_pham_id INT,
  ram_id INT,
  cpu_id INT,
  ssd_id INT,
  card_id INT,
  man_hinh_id INT,
  gia_nhap DECIMAL(18,2),
  gia_ban DECIMAL(18,2),
  so_luong INT,
  trang_thai INT
);

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