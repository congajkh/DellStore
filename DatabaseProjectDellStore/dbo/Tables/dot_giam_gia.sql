CREATE TABLE [dbo].[dot_giam_gia] (
    [id]            INT            IDENTITY (1, 1) NOT NULL,
    [ten]           NVARCHAR (100) NULL,
    [ngay_bat_dau]  DATETIME       NULL,
    [ngay_ket_thuc] DATETIME       NULL,
    [trang_thai]    INT            NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

