CREATE DATABASE DATN9;
go
USE DATN9;
GO

CREATE TABLE NhanVien (
    ID INT PRIMARY KEY IDENTITY,
    HoTen NVARCHAR(100) NOT NULL,
    TenTaiKhoan NVARCHAR(50),
    CaLamViec NVARCHAR(50),
	Role bit default 1,
	MatKhau NVARCHAR(50)
);
Drop table NhanVien

CREATE TABLE Ban (
    ID INT PRIMARY KEY IDENTITY,
    TrangThai NVARCHAR(50),
    Ten NVARCHAR(100),
    Loai NVARCHAR(50)
);

-- Bảng Hóa Đơn
CREATE TABLE HoaDon (
    ID INT PRIMARY KEY IDENTITY,
    NhanVienID INT FOREIGN KEY REFERENCES NhanVien(ID),
    BanID INT FOREIGN KEY REFERENCES Ban(ID),
    TongTien DECIMAL(18,2),
    ThoiGianTao DATETIME DEFAULT GETDATE()
);

CREATE TABLE ThucDon(
	ID Int PRIMARY KEY IDENTITY,
	TenMon NVARCHAR(50) NOT NULL,
	Loai NVARCHAR(50) NOT NULL,
	GiaBan NVARCHAR(50) NOT NULL,
	AnhMon NVARCHAR(150)
);

-- Bảng Chi Tiết Hóa Đơn
CREATE TABLE ChiTietHoaDon (
    ID INT PRIMARY KEY IDENTITY,
    HoaDonID INT FOREIGN KEY REFERENCES HoaDon(ID),
    ThucDonID INT FOREIGN KEY REFERENCES ThucDon(ID),
    SoLuong INT CHECK (SoLuong > 0),
    Gia DECIMAL(18,2) NOT NULL
);


CREATE TABLE BanGiaoCa (
    ID INT PRIMARY KEY IDENTITY,
    NhanVienGuiID INT FOREIGN KEY REFERENCES NhanVien(ID),
    NhanVienNhanID INT FOREIGN KEY REFERENCES NhanVien(ID),
    SoTienBanGiao DECIMAL(18,2) NOT NULL,
    ThoiGian DATETIME DEFAULT GETDATE()
);

CREATE TABLE ThoiGianHoaDon (
    ID INT PRIMARY KEY IDENTITY,
    BanID INT FOREIGN KEY REFERENCES Ban(ID),
    HoaDonID INT FOREIGN KEY REFERENCES HoaDon(ID),
    ThoiGianBatDau DATETIME,
    ThoiGianKetThuc DATETIME
);

CREATE TABLE ThueGay(
	ID Int PRIMARY KEY IDENTITY,
	TenGay NVARCHAR(50) NOT NULL,
	GiaThue NVARCHAR(50) NOT NULL,
	AnhGay NVARCHAR(150)
);
Drop table ThueGay

CREATE TABLE NhapHang (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    TenHang VARCHAR(255),
    NgayNhap DATE,
    SoLuong INT,
    GiaDon INT,
    TongGia INT,
    ThangNhap VARCHAR(255)
);

CREATE TABLE DichVuChiTiet (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    Ban INT,
    TenHang NVARCHAR(255),
    SoLuong INT,
    DonGia INT,
    ThanhTien INT
);

SELECT * FROM DichVuChiTiet WHERE Ban = 1;

CREATE TABLE bills (
    id INT IDENTITY(1,1) PRIMARY KEY,
    table_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    service_total FLOAT NOT NULL,
    hourly_total FLOAT NOT NULL,
    total FLOAT NOT NULL,
    bill_date DATETIME NOT NULL
);

CREATE TABLE LichSuMonAn(
	ID INT PRIMARY KEY IDENTITY,
	ThucdonnID INT FOREIGN KEY REFERENCES ThucDon(ID),
    TenMon NVARCHAR(100),
    SoLuong INT,
    NgayTao DATETIME DEFAULT GETDATE()
)
SELECT * FROM LichSuMonAn

CREATE TABLE ThongKeMonAn(
	TenMon NVARCHAR(100) PRIMARY KEY,
    SoLuong INT
)
SELECT * FROM ThongKeMonAn

INSERT INTO NhapHang (TenHang, NgayNhap, SoLuong, GiaDon, TongGia, ThangNhap)
VALUES
('Hàng A', '2025-04-01', 5, 200000, 1000000, 'Tháng 4'),
('Hàng B', '2025-04-05', 2, 300000, 600000, 'Tháng 4'),
('Hàng C', '2025-04-10', 3, 150000, 450000, 'Tháng 4');


INSERT INTO NhanVien (HoTen, TenTaiKhoan, CaLamViec, Role, MatKhau) VALUES
(N'Trần Thị B', N'TTB', N'Ca chiều', 0, N'1234'),
(N'Lê Văn C', N'LVC', N'Ca tối', 0, '34'),
(N'Phạm Thị D', N'PTD', N'Ca sáng', 0, N'5678'),
(N'Hoàng Văn E', N'User', N'Ca chiều', 0, N'1123'),
(N'Admin', N'Admin', N'Full', 1, '2234');
select * from NhanVien

