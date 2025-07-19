
-- TẠO DATABASE
CREATE DATABASE DellStore2;
GO

USE DellStore2;
GO

-- BẢNG 1: nhan_vien
CREATE TABLE nhan_vien (
    id INT PRIMARY KEY IDENTITY,
    ma_nv VARCHAR(20),
    ten_nv NVARCHAR(100),
    gioi_tinh BIT,
    ngay_sinh DATE,
    dia_chi NVARCHAR(255),
    sdt VARCHAR(15),
    email VARCHAR(100),
    trang_thai INT,
    chuc_vu NVARCHAR(50),
    tai_khoan VARCHAR(50),
    mat_khau VARCHAR(255)
);

-- BẢNG 2: khach_hang
CREATE TABLE khach_hang (
    id INT PRIMARY KEY IDENTITY,
    ten NVARCHAR(100),
    gioi_tinh VARCHAR(15),
    dia_chi VARCHAR(100),
    sdt VARCHAR(15),
    email VARCHAR(100)
);

-- BẢNG 3: hinh_thuc_thanh_toan
CREATE TABLE hinh_thuc_thanh_toan (
    id INT PRIMARY KEY IDENTITY,
    ten NVARCHAR(100)
);

-- BẢNG 4: hoa_don
CREATE TABLE hoa_don (
    id INT PRIMARY KEY IDENTITY,
    ma VARCHAR(50),
    nhan_vien_id INT FOREIGN KEY REFERENCES nhan_vien(id),
    khach_hang_id INT FOREIGN KEY REFERENCES khach_hang(id),
    ngay_tao DATE,
    tong_tien DECIMAL(18,2)
);

-- BẢNG 5: chi_tiet_thanh_toan
CREATE TABLE chi_tiet_thanh_toan (
    id INT PRIMARY KEY IDENTITY,
    hoa_don_id INT FOREIGN KEY REFERENCES hoa_don(id),
    hinh_thuc_id INT FOREIGN KEY REFERENCES hinh_thuc_thanh_toan(id),
    so_tien DECIMAL(18,2)
);

-- BẢNG 6: hang
CREATE TABLE hang (
    id INT PRIMARY KEY IDENTITY,
    ten NVARCHAR(100)
);

-- BẢNG 7: loai_san_pham
CREATE TABLE loai_san_pham (
    id INT PRIMARY KEY IDENTITY,
    ten NVARCHAR(100)
);

-- BẢNG 8: san_pham
CREATE TABLE san_pham (
    id INT PRIMARY KEY IDENTITY,
    ten NVARCHAR(100),
    mo_ta NVARCHAR(255),
    loai_san_pham_id INT FOREIGN KEY REFERENCES loai_san_pham(id),
    hang_id INT FOREIGN KEY REFERENCES hang(id),
    trang_thai INT
);

-- BẢNG 9: cpu
CREATE TABLE cpu (
    id INT PRIMARY KEY IDENTITY,
    ten NVARCHAR(100),
    toc_do NVARCHAR(50),
    loai NVARCHAR(50),
    trang_thai NVARCHAR(50)
);

-- BẢNG 10: ram
CREATE TABLE ram (
    id INT PRIMARY KEY IDENTITY,
    ten NVARCHAR(100),
    dung_luong NVARCHAR(50),
    loai NVARCHAR(50),
    trang_thai NVARCHAR(50)
);

-- BẢNG 11: ssd
CREATE TABLE ssd (
    id INT PRIMARY KEY IDENTITY,
    ten NVARCHAR(100),
    dung_luong NVARCHAR(50),
    loai NVARCHAR(50),
    trang_thai NVARCHAR(50)
);

-- BẢNG 12: gpu
CREATE TABLE gpu (
    id INT PRIMARY KEY IDENTITY,
    ten NVARCHAR(100),
    loai NVARCHAR(50),
    trang_thai NVARCHAR(50)
);


