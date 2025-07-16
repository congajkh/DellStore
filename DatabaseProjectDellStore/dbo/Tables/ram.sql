CREATE TABLE [dbo].[ram] (
    [id]         INT           IDENTITY (1, 1) NOT NULL,
    [ten]        NVARCHAR (50) NULL,
    [dung_luong] NVARCHAR (50) NULL,
    [loai]       NVARCHAR (50) NULL,
    [trang_thai] INT           NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

