CREATE TABLE [dbo].[serial] (
    [id]             INT           IDENTITY (1, 1) NOT NULL,
    [ma_serial]      NVARCHAR (50) NULL,
    [chi_tiet_sp_id] INT           NULL,
    [trang_thai]     INT           NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

