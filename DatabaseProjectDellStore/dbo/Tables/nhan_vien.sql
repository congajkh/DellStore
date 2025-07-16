CREATE TABLE [dbo].[nhan_vien] (
    [id]         INT            IDENTITY (1, 1) NOT NULL,
    [ma_nv]      NVARCHAR (20)  NULL,
    [ten_nv]     NVARCHAR (100) NULL,
    [gioi_tinh]  NVARCHAR (10)  NULL,
    [ngay_sinh]  NVARCHAR (50)  NULL,
    [dia_chi]    NVARCHAR (255) NULL,
    [sdt]        NVARCHAR (20)  NULL,
    [email]      NVARCHAR (100) NULL,
    [trang_thai] INT            NULL,
    [chuc_vu]    NVARCHAR (50)  NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