INSERT INTO Ban (TrangThai, Ten, Loai) VALUES
(N'Trống', N'Bàn 1', N'VIP'),
(N'Có khách', N'Bàn 2', N'Thường'),
(N'Trống', N'Bàn 3', N'Thường'),
(N'Có khách', N'Bàn 4', N'VIP'),
(N'Trống', N'Bàn 5', N'Thường');

INSERT INTO HoaDon (NhanVienID, BanID, TongTien, ThoiGianTao) VALUES
(1, 1, 500000, GETDATE()),
(2, 2, 750000, GETDATE()),
(3, 3, 300000, GETDATE()),
(4, 4, 450000, GETDATE()),
(5, 5, 600000, GETDATE());


INSERT INTO ChiTietHoaDon (HoaDonID, ThucDonID, SoLuong, Gia) VALUES
(1, 1, 2, 40000),
(2, 2, 3, 90000),
(3, 3, 1, 150000),
(4, 4, 1, 500000),
(5, 5, 2, 160000);

INSERT INTO BanGiaoCa (NhanVienGuiID, NhanVienNhanID, SoTienBanGiao, ThoiGian) VALUES
(1, 2, 2000000, GETDATE()),
(2, 3, 1500000, GETDATE()),
(3, 4, 1800000, GETDATE()),
(4, 5, 2200000, GETDATE()),
(5, 1, 2500000, GETDATE());

INSERT INTO ThoiGianHoaDon (BanID, HoaDonID, ThoiGianBatDau, ThoiGianKetThuc) VALUES
(1, 1, GETDATE(), DATEADD(HOUR, 2, GETDATE())),
(2, 2, GETDATE(), DATEADD(HOUR, 3, GETDATE())),
(3, 3, GETDATE(), DATEADD(HOUR, 1, GETDATE())),
(4, 4, GETDATE(), DATEADD(HOUR, 2, GETDATE())),
(5, 5, GETDATE(), DATEADD(HOUR, 4, GETDATE()));

INSERT INTO ThucDon(TenMon,Loai, GiaBan, AnhMon) VALUES
('Bánh mỳ xá xíu','Đồ ăn', 12000, 'C:\Users\Lappro\Downloads\t1.jpg'),
('Mì tôm hải sản','Đồ ăn', 35000, 'C:\Users\Lappro\Downloads\t2.jpg'),
('Bánh mỳ xúc xích','Đồ ăn', 10000, 'C:\Users\Lappro\Downloads\t3.jpg'),
('Bò húc','Nước', 10000, 'C:\Users\Lappro\Downloads\t4.jpg'),
('Coca cola','Nước', 10000, 'C:\Users\Lappro\Downloads\t5.jpg'),
('Sting','Nước', 10000, 'C:\Users\Lappro\Downloads\t6.jpg'),
('Bia','Nước', 15000, 'C:\Users\Lappro\Downloads\t7.jpg'),
('Bao Thăng Long','Thuốc', 20000, 'C:\Users\Lappro\Downloads\t8.jpg'),
('Bao 555','Thuốc', 50000, 'C:\Users\Lappro\Downloads\t9.jpg'),
('Bao Sài Gòn','Thuốc', 25000, 'C:\Users\Lappro\Downloads\t10.jpg');


INSERT INTO ThueGay(TenGay, GiaThue, AnhGay) VALUES
('Cơ CUPPA Pink S9', 30000, 'C:\Users\Lappro\Downloads\g1.jpg'),
('Cơ LONGFU X03 Full Cacbon', 35000, 'C:\Users\Lappro\Downloads\g2.jpg'),
('Cơ Fury Tempest AE Seri T1-Pro', 40000, 'C:\Users\Lappro\Downloads\g3.jpg'),
('Cơ Bida CUPPA MT-01', 30000, 'C:\Users\Lappro\Downloads\g4.jpg'),
('Cơ Fury TY RK', 35000, 'C:\Users\Lappro\Downloads\g5.jpg');

SELECT TOP 5 td.TenMon, SUM(ct.SoLuong) AS TongSoLuong
FROM ChiTietHoaDon ct
JOIN ThucDon td ON ct.ThucDonID = td.ID
GROUP BY td.TenMon
ORDER BY TongSoLuong DESC;

SELECT TOP 5 TenMon, SUM(SoLuong) AS TongSoLuong
FROM LichSuMonAn
GROUP BY TenMon
ORDER BY TongSoLuong DESC;

SELECT * FROM NhanVien
SELECT * FROM Ban
SELECT * FROM DichVu
SELECT * FROM HoaDon
SELECT * FROM ChiTietHoaDon
SELECT * FROM BanGiaoCa
SELECT * FROM ThoiGianHoaDon
SELECT * FROM ThucDon
SELECT * FROM ThueGay
	