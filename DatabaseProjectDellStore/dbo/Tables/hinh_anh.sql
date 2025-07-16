CREATE TABLE [dbo].[hinh_anh] (
    [id]             INT            IDENTITY (1, 1) NOT NULL,
    [chi_tiet_sp_id] INT            NULL,
    [duong_dan]      NVARCHAR (255) NULL,
    [trang_thai]     INT            NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

