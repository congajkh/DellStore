CREATE TABLE [dbo].[hoa_don] (
    [id]            INT             IDENTITY (1, 1) NOT NULL,
    [ma]            NVARCHAR (20)   NULL,
    [nhan_vien_id]  INT             NULL,
    [khach_hang_id] INT             NULL,
    [ngay_tao]      DATETIME        NULL,
    [tong_tien]     DECIMAL (18, 2) NULL,
    [trang_thai]    INT             NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