-- BẢNG 14: chi_tiet_san_pham
CREATE TABLE chi_tiet_san_pham (
    id INT PRIMARY KEY IDENTITY,
    san_pham_id INT FOREIGN KEY REFERENCES san_pham(id),
    cpu_id INT FOREIGN KEY REFERENCES cpu(id),
    ram_id INT FOREIGN KEY REFERENCES ram(id),
    ssd_id INT FOREIGN KEY REFERENCES ssd(id),
    gpu_id INT FOREIGN KEY REFERENCES gpu(id),
    gia_ban DECIMAL(18,2),
    so_luong_ton INT
);


-- BẢNG 16: serial
CREATE TABLE serial (
    id INT PRIMARY KEY IDENTITY,
    ma_serial VARCHAR(50),
    chi_tiet_san_pham_id INT FOREIGN KEY REFERENCES chi_tiet_san_pham(id),
    trang_thai INT
);

-- BẢNG 17: dot_giam_gia
CREATE TABLE dot_giam_gia (
    id INT PRIMARY KEY IDENTITY,
    ngay_bat_dau DATE,
    ngay_ket_thuc DATE
);

-- BẢNG 18: chi_tiet_dot_giam_gia
CREATE TABLE chi_tiet_dot_giam_gia (
    id INT PRIMARY KEY IDENTITY,
    chi_tiet_san_pham_id INT FOREIGN KEY REFERENCES chi_tiet_san_pham(id),
    dot_giam_gia_id INT FOREIGN KEY REFERENCES dot_giam_gia(id),
    muc_giam DECIMAL(5,2)
);

-- BẢNG 19: chi_tiet_hoa_don
CREATE TABLE chi_tiet_hoa_don (
    id INT PRIMARY KEY IDENTITY,
    hoa_don_id INT FOREIGN KEY REFERENCES hoa_don(id),
    serial_id INT FOREIGN KEY REFERENCES serial(id),
    so_luong INT,
    don_gia DECIMAL(18,2),
    tien_khuyen_mai DECIMAL(18,2),
    tong_tien DECIMAL(18,2),
    trang_thai INT
);

-- BẢNG 20: serial_da_ban
CREATE TABLE serial_da_ban (
    id INT PRIMARY KEY IDENTITY,
    serial_id INT FOREIGN KEY REFERENCES serial(id),
    hoa_don_id INT FOREIGN KEY REFERENCES hoa_don(id)
);

INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES ('NV001', N'Nhân viên 1', 1, '1990-01-01', N'Địa chỉ 1', '090000001', 'nv1@mail.com', 1, N'Nhân viên', 'user1', 'pass1');
INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES ('NV002', N'Nhân viên 2', 0, '1990-01-02', N'Địa chỉ 2', '090000002', 'nv2@mail.com', 1, N'Nhân viên', 'user2', 'pass2');
INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES ('NV003', N'Nhân viên 3', 1, '1990-01-03', N'Địa chỉ 3', '090000003', 'nv3@mail.com', 1, N'Nhân viên', 'user3', 'pass3');
INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES ('NV004', N'Nhân viên 4', 0, '1990-01-04', N'Địa chỉ 4', '090000004', 'nv4@mail.com', 1, N'Nhân viên', 'user4', 'pass4');
INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES ('NV005', N'Nhân viên 5', 1, '1990-01-05', N'Địa chỉ 5', '090000005', 'nv5@mail.com', 1, N'Nhân viên', 'user5', 'pass5');
INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES ('NV006', N'Nhân viên 6', 0, '1990-01-06', N'Địa chỉ 6', '090000006', 'nv6@mail.com', 1, N'Nhân viên', 'user6', 'pass6');
INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES ('NV007', N'Nhân viên 7', 1, '1990-01-07', N'Địa chỉ 7', '090000007', 'nv7@mail.com', 1, N'Nhân viên', 'user7', 'pass7');
INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES ('NV008', N'Nhân viên 8', 0, '1990-01-08', N'Địa chỉ 8', '090000008', 'nv8@mail.com', 1, N'Nhân viên', 'user8', 'pass8');
INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES ('NV009', N'Nhân viên 9', 1, '1990-01-09', N'Địa chỉ 9', '090000009', 'nv9@mail.com', 1, N'Nhân viên', 'user9', 'pass9');
INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES ('NV010', N'Nhân viên 10', 0, '1990-01-10', N'Địa chỉ 10', '0900000010', 'nv10@mail.com', 1, N'Nhân viên', 'user10', 'pass10');
INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES (N'Khách hàng 1', N'Nam', N'Địa chỉ KH 1', '091000001', 'kh1@mail.com');
INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES (N'Khách hàng 2', N'Nam', N'Địa chỉ KH 2', '091000002', 'kh2@mail.com');
INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES (N'Khách hàng 3', N'Nam', N'Địa chỉ KH 3', '091000003', 'kh3@mail.com');
INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES (N'Khách hàng 4', N'Nam', N'Địa chỉ KH 4', '091000004', 'kh4@mail.com');
INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES (N'Khách hàng 5', N'Nam', N'Địa chỉ KH 5', '091000005', 'kh5@mail.com');
INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES (N'Khách hàng 6', N'Nam', N'Địa chỉ KH 6', '091000006', 'kh6@mail.com');
INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES (N'Khách hàng 7', N'Nam', N'Địa chỉ KH 7', '091000007', 'kh7@mail.com');
INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES (N'Khách hàng 8', N'Nam', N'Địa chỉ KH 8', '091000008', 'kh8@mail.com');
INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES (N'Khách hàng 9', N'Nam', N'Địa chỉ KH 9', '091000009', 'kh9@mail.com');
INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES (N'Khách hàng 10', N'Nam', N'Địa chỉ KH 10', '0910000010', 'kh10@mail.com');
INSERT INTO hinh_thuc_thanh_toan (ten) VALUES (N'Hình thức 1');
INSERT INTO hinh_thuc_thanh_toan (ten) VALUES (N'Hình thức 2');
INSERT INTO hinh_thuc_thanh_toan (ten) VALUES (N'Hình thức 3');
INSERT INTO hinh_thuc_thanh_toan (ten) VALUES (N'Hình thức 4');
INSERT INTO hinh_thuc_thanh_toan (ten) VALUES (N'Hình thức 5');
INSERT INTO hinh_thuc_thanh_toan (ten) VALUES (N'Hình thức 6');
INSERT INTO hinh_thuc_thanh_toan (ten) VALUES (N'Hình thức 7');
INSERT INTO hinh_thuc_thanh_toan (ten) VALUES (N'Hình thức 8');
INSERT INTO hinh_thuc_thanh_toan (ten) VALUES (N'Hình thức 9');
INSERT INTO hinh_thuc_thanh_toan (ten) VALUES (N'Hình thức 10');
INSERT INTO hang (ten) VALUES (N'Hãng 1');
INSERT INTO hang (ten) VALUES (N'Hãng 2');
INSERT INTO hang (ten) VALUES (N'Hãng 3');
INSERT INTO hang (ten) VALUES (N'Hãng 4');
INSERT INTO hang (ten) VALUES (N'Hãng 5');
INSERT INTO hang (ten) VALUES (N'Hãng 6');
INSERT INTO hang (ten) VALUES (N'Hãng 7');
INSERT INTO hang (ten) VALUES (N'Hãng 8');
INSERT INTO hang (ten) VALUES (N'Hãng 9');
INSERT INTO hang (ten) VALUES (N'Hãng 10');
INSERT INTO loai_san_pham (ten) VALUES (N'Loại SP 1');
INSERT INTO loai_san_pham (ten) VALUES (N'Loại SP 2');
INSERT INTO loai_san_pham (ten) VALUES (N'Loại SP 3');
INSERT INTO loai_san_pham (ten) VALUES (N'Loại SP 4');
INSERT INTO loai_san_pham (ten) VALUES (N'Loại SP 5');
INSERT INTO loai_san_pham (ten) VALUES (N'Loại SP 6');
INSERT INTO loai_san_pham (ten) VALUES (N'Loại SP 7');
INSERT INTO loai_san_pham (ten) VALUES (N'Loại SP 8');
INSERT INTO loai_san_pham (ten) VALUES (N'Loại SP 9');
INSERT INTO loai_san_pham (ten) VALUES (N'Loại SP 10');
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (N'CPU 1', N'2.0GHz', N'Intel', N'active');
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (N'CPU 2', N'4.0GHz', N'Intel', N'active');
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (N'CPU 3', N'6.0GHz', N'Intel', N'active');
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (N'CPU 4', N'8.0GHz', N'Intel', N'active');
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (N'CPU 5', N'10.0GHz', N'Intel', N'active');
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (N'CPU 6', N'12.0GHz', N'Intel', N'active');
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (N'CPU 7', N'14.0GHz', N'Intel', N'active');
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (N'CPU 8', N'16.0GHz', N'Intel', N'active');
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (N'CPU 9', N'18.0GHz', N'Intel', N'active');
INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (N'CPU 10', N'20.0GHz', N'Intel', N'active');
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (N'RAM 1', N'4GB', N'DDR4', N'active');
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (N'RAM 2', N'8GB', N'DDR4', N'active');
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (N'RAM 3', N'12GB', N'DDR4', N'active');
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (N'RAM 4', N'16GB', N'DDR4', N'active');
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (N'RAM 5', N'20GB', N'DDR4', N'active');
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (N'RAM 6', N'24GB', N'DDR4', N'active');
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (N'RAM 7', N'28GB', N'DDR4', N'active');
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (N'RAM 8', N'32GB', N'DDR4', N'active');
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (N'RAM 9', N'36GB', N'DDR4', N'active');
INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (N'RAM 10', N'40GB', N'DDR4', N'active');
INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES (N'SSD 1', N'128GB', N'SATA', N'active');
INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES (N'SSD 2', N'256GB', N'SATA', N'active');
INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES (N'SSD 3', N'384GB', N'SATA', N'active');
INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES (N'SSD 4', N'512GB', N'SATA', N'active');
INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES (N'SSD 5', N'640GB', N'SATA', N'active');
INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES (N'SSD 6', N'768GB', N'SATA', N'active');
INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES (N'SSD 7', N'896GB', N'SATA', N'active');
INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES (N'SSD 8', N'1024GB', N'SATA', N'active');
INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES (N'SSD 9', N'1152GB', N'SATA', N'active');
INSERT INTO ssd (ten, dung_luong, loai, trang_thai) VALUES (N'SSD 10', N'1280GB', N'SATA', N'active');
INSERT INTO gpu (ten, loai, trang_thai) VALUES (N'GPU 1', N'NVIDIA', N'active');
INSERT INTO gpu (ten, loai, trang_thai) VALUES (N'GPU 2', N'NVIDIA', N'active');
INSERT INTO gpu (ten, loai, trang_thai) VALUES (N'GPU 3', N'NVIDIA', N'active');
INSERT INTO gpu (ten, loai, trang_thai) VALUES (N'GPU 4', N'NVIDIA', N'active');
INSERT INTO gpu (ten, loai, trang_thai) VALUES (N'GPU 5', N'NVIDIA', N'active');
INSERT INTO gpu (ten, loai, trang_thai) VALUES (N'GPU 6', N'NVIDIA', N'active');
INSERT INTO gpu (ten, loai, trang_thai) VALUES (N'GPU 7', N'NVIDIA', N'active');
INSERT INTO gpu (ten, loai, trang_thai) VALUES (N'GPU 8', N'NVIDIA', N'active');
INSERT INTO gpu (ten, loai, trang_thai) VALUES (N'GPU 9', N'NVIDIA', N'active');
INSERT INTO gpu (ten, loai, trang_thai) VALUES (N'GPU 10', N'NVIDIA', N'active');