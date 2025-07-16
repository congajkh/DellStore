CREATE TABLE [dbo].[phieu_giam_gia] (
    [id]            INT             IDENTITY (1, 1) NOT NULL,
    [ten]           NVARCHAR (100)  NULL,
    [muc_giam]      DECIMAL (18, 2) NULL,
    [loai]          NVARCHAR (20)   NULL,
    [ngay_bat_dau]  DATETIME        NULL,
    [ngay_ket_thuc] DATETIME        NULL,
    [hang_ap_dung]  NVARCHAR (50)   NULL,
    [trang_thai]    INT             NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

