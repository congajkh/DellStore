CREATE TABLE [dbo].[nhat_ky_dang_nhap] (
    [id]           INT            IDENTITY (1, 1) NOT NULL,
    [nhan_vien_id] INT            NULL,
    [thoi_gian]    DATETIME       NULL,
    [dia_chi_ip]   NVARCHAR (100) NULL,
    [trang_thai]   INT            NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

