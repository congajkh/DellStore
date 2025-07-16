CREATE TABLE [dbo].[card] (
    [id]         INT            IDENTITY (1, 1) NOT NULL,
    [ten]        NVARCHAR (100) NULL,
    [loai]       NVARCHAR (50)  NULL,
    [trang_thai] INT            NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

