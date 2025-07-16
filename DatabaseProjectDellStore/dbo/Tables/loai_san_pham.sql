CREATE TABLE [dbo].[loai_san_pham] (
    [id]         INT            IDENTITY (1, 1) NOT NULL,
    [ten]        NVARCHAR (100) NULL,
    [trang_thai] INT            NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

