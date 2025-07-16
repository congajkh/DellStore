CREATE TABLE [dbo].[tai_khoan] (
    [id]            INT            IDENTITY (1, 1) NOT NULL,
    [ten_dang_nhap] NVARCHAR (50)  NULL,
    [mat_khau]      NVARCHAR (100) NULL,
    [trang_thai]    INT            NULL,
    [manv]          NVARCHAR (20)  NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

